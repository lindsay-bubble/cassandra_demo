package com.fairyfloss.cassandra.repository;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.Mapper;
import com.fairyfloss.cassandra.domain.GoodsDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.datastax.driver.mapping.Mapper.Option.ttl;
import static com.fairyfloss.cassandra.constant.CassandraConstant.DEMANDED_SIZE;
import static com.fairyfloss.cassandra.constant.CassandraConstant.TABLE_NAME;

/**
 * Created by Lindsay on 2017/5/1.
 */
@Repository
public class CassandraRepository {

    protected PreparedStatement preparedSelectGoodsByUid;

    @Autowired
    protected Mapper<GoodsDO> goodsMapper;

    @Autowired
    protected Session session;

    @Autowired
    public void setPreparedSelectGoodsByUid(Session session) {
        this.preparedSelectGoodsByUid = session.prepare("select * from " + TABLE_NAME + " where uid = ? limit 100");
    }

    // way 1 用preparedStatemet
    private ResultSet getResultSetByPreparedStatement(long uid) {
        Statement s = preparedSelectGoodsByUid.bind(uid);
        return session.execute(s);
    }

    // way 2 用QueryBuilder
    private ResultSet getResultSetByQueryBuilder(long uid) {
        return session.execute(QueryBuilder.select().all().from(TABLE_NAME).limit(100));
    }


    public void saveGoods(GoodsDO goodsDO) {
        goodsMapper.save(goodsDO);
    }

    public void saveGoodsWithTTL(GoodsDO goodsDO, int seconds) {
        // the data is valid for 3600 seconds = 1 hour
        goodsMapper.save(goodsDO, ttl(3600));
    }

    public GoodsDO getGoods() {
        // 输入key的顺序和java domain中定义clustering key的顺序有关，而和在cassandra table中定义的primary key的顺序无关。
        return goodsMapper.get(101, 12345L, 301);
    }

    // 得到所有的结果， 和fetchSize无关
    public List<GoodsDO> listGoods(long uid) {
        ResultSet resultSet = getResultSetByPreparedStatement(uid);
        List<GoodsDO> goodsList = new ArrayList<>(DEMANDED_SIZE);
        resultSet.all().forEach(row -> goodsList.add(toGoods(row)));
        return goodsList;
    }

    public List<GoodsDO> listFootprint(Long uid) {
        List<GoodsDO> goodsDOList = new ArrayList<>(DEMANDED_SIZE);
        ResultSet resultSet = session.execute(preparedSelectGoodsByUid.bind(uid));
        for (Row row = resultSet.one(); row != null && goodsDOList.size() < DEMANDED_SIZE; row = resultSet.one()) {
            GoodsDO goods = toGoods(row);
            if (check(goods)) {
                goodsDOList.add(goods);
            }
        }
        return goodsDOList;
    }

    protected GoodsDO toGoods(Row row) {
        GoodsDO goods = new GoodsDO();
        goods.setUid(row.getLong("uid"));
        goods.setBrowseTime(row.getLong("browse_time"));
        goods.setGoodsId(row.getLong("goods_id"));
        goods.setIsVisible(row.getBool("is_visible"));
        return goods;
    }

    protected boolean check(GoodsDO goods) {
        return goods.getUid() != null && goods.getIsVisible() != null && goods.getBrowseTime() != null && goods.getIsVisible();
    }
}

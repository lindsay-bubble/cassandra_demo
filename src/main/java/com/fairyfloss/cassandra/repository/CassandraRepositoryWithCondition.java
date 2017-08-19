package com.fairyfloss.cassandra.repository;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.fairyfloss.cassandra.domain.GoodsDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.fairyfloss.cassandra.constant.CassandraConstant.TABLE_NAME;

/**
 * Created by mianhuatang on 2017/5/26.
 */
@Repository
public class CassandraRepositoryWithCondition extends CassandraRepository {

    private PreparedStatement preparedUpdate;
    private PreparedStatement preparedInsert;

    @Autowired
    public void setPreparedUpdate(Session session) {
        this.preparedUpdate =
                session.prepare("UPDATE " + TABLE_NAME +
                        " SET is_visible = :v1 where uid = ? and browse_time = :b and goods_id = :g IF is_visible = :v2 ");
    }
    @Autowired
    public void setPreparedInsert(Session session) {
        this.preparedInsert =
                session.prepare("INSERT INTO " + TABLE_NAME +
                        " (uid, browse_time, goods_id, is_visible) values (:uid, :b, :g, :v) IF NOT EXISTS");
    }


    public boolean updateGoodsWithCheck(GoodsDO goodsDO) {

        BoundStatement boundStatement = preparedUpdate.bind()
                .setBool("v1", goodsDO.getIsVisible())
                .setLong("uid", goodsDO.getUid())
                .setLong("b", goodsDO.getBrowseTime())
                .setLong("g", goodsDO.getGoodsId())
                .setBool("v2", true);
        return session.execute(boundStatement).wasApplied();
    }

    public boolean insertGoodsWithCheck(GoodsDO goodsDO) {

        BoundStatement boundStatement = preparedInsert.bind()
                .setLong("uid", goodsDO.getUid())
                .setLong("b", goodsDO.getBrowseTime())
                .setLong("g", goodsDO.getGoodsId())
                .setBool("v", goodsDO.getIsVisible());
        return session.execute(boundStatement).wasApplied();
    }

}

package com.fairyfloss.cassandra.repository;

import com.datastax.driver.mapping.Result;
import com.fairyfloss.cassandra.accessor.GoodsAccessor;
import com.fairyfloss.cassandra.domain.GoodsDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mianhuatang on 2017/5/4.
 */
@Repository
public class CassandraRepositoryWithAccessor {

    @Autowired
    GoodsAccessor goodsAccessor;

    public List<GoodsDO> listGoodsWithAccessor(Long uid) {
        Result<GoodsDO> result = goodsAccessor.getAll(uid);
        List<GoodsDO> list = new ArrayList<>();
        for (GoodsDO goods = result.one(); goods != null; goods = result.one()) {
            System.out.println(goods);
            System.out.println(result.getAvailableWithoutFetching());
            list.add(goods);
        }
        return list;
    }

}

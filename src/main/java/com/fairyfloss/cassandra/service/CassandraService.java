package com.fairyfloss.cassandra.service;

import com.fairyfloss.cassandra.domain.GoodsDO;
import com.fairyfloss.cassandra.domain.GoodsListVO;
import com.fairyfloss.cassandra.repository.CassandraRepositoryWithAccessor;
import com.fairyfloss.cassandra.repository.CassandraRepositoryWithCondition;
import com.fairyfloss.cassandra.repository.CassandraRepositoryWithPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Created by Lindsay on 2017/5/1.
 */

@Service
public class CassandraService {
    @Autowired
    CassandraRepositoryWithPaging cassandraRepository;

    @Autowired
    CassandraRepositoryWithAccessor cassandraRepositoryWithAccessor;

    @Autowired
    CassandraRepositoryWithCondition cassandraRepositoryWithCondition;

    public void saveGoods(GoodsDO goodsDO) {
        cassandraRepository.saveGoods(goodsDO);
    }

    public GoodsDO getGoods() {
        return cassandraRepository.getGoods();
    }

    public List<GoodsDO> listGoods(long uid) {
        return cassandraRepository.listGoods(uid);
    }

    public List<GoodsDO> listFootprint(long uid) {
        return cassandraRepository.listFootprint(uid);
    }

    public GoodsListVO listGoods(long uid, byte[] pagingState) {
        return cassandraRepository.listGoods(uid, pagingState);
    }

    public List<GoodsDO> listGoodsWithAccessor(long uid) {
        return cassandraRepositoryWithAccessor.listGoodsWithAccessor(uid);
    }


    public void saveGivenGoods() {
        GoodsDO goodsDO = new GoodsDO(103L, 12345L, 303L, false);
        cassandraRepository.saveGoods(goodsDO);
    }

    public void saveGivenGoodsList() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            GoodsDO goodsDO = new GoodsDO(101L
                    , (long) random.nextInt(20000)
                    , (long) 300 + random.nextInt(100)
                    , (i % 2) == 0);
            cassandraRepository.saveGoods(goodsDO);
        }
    }

    public boolean updateGivenGoods() {
        GoodsDO goodsDO = new GoodsDO(5373807157L,1494825391L,438268L,true);
        return cassandraRepositoryWithCondition.updateGoodsWithCheck(goodsDO);
    }

    public boolean insertGivenGoods() {
        GoodsDO goodsDO = new GoodsDO(5373807157L,1494825391L,438267L,true);
        return cassandraRepositoryWithCondition.insertGoodsWithCheck(goodsDO);
    }
}

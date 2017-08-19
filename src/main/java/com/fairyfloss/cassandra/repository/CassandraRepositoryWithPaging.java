package com.fairyfloss.cassandra.repository;

import com.datastax.driver.core.PagingState;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.fairyfloss.cassandra.domain.GoodsDO;
import com.fairyfloss.cassandra.domain.GoodsListVO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.fairyfloss.cassandra.constant.CassandraConstant.TABLE_NAME;

/**
 * Created by Lindsay on 2017/5/1.
 */
@Repository
public class CassandraRepositoryWithPaging extends CassandraRepository {

    // way 1
    private Statement getStatementByPrepared(long uid, byte[] pagingState) {
        return preparedSelectGoodsByUid.bind(uid).setPagingState(PagingState.fromBytes(pagingState));
    }

    // way 2
    private Statement getStatementByQueryBuilder(int uid, byte[] pagingState) {
        return QueryBuilder.select().all().from(TABLE_NAME).setPagingState(PagingState.fromBytes(pagingState));
    }

    public GoodsListVO listGoods(long uid, byte[] pagingState) {
        Statement statement = getStatementByPrepared(uid, pagingState);
        statement.setFetchSize(4);
        if (pagingState != null) {
            statement.setPagingState(PagingState.fromBytes(pagingState));
        }

        ResultSet resultSet = session.execute(statement);
        byte[] nextPage = null;
        if (resultSet.getExecutionInfo().getPagingState() != null)
            nextPage = resultSet.getExecutionInfo().getPagingState().toBytes();
        int size = resultSet.getAvailableWithoutFetching();
        System.out.println("availabe without fetching size is " + size);
        List<GoodsDO> goodsDOList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            goodsDOList.add(toGoods(resultSet.one()));
        }
        return new GoodsListVO(goodsDOList, nextPage);

    }


}

package com.fairyfloss.cassandra.accessor;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import com.fairyfloss.cassandra.domain.GoodsDO;

import static com.fairyfloss.cassandra.constant.CassandraConstant.TABLE_NAME;

/**
 * Created by mianhuatang on 2017/5/4.
 */
@Accessor
public interface GoodsAccessor {
    @Query("SELECT * FROM " + TABLE_NAME + " where uid = ? limit 10")
    Result<GoodsDO> getAll(Long uid);

}

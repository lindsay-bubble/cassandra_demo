package com.fairyfloss.cassandra.domain;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.fairyfloss.cassandra.constant.CassandraConstant.TABLE_NAME;

/**
 * Created by Lindsay on 2017/5/1.
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TABLE_NAME)
public class GoodsDO {
    @PartitionKey
    Long uid;

    @ClusteringColumn(0)
//    @Column(name = "browse_time")
    Long browseTime;

    @ClusteringColumn(1)
//    @Column(name = "goods_id")
    Long goodsId;

//    @Column(name = "is_visible")
    Boolean isVisible;
}

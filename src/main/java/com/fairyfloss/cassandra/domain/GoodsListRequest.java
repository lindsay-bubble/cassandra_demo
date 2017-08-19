package com.fairyfloss.cassandra.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by Lindsay on 2017/5/2.
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoodsListRequest {
    long uid;
    byte[] pagingState;
}

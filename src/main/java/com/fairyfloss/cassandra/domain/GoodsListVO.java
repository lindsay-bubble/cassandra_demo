package com.fairyfloss.cassandra.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Created by Lindsay on 2017/5/2.
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoodsListVO {

    List<GoodsDO> goodsDOList;
    byte[] PagingState;
}

package com.fairyfloss.cassandra.controller;

import com.fairyfloss.cassandra.domain.GoodsDO;
import com.fairyfloss.cassandra.domain.GoodsListRequest;
import com.fairyfloss.cassandra.domain.GoodsListVO;
import com.fairyfloss.cassandra.service.CassandraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Lindsay on 2017/5/1.
 */

@RestController
//@RequestMapping("/goods/")
public class CassandraController {
    @Autowired
    CassandraService cassandraService;

    @RequestMapping("/saving/one")
    public void saveGoods(@RequestBody GoodsDO goodsDO) {
        cassandraService.saveGoods(goodsDO);
    }

    @RequestMapping("/list/{uid}")
    public List<GoodsDO> listGoods(@PathVariable long uid) {
        return cassandraService.listGoods(uid);
    }

    @RequestMapping("/listwithpaging")
    public GoodsListVO listGoods(@RequestBody GoodsListRequest goodsListRequest) {
        System.out.println(goodsListRequest);
        return cassandraService.listGoods(goodsListRequest.getUid(), goodsListRequest.getPagingState());
    }

    @RequestMapping("/list")
    public GoodsListVO listGoods(@RequestParam long uid,
                                 @RequestParam(required = false) byte[] pagingState) {
        return cassandraService.listGoods(uid, pagingState);
    }

    @RequestMapping("/listfootprint")
    public List<GoodsDO> listFootprintGoods(@RequestParam long uid) {
        return cassandraService.listFootprint(uid);
    }

    @RequestMapping("/listwithaccessor")
    public List<GoodsDO> listGoodsWithAccessor(@RequestParam long uid) {
        return cassandraService.listGoodsWithAccessor(uid);
    }


    @RequestMapping("/given/list/one")
    public GoodsDO getGoods() {
        return cassandraService.getGoods();
    }

    @RequestMapping("/given/save/one")
    public void saveGivenGoods() {
        cassandraService.saveGivenGoods();
    }

    @RequestMapping("/given/save/list")
    public void saveGivenGoodsList() {
        cassandraService.saveGivenGoodsList();
    }

    @RequestMapping("/given/update/one")
    public boolean updateGivenGoods() {
        return cassandraService.updateGivenGoods();
    }

    @RequestMapping("/given/insert/one")
    public boolean insertGivenGoods() {
        return cassandraService.insertGivenGoods();
    }
}

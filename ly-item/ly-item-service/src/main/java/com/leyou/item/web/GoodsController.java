/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: GoodsController
 * Author:   njtech
 * Date:     2019/4/1 17:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Spu;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author njtech
 * @create 2019/4/1
 * @since 1.0.0
 */
@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;


    /**
     * 功能描述: 分页查询Spu
     * @Author:小艺
     * @param
     * @return:
     * @since: 1.0.0
     * @Date: 2019/4/1 18:08
     */

    @GetMapping("spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows,
            @RequestParam(value = "key",required = false)String key,
            @RequestParam(value = "saleable", required = false)Boolean saleable
    ) {
        return ResponseEntity.ok(goodsService.querySpuByPage(page,rows,key,saleable));
    }


    /**
     * 新增：
     * 保存商品
     * @param spu
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu) {
        goodsService.saveGoods(spu);
        //return new ResponseEntity<>(HttpStatus.CREATED);//旧版本
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
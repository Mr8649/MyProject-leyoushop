package com.leyou.item.mapper;


//public interface StockMapper extends BaseMapper<Stock, Long> {
//
//    @Update("update tb_stock set stock = stock - #{num} where sku_id = #{skuId} and stock >= #{num}")
//    int decreaseStock(@Param("skuId") Long skuId, @Param("num") Integer num);
//}

import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;

//
//public interface StockMapper extends InsertListMapper {
//
//
//}
@RegisterMapper
public interface StockMapper extends InsertListMapper {

}

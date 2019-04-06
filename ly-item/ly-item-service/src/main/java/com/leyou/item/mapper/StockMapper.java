package com.leyou.item.mapper;
import com.leyou.common.mapper.BaseMapper;
import com.leyou.item.pojo.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;


/**
 * 功能描述: <br>
 * 〈〉
 *
 * @Author:小艺
 * @param 
 * @return:
 * @since: 1.0.0
 * @Date: 2019/4/6 13:25
 */

public interface StockMapper extends BaseMapper<Stock, Long> {

 @Update("update tb_stock set stock = stock - #{num} where sku_id = #{skuId} and stock >= #{num}")
 int decreaseStock(@Param("skuId") Long skuId, @Param("num") Integer num);
}

package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 功能描述: <br>
 * 〈〉
 *
 * @Author:小艺
 * @param 
 * @return:
 * @since: 1.0.0
 * @Date: 2019/4/1 17:36
 */

@Table(name = "tb_spu_detail")
@Data
public class SpuDetail {

    @Id
    private Long spuId;

    //商品描述
    private String description;

    //通用规格参数数据
    private String genericSpec;

    //特殊规格参数数据
    private String specialSpec;

    //包装清单
    private String packingList;

    //售后服务
    private String afterService;
}

package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


/**
 * 功能描述: <br>
 * 〈〉
 * @Author:小艺
 * @param 
 * @return:
 * @since: 1.0.0
 * @Date: 2019/4/2 15:39
 */

@Table(name = "tb_sku")
@Data
public class Sku {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long spuId;
    private String title;
    private String images;
    private Double price;
    /**商品特殊规格的键值对*/
    private String ownSpec;
    private String indexes;// 商品特殊规格的下标
    private Boolean enable;// 是否有效，逻辑删除用
    private Date createTime;// 创建时间
    private Date lastUpdateTime;// 最后修改时间
    @Transient
    private Integer stock;// 库存
}

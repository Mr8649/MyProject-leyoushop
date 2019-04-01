package com.leyou.item.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;


/**
 * 功能描述: <br>
 * 〈〉
 *
 * @Author:小艺
 * @param
 * @return:
 * @since: 1.0.0
 * @Date: 2019/4/1 17:30
 */

@Table(name = "tb_spu")
@Data
public class Spu {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String title;//标题
    private String subTitle;//子标题
    private Long cid1;//一级类目
    private Long cid2;//二级类目
    private Long cid3;//三级类目
    private Long brandId;//
    private Boolean saleable;//是否上架

    @JsonIgnore
    private Boolean valid;//是否有效
    private Date createTime;//创建时间

    @JsonIgnore
    private Date lastUpdateTime;//最后修改时间:当返回页面的时候就会忽略这个


    //spu所属的分类名称
    @Transient
    private String cname;

    //spu所属品牌名
    @Transient
    private String bname;

    //spu详情
    @Transient
    private SpuDetail spuDetail;

    //sku集合
    @Transient
    private List<Sku> skus;
}

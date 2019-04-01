/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SpuVO
 * Author:   njtech
 * Date:     2019/4/1 17:53
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.leyou.item.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Transient;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author njtech
 * @create 2019/4/1
 * @since 1.0.0
 */
public class SpuVO {
    private Long id;
    private String title;//标题
    private String subTitle;//子标题
    private Long cid1;//一级类目
    private Long cid2;//二级类目
    private Long cid3;//三级类目
    private Long brandId;//
    private Boolean saleable;//是否上架
    private Date createTime;//创建时间

    @JsonIgnore
    private Date lastUpdateTime;//最后修改时间


    //spu所属的分类名称
    @Transient
    private String cname;

    //spu所属品牌名
    @Transient
    private String bname;


}
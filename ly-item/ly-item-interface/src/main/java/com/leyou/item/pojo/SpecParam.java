package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
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
 * @Date: 2019/4/1 12:32
 */

@Data
@Table(name = "tb_spec_param")
public class SpecParam {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long cid;
    private Long groupId;
    private String name;
    @Column(name = "`numeric`")
    private Boolean numeric;
    private String unit;
    private Boolean generic;
    private Boolean searching;
    private String segments;
}

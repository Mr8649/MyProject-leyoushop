package com.leyou.common.vo;

import lombok.Data;
import java.util.List;

/**
 * @author 小艺
 * @date 2019/3/28
 * 功能：这个是一个视图对象，返回表信息和总条数
 */
@Data
public class PageResult<T> {

    private Long total;//总条数
    private Integer totalPage;//总页数
    private List<T> items;//当前页数

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Integer totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }


}

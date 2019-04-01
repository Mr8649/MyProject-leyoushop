package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

//新定义的接口继承Mapper,并且在<>使用Category的类型，导入相关的包
public  interface CategoryMapper extends Mapper<Category>, IdListMapper<Category, Long> {

}

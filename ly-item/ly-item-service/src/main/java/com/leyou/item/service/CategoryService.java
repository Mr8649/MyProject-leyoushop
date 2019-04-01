/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CategoryService
 * Author:   小艺
 * Date:     2019/3/14 15:10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.leyou.item.service;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
/**
 * 〈一句话功能简述〉<br>
 *     这个是商品微服务的实现类
 * 〈〉
 *
 * @author 小艺
 * @create 2019/3/14
 * @since 1.0.0
 */
//导入service支持
@Service
public class CategoryService {
    //注入Mapper
    @Autowired
    private  CategoryMapper categoryMapper;//如果引用不成功就会变成灰色
    //private CategoryMapper categoryMapper;改成下面的
    public List<Category> queryCategoryListByPid(Long pid){
        //查询条件，mapper会把对象中的非空属性作为查询条件
        Category t=new Category();
        t.setParentId(pid);
        List<Category> list=categoryMapper.select(t);
        //判断查询结果
        if(CollectionUtils.isEmpty(list)){
                throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return list;
    }
//然后再web里面写categoryController


    public List<Category> queryByIds(List<Long> ids) {
        //return categoryMapper.selectByIdList(ids);
        List<Category> list=categoryMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return list;
    }
}
/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SpecificationService
 * Author:   njtech
 * Date:     2019/4/1 11:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.pojo.SpecGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author njtech
 * @create 2019/4/1
 * @since 1.0.0
 */
@Service
public class SpecificationService {
    @Autowired
    private SpecGroupMapper groupMapper;

    public List<SpecGroup> queryGroupByCid(Long cid){
        //查询条件
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        //开始查询
        List<SpecGroup> list= groupMapper.select(group);
        if (CollectionUtils.isEmpty(list)) {
            //没有查找到
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        return list;
    }



}
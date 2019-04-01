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
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
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
    @Autowired
    private SpecParamMapper paramMapper;

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


    /***
     * 功能：这个是编辑商品规格参数
     * @param gid
     * @param cid
     * @param searching
     * @param generic
     * @return
     */

    public List<SpecParam> queryParamByCid(Long gid, Long cid, Boolean searching, Boolean generic) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        param.setGeneric(generic);
        List<SpecParam> list = paramMapper.select(param);
        if (CollectionUtils.isEmpty(list)) {
            //没查到
            throw new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }
        return list;
    }

    /**
     * 这个是保存规格分组
     * @param param
     */
    public void saveSpecGroup(SpecGroup param) {
        int count = groupMapper.insert(param);
        if (count != 1) {
            throw new LyException(ExceptionEnum.SPEC_GROUP_CREATE_FAILED);
        }
    }

    /**
     * 删除规格分组
     * @param id
     */
    public void deleteSpecGroup(Long id) {
        if (id == null) {
            throw new LyException(ExceptionEnum.INVALID_PARAM);
        }
        SpecGroup group = new SpecGroup();
        group.setId(id);
        int count = groupMapper.deleteByPrimaryKey(group);
        if (count != 1) {
            throw new LyException(ExceptionEnum.DELETE_SPEC_GROUP_FAILED);
        }
    }


    /**
     * 更新编辑后的规格分组
     * @param group
     */
    public void updateSpecGroup(SpecGroup group) {
        int count = groupMapper.updateByPrimaryKey(group);
        if (count != 1) {
            throw new LyException(ExceptionEnum.UPDATE_SPEC_GROUP_FAILED);
        }
    }

    /**
     * 保存商品参数
     * @param param
     */
    public void saveSpecParam(SpecParam param) {
        int count = paramMapper.insert(param);
        if (count != 1) {
            throw new LyException(ExceptionEnum.SPEC_PARAM_CREATE_FAILED);
        }
    }

    /**
     * 删除商品参数
     * @param id
     */
    public void deleteSpecParam(Long id) {
        if (id == null) {
            throw new LyException(ExceptionEnum.INVALID_PARAM);
        }
        int count = paramMapper.deleteByPrimaryKey(id);
        if (count != 1) {
            throw new LyException(ExceptionEnum.DELETE_SPEC_PARAM_FAILED);
        }
    }

    /**
     * 更新商品规格参数
     * @param param
     */
    public void updateSpecParam(SpecParam param) {
        int count = paramMapper.updateByPrimaryKeySelective(param);
        if (count != 1) {
            throw new LyException(ExceptionEnum.UPDATE_SPEC_PARAM_FAILED);
        }
    }
}
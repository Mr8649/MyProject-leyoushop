package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author 小艺
 * @create 2019/3/19
 * @since 1.0.0
 */
@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> queryBrandByPageAndSort(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        // 开始分页

        PageHelper.startPage(page, rows);
        // 过滤
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)) {
            String st = "%"+key+"%";
            criteria.andLike("name",st).orEqualTo("letter", key.toUpperCase());
        }
        if (StringUtils.isNotBlank(sortBy)) {
            // 排序
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        // 查询
        List<Brand> brands = brandMapper.selectByExample(example);
        //System.out.println(brands.get(0));
        // List<Brand> categories = brandMapper.selectByExample(example);
        //Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);
        //自己添加了这一段代码
        if (CollectionUtils.isEmpty(brands)){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }

        //PageInfo pageInfo = new PageInfo(brands);这个是原版本
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);

        // 返回结果
        //原来的版本
        //return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
        return new PageResult<>(pageInfo.getTotal(),brands);
    }
//
//    @Transactional
//    public void save(Brand brand, List<Long> cids) {
//
//
//        brandMapper.insertSelective(brand);
//
//
//        for (Long ci : cids) {
//            brandMapper.saveBrandCategory(brand.getId(),ci);
//        }
//
//    }
//
//    public List<Brand> queryBrandList(Long cid) {
//        return brandMapper.queryBrandList(cid);
//    }
//
//    public Brand queryByid(Long id) {
//        return brandMapper.selectByPrimaryKey(id);
//    }
//
//    public List<Brand> queryByIds(List<Long> ids) {
//        List<Brand> list = brandMapper.selectByIdList(ids);
//        return list;
//    }
}
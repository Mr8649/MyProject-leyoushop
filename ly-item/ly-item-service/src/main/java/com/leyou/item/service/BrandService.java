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
import org.springframework.transaction.annotation.Transactional;
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

    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        // 开始分页
        PageHelper.startPage(page, rows);
        // 条件过滤
//        Example example = new Example(Brand.class);
//        Example.Criteria criteria = example.createCriteria();
//        if (StringUtils.isNotBlank(key)) {
//            String st = "%" + key + "%";
//            criteria.orLike("name", st).orEqualTo("letter", key.toUpperCase());
//        }

        //过滤条件：老师版本
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)) {
            //String st = "%" + key + "%";
            example.createCriteria().orLike("name", "%" + key + "%")
                  .orEqualTo("letter", key.toUpperCase());
        }

        if (StringUtils.isNotBlank(sortBy)) {
            // 排序
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<Brand> list = brandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        //解析分页结果
        PageInfo<Brand> info = new PageInfo<>(list);
        return new PageResult<>(info.getTotal(), list);
    }

    /**insert和insertSeclect的区别就是：
     * insertSeclect()是要有选择性的安装,如果没有插入的字段那么就是插入为空，有的字段就插入
     * insert()是要求对应所有的字段进行插入
     */
    //1代表成功0代表失败
    //可用可不用brand.setId(null);

    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        brand.setId(null);
      int count=brandMapper.insert(brand);
      if(count!=1){
          throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
      }
      //要继续新增中间表===中间表没有实体类那么该怎么新增呢？===因为cid又有很多个，所以可以通过数组遍历来进行获取
        for(Long cid : cids){
            count=brandMapper.insertCategoryBrand(cid ,brand.getId());
            if(count!=1){
                throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
            }
        }
    }

}


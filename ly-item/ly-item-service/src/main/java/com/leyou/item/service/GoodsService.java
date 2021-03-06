package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.mapper.StockMapper;
import com.leyou.item.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author njtech
 * @create 2019/4/1
 * @since 1.0.0
 */
@Service
public class GoodsService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    //private SpuDetailMapper spuDetailMapper;
    private SpuDetailMapper detailMapper;

    @Autowired
   private CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;



    /**
     *
     * @param page
     * @param rows
     * @param key
     * @param saleable
     * @return
     */
    public PageResult<Spu> querySpuByPage(Integer page, Integer rows, String key, Boolean saleable) {
        //分页
        PageHelper.startPage(page, rows);
        //过滤
        Example example = new Example(Spu.class);
          //搜索字段过滤
        Example.Criteria criteria = example.createCriteria();

        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        //上下架过滤
        if (saleable != null) {
            criteria.andEqualTo("saleable", saleable);//原版是orEqualTo
        }
        //默认以上一次更新时间排序
        example.setOrderByClause("last_update_time desc");//原版
        //example.setOrderByClause("lastUpdateTime DESC");

        //只查询未删除的商品
        criteria.andEqualTo("valid", 1);

        //查询
        List<Spu> spus = spuMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(spus)){
            throw new LyException(ExceptionEnum.GOODS_NOT_FOUND);
        }
        //对查询结果中的分类名和品牌名进行处理，解析
        //handleCategoryAndBrand(spus);原版本
        loadCategoryAndBrandName(spus);

        //解析分页结果
        PageInfo<Spu> info = new PageInfo<>(spus);

        return new PageResult<>(info.getTotal(), spus);
    }

    private void loadCategoryAndBrandName(List<Spu>spus){
        for (Spu spu : spus) {
            //根据spu中的分类ids查询分类名
            //处理分类名称
            //原版本是names
            List<String> names = categoryService.queryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream()
                    .map(Category::getName)
                    .collect(Collectors.toList());
            //对分类名进行处理
            spu.setCname(StringUtils.join(names, "/"));

            //查询品牌
            //spu.setBname(brandMapper.selectByPrimaryKey(spu.getBrandId()).getName());//老版本
            spu.setBname(brandService.queryById(spu.getBrandId()).getName());
        }
    }


    /**
     * 功能描述:新增商品的服务
     * 〈〉
     *
     * @Author:小艺
     * @param
     * @return:
     * @since: 1.0.0
     * @Date: 2019/4/2 15:52
     */
//@Transactional
//    public void saveGoods(Spu spu) {
//    //新增spu
//    //添加商品要添加四个表 spu, spuDetail, sku, stock四张表
//    spu.setSaleable(true);
//    //spu.setValid(true);//原版本
//    spu.setValid(false);
//    spu.setId(null);
//    spu.setCreateTime(new Date());
//    spu.setLastUpdateTime(spu.getCreateTime());
//
//    //插入数据
//    int count = spuMapper.insert(spu);
//    if (count != 1) {
//        throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
//    }
//
//    //插入detail数据
//    SpuDetail detail = spu.getSpuDetail();
//    detail.setSpuId(spu.getId());
//    detailMapper.insert(detail);
//
//
//    //定义一个库存集合
//    //List<Stock> stockList=new ArrayList<>();
//
//
//    //新增SKU
//    List<Sku> skus = spu.getSkus();
//    for (Sku sku : skus) {
//        sku.setCreateTime(new Date());
//        sku.setLastUpdateTime(sku.getLastUpdateTime());
//        sku.setSpuId(spu.getId());
//
//
//        count = skuMapper.insert(sku);
//        if (count != 1) {
//            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
//        }
//
//
//        /**采用了新增以后就不需要再进行一条一条的插入了,应该采用批量的插入
//         *  count = stockMapper.insert(stock);
//         *             if (count != 1) {
//         *                 throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
//         *             }
//         */
//
//        //插入sku和库存:新增detail
//        Stock stock = new Stock();
//        stock.setSkuId(sku.getId());
//        stock.setStock(sku.getStock());
//        count = stockMapper.insert(stock);
//        if (count != 1) {
//            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
//        }
//
//        //stockList.add(stock);
//        //新增sku和库存====后面才添加的
//        saveSkuAndStock(spu);
//    }

    @Transactional
  public void saveGoods(Spu spu) {
        //新增spu
        spu.setId(null);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(false);

        int count=spuMapper.insert(spu);
        if(count!=1){
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
        }

        //新增detail
        SpuDetail detail=spu.getSpuDetail();
        detail.setSpuId(spu.getId());
        detailMapper.insert(detail);
        saveSkuAndStock(spu);
    }


    private void saveSkuAndStock(Spu spu){
        int count;
        List<Stock>stockList=new ArrayList<>();
        //新增sku
        List<Sku> skus=spu.getSkus();
        for(Sku sku:skus){
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());

            count=skuMapper.insert(sku);
            if(count!=1){
                throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
            }

            //新增库存
            Stock stock=new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());

            stockList.add(stock);
        }

        //批量新增库存
        count=stockMapper.insertList(stockList);

        if (count!=stockList.size()){
            throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);

        }

    }

//}
//        private void saveSkuAndStock(Spu spu){
//            int count;
//            List<Stock>stockList=new ArrayList<>();
//            //新增sku
//            List<Sku> skus=spu.getSkus();
//            for(Sku sku:skus){
//                sku.setCreateTime(new Date());
//                sku.setLastUpdateTime(sku.getCreateTime());
//                sku.setSpuId(spu.getId());
//
//                count=skuMapper.insert(sku);
//                if(count!=1){
//                    throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
//                }
//
//                //新增库存
//                Stock stock=new Stock();
//                stock.setSkuId(sku.getId());
//                stock.setStock(sku.getStock());
//
//                stockList.add(stock);
//            }
//
//            //批量新增库存
//            count=stockMapper.insertList(stockList);
//
//            if (count!=stockList.size()){
//                throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
//
//            }
//
//    }

    /**
     * 查询详情
     * @param spuId
     * @return
     */
    public SpuDetail queryDetailById(Long spuId) {
        SpuDetail detail = detailMapper.selectByPrimaryKey(spuId);
        if (detail==null){
            throw new LyException(ExceptionEnum.GOODS_DETAIL_NOT_FOUND);
        }
        return detail;
    }

    public List<Sku> querySkuBySpuId(Long spuId) {
        //查询sku
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(sku);
        if (CollectionUtils.isEmpty(skuList)){
            throw new LyException(ExceptionEnum.GOODS_SKU_NOT_FOUND);
        }

        //查询库存：方法一：
//        for (Sku s : skuList) {
//            Stock stock=stockMapper.selectByPrimaryKey(s.getId());
//           if (stock==null){
//               throw  new LyException(ExceptionEnum.STOCK_NOT_ENOUGH);
//           }
//           s.setStock(stock.getStock());
//        }

        //批量查询:方法二
        List<Long> ids=skuList.stream().map(Sku::getId).collect(Collectors.toList());
        List<Stock> stockList=stockMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(stockList)){
            throw  new LyException(ExceptionEnum.STOCK_NOT_ENOUGH);
        }

        //我们把stoct变成个map，其key是：sku的id,值是库存值
        Map<Long,Integer> stockMap=stockList.stream()
                .collect(Collectors.toMap(Stock::getSkuId,Stock::getStock));
        skuList.forEach(s->s.setStock(stockMap.get(sku.getId())));

        return skuList;
    }


    @Transactional
    public void updateGoods(Spu spu){
        if (spu.getId()==null){
            throw  new LyException(ExceptionEnum.GOODS_ID_CANNOT_BE_NULL);
        }



        Sku sku=new Sku();
        sku.setSpuId(spu.getId());
        //查询
        List<Sku>skuList=skuMapper.select(sku);
        if(CollectionUtils.isEmpty(skuList)){
            //删除sku和stock
            skuMapper.delete(sku);
            //删除stock
             List<Long> ids = skuList.stream().map(Sku::getId).collect(Collectors.toList());
             stockMapper.deleteByIdList(ids);

        }

        //修改spu
        spu.setValid(null);
        spu.setSaleable(null);
        spu.setLastUpdateTime(new Date());
        spu.setCreateTime(null);

        int count=spuMapper.updateByPrimaryKeySelective(spu);
        if(count!=1){
            throw  new LyException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }

        //修改detail
         count = detailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
        if(count!=1){
            throw  new LyException(ExceptionEnum.GOODS_UPDATE_ERROR);
        }

        //新增sku和stock
        saveSkuAndStock(spu);



    }
}
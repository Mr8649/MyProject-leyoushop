/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: CategoryController
 * Author:   小艺
 * Date:     2019/3/14 15:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.leyou.item.web;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author 小艺
 * @create 2019/3/14
 * @since 1.0.0
 */

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

  //因为请求参数是get
    /**
     * 根据父节点的id查询商品的分类
     * @param pid
     * @reture
     *
     * */
    @GetMapping("list")
    //因为返回值是一个集合，所以用List来进行接受
    //需要完善下面的代码
    //public List<CategoryController> queryCategoryListByPid(@RequestParam("pid")Long pid){
    public ResponseEntity<List<Category>> queryCategoryListByPid(@RequestParam("pid")Long pid){
     /*
     * 下面是返回结果的传统方法：
     * return ResponseEntity.status(HttpStatus.OK).body(null));
     *
     * 但是这种方法我们不用，因为太麻烦，而且不好。下面是简写的方式
     *
     * */
     return ResponseEntity.ok(categoryService.queryCategoryListByPid(pid));


    }



}
package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 分页查询品牌
 *
 * @author 小艺
 * @create 2019/3/19
 * @since 1.0.0
 */
@RestController
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key) {

        return ResponseEntity.ok(brandService.queryBrandByPage(page,rows,sortBy,desc, key));
}


/**
 * 功能描述:品牌新增
 * 〈〉
 *
 * @Author:小艺
 * @param
 * @return: Void
 * @since: 1.0.0
 * @Date: 2019/3/29 12:56
 */
@PostMapping
public  ResponseEntity<Void> saveBrand(Brand brand, @RequestParam("cids") List<Long> cids){

    //System.out.println(brandBo);
    //brandService.save(brand,cids);//旧版本
    brandService.saveBrand(brand,cids);
    //如果有解析那么就返回body，没有解析就返回build
    return ResponseEntity.status(HttpStatus.CREATED).build();
}

}


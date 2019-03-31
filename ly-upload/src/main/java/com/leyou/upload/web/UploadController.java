package com.leyou.upload.web;

import com.leyou.upload.service.UploadService.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("upload")
public class UploadController {

    /**
     * 功能描述: 上传图片的功能
     * 〈〉
     *
     * @Author:小艺
     * @param
     * @return:
     * @since: 1.0.0
     * @Date: 2019/3/29 22:13
     */

    @Autowired
 private  UploadService uploadService;
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file){
        //String upload = uploadService.upload(file);
        //return ResponseEntity.ok(upload);
        String url = uploadService.uploadImage(file);
        return ResponseEntity.ok(url);
        //简写的形式如下：===return ResponseEntity.ok(uploadService.uploadImage(file));
    }
}

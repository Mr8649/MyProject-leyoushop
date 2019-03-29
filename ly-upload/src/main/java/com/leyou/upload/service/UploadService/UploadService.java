package com.leyou.upload.service.UploadService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class UploadService {

    public String uploadImage(MultipartFile file) {
        //准备目标路径
        this.getClass().getClassLoader().getResource("").getFile();
        File dest=new File("static");
        //保存文件到本地
        file.transferTo(dest);


        //返回路径

        return null;
    }
}


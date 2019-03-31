package com.leyou.upload.service.UploadService;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.upload.config.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
//@Resource

public class UploadService {
    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private UploadProperties prop;
    // 支持的文件类型
    //private static final List<String> ALLOW_TYPES = Arrays.asList("image/png", "image/jpeg", "image/bmp", "image/jpg");

    public String uploadImage(MultipartFile file) {

        //this.getClass().getClassLoader().getResource("").getFile();
        try {
            /**
             * 其实图片上传就是基本上就是两个流程，一个是准备路径，一个是保存
             * ====但是要完善一下，图片来了以后我需要进行在上传之前做校验(一般校验后缀名)
             */
            //1：校验
            //1）校验文件类型====怎么去定义一个文件的类型？===》放到配置文件中
            String contentType = file.getContentType();
            //if (!ALLOW_TYPES.contains(contentType)) {
            if (!prop.getAllowTypes().contains(contentType)) {
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            //2）当文件类型为空的时候，或者出于安全考虑，还需要对文件的内容进行校验
            BufferedImage image = ImageIO.read(file.getInputStream());
            //下面image==null是有一种校验方法，还有有一种是通过校验文件的长宽高等属性来判断的
            if (image == null) {
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }

            //准备目标路径
            //File dest = new File("C:\\Users\\njtech\\Desktop\\upload", file.getOriginalFilename());//这个是保存在本地，当使用FastDFS后就需要改变了
            //保存文件到本地
            //file.transferTo(dest);

            //上传到FastDFS
            //String extension=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            //上一句代码完善如下：
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);

            //返回路径(返回一个可以访问的路径，一般用域名访问呢)
            //return "http://image.leyou.com/" +storePath;
            return prop.getBaseUrl() +storePath;
            //return "http://image.leyou.com/" + file.getOriginalFilename();
        } catch (IOException e) {
            //上传失败
            log.error("[文件上传]上传文件失败", e);//上传文件失败并且打出日志
            throw new LyException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }
    }
}


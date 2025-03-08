package com.wjh.picturebackend.controller;

import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.utils.IOUtils;
import com.wjh.picturebackend.annotation.AuthCheck;
import com.wjh.picturebackend.common.BaseResponse;
import com.wjh.picturebackend.common.ResultUtils;
import com.wjh.picturebackend.constant.UserConstant;
import com.wjh.picturebackend.exception.BusinessException;
import com.wjh.picturebackend.exception.ErrorCode;
import com.wjh.picturebackend.manager.CosManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;


/**
 * @Author 
 * @Date 2025/2/5 19:08:09
 * @Description 文件上传接口
 */
@RestController
@RequestMapping("/file")
@Api(tags = "文件接口")
@Slf4j
@SuppressWarnings("all")
public class FileController {

    private static final Logger log = LoggerFactory.getLogger(FileController.class);
    private final CosManager cosManager;

    public FileController(CosManager cosManager) {
        this.cosManager = cosManager;
    }

    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/test/upload")
    @ApiOperation(value = "测试上传")
    public BaseResponse<String> testUpload(@RequestPart("file") MultipartFile multipartFile) {
        //文件目录
        String originalFilename = multipartFile.getOriginalFilename();
        String filePath = String.format("/test/%s", originalFilename);
        File file = null ;
        try {
            //上传文件
            file  = File.createTempFile(filePath, null);
            multipartFile.transferTo(file);
            cosManager.putObjectResult(filePath, file);
            //返回可访问的地址
            return ResultUtils.success(filePath);
        } catch (Exception e) {
            log.error("file upload error , filePath = {}" , filePath  + "Exception = {} " +  e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR , "上传失败");
        }finally {
            if (file != null) {
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error , filePath = {}" , filePath);
                }
            }
        }
    }

    @PostMapping("/download")
    @ApiOperation(value = "测试文件下载")
    public void download(String filePath , HttpServletResponse response) throws IOException {
        COSObjectInputStream inputStream = null;
        try {
            COSObject objectResult = cosManager.getObjectResult(filePath);
            inputStream = objectResult.getObjectContent();
            byte[] byteArray = IOUtils.toByteArray(inputStream);

            //设置响应头
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + filePath);

            // 写入响应
            response.getOutputStream().write(byteArray);
            response.getOutputStream().flush();

        } catch (IOException e) {
            log.error("file download error , filepath = " + filePath + " Exception  = {}" , e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR , "下载失败");
        }finally {
            // 释放流
            if (inputStream != null){
                inputStream.close();
            }
        }

    }
}

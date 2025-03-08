package com.wjh.picturebackend.controller;

import com.wjh.picturebackend.common.BaseResponse;
import com.wjh.picturebackend.common.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 
 * @Date 2025/2/2 23:29:28
 */
@RestController
@RequestMapping("/")
@Api(tags = "健康检查")
public class MainController {
    //健康检查
    @PostMapping("/health")
    @ApiOperation(value = "健康检查")
    public BaseResponse<String> health() {
        return ResultUtils.success("Hello World");
    }
}

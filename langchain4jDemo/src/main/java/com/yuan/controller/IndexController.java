package com.yuan.controller;

import cn.hutool.core.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
     * 映射主页的请求处理方法
     * 该方法处理GET请求，并返回带有当前时间的欢迎信息
     *
     * @return 返回包含当前时间的字符串，用于在主页上显示
     */
    @GetMapping("/")
    public String index() {
        // 记录主页方法的调用信息
        logger.info("index()");
        // 返回欢迎信息和当前时间的组合字符串
        return "helloworld " + DateUtil.now();
    }


}

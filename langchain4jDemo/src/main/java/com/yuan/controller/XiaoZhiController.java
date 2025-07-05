package com.yuan.controller;

import com.alibaba.fastjson.JSON;
import com.yuan.assistant.XiaoZhiAgent;
import com.yuan.bean.XiaoZhiChatForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "XiaoZhiController_硅谷小智", description = "XiaoZhiController")
@RestController
@RequestMapping("/xiaozhi")
public class XiaoZhiController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(XiaoZhiController.class);

    @Autowired
    private XiaoZhiAgent xiaoZhiAgent;

    /**
     * 开启对话
     *
     * @param chatForm
     * @return
     */
    @Operation(summary = "XiaoZhiController_开始对话")
    @PostMapping("/chat")
    public String chat(@RequestBody XiaoZhiChatForm chatForm) {
        logger.info("XiaoZhiController.chat() {}", JSON.toJSONString(chatForm));
        return xiaoZhiAgent.chat(chatForm.getMemoryId(), chatForm.getMessage());
    }


}

package com.yuan;

import com.yuan.assistant.SeparateChatAssistant;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LangChainDemoMain.class)
public class PromptTest {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(PromptTest.class);

    @Autowired
    private SeparateChatAssistant separateChatAssistant;


    @Test
    public void testPrompt() {
        ObjectId objectId = new ObjectId("64c5c5c5c5c5c5c5c5c5c5c5");
        String result = separateChatAssistant.chat(objectId, "我是hanx");
        logger.info("result: {}", result);

        String result1 = separateChatAssistant.chat(objectId, "你知道我是谁吗");
        logger.info("result1: {}", result1);

        String result2 = separateChatAssistant.chat(objectId, "你知道我是谁吗");
        logger.info("result2: {}", result2);
    }


    @Test
    public void testPrompt1() {
        ObjectId objectId = new ObjectId("64c5c5c5c5c5c5c5c5c5c5c5");

        String result1 = separateChatAssistant.chat(objectId, "让你猜猜我是谁");
        logger.info("result1: {}", result1);

    }


    /**
     * 西安话 交互 测试
     * 2025年7月1日22:26:42
     * liusc
     */
    @Test
    public void testPrompt2() {
        ObjectId objectId = new ObjectId("000000000000000000000001");

        String result1 = separateChatAssistant.chatXian(objectId, "我是未来穿越回来的,我是谁?今天是几号了?");
        logger.info("result1: {}", result1);

    }


    /**
     * 北京话 提示词模板测试
     */
    @Test
    public void testPrompt3() {
        ObjectId objectId = new ObjectId("000000000000000000000002");
        String result1 = separateChatAssistant.chatTemplate(objectId, "我是hanx,今天是几号了?");
        logger.info("result1: {}", result1);

    }


}

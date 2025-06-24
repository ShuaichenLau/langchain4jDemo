package com.yuan;


import dev.langchain4j.model.ollama.OllamaChatModel;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * deepseek-r1 demo 测试
 * 2025年6月24日21:05:28
 * by liusc
 */
@SpringBootTest(classes = LangChainDemoMain.class)
public class Deepseekr1Test {

    private final Logger logger = LoggerFactory.getLogger(Deepseekr1Test.class);


    /**
     * deepseek-r1
     */
    @Autowired
    private OllamaChatModel ollamaChatModel;

    /**
     * deepseek-r1
     */
    @Test
    public void testSpringBoot() {
        String chat = ollamaChatModel.chat("我是谁?");
        System.out.println(chat);
        logger.info(chat);
    }


}

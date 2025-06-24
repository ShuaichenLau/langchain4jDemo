package com.yuan;


import dev.langchain4j.community.model.dashscope.QwenChatModel;
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
public class QianWenTest {

    private final Logger logger = LoggerFactory.getLogger(QianWenTest.class);


    /**
     * 阿里通义百炼平台  千问
     */
    @Autowired
    private QwenChatModel qwenChatModel;

    /**
     * 阿里通义百炼平台  千问
     * 2025年6月24日22:43:01
     */
    @Test
    public void testSpringBoot() {
        String chat = qwenChatModel.chat("我是谁?");
        System.out.println(chat);
        logger.info(chat);
    }


}

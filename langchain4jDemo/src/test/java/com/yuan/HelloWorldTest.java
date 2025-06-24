package com.yuan;


import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * demo 测试
 * 2025年6月24日21:05:28
 * by liusc
 */
@SpringBootTest(classes = LangChainDemoMain.class)
public class HelloWorldTest {

    private final Logger logger = LoggerFactory.getLogger(HelloWorldTest.class);

    /**
     *
     */
    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Test
    public void testSpringBoot() {
        String chat = openAiChatModel.chat("我是谁?");
//        System.out.println(chat);
        logger.info(chat);
    }

    /**
     *
     */
    @Test
    public void test() {

        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo").modelName("gpt-4o-mini")
                .build();

        String out = model.chat("你好,你是谁?");
        System.out.println(out);


        String chat = model.chat("你可以做什么？");

        System.out.println(chat);

        System.out.println("HelloWorld");


    }
}

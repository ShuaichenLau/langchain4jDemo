package com.yuan;


import dev.langchain4j.model.openai.OpenAiChatModel;
import com.yuan.LangChainDemoMain;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * demo 测试
 * 2025年6月24日21:05:28
 * by liusc
 */
@SpringBootTest(classes = LangChainDemoMain.class)
public class HelloWorldTest {


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

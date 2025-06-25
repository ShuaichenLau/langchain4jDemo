package com.yuan;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LangChainDemoMain.class)
public class AIServiceTest {

    @Autowired
    private QwenChatModel qwenChatModel;

    @Test
    public void testChat(){
        Assistant assistant = AiServices.create(Assistant.class, qwenChatModel);
        String answer = assistant.chat("who are you");
        System.out.println(answer);
    }


    @Autowired
    private AssistantV1 assistantV1;
    @Test
    public void testChat1(){
        String answer = assistantV1.chat("你是谁");
        System.out.println(answer);
    }

}

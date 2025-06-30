package com.yuan;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest(classes = LangChainDemoMain.class)
public class AIServiceTest {

    private Logger logger = LoggerFactory.getLogger(AIServiceTest.class);

    @Autowired
    private QwenChatModel qwenChatModel;


    /**
     * 聊天记忆实现
     */
    @Test
    public void testChatMemory3(){

        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        Assistant assistant = AiServices.builder(Assistant.class).chatLanguageModel(qwenChatModel)
                .chatMemory(chatMemory).build();

        String chat1 = assistant.chat("我是hanx");
        logger.info("1输出大语言模型回复 : {}", chat1);


        String chat2 = assistant.chat("你能说出我是谁吗?");
        logger.info("2输出大语言模型回复 : {}", chat2);
    }



    /**
     * 简单的聊天记忆
     */
    @Test
    public void testChatMemory2() {
        UserMessage userMessage1 = UserMessage.userMessage("我是hanx");
        ChatResponse chatResponse1 = qwenChatModel.chat(userMessage1);
        AiMessage aiMessage1 = chatResponse1.aiMessage();
        // 输出大语言模型回复
        logger.info("1输出大语言模型回复 : {}", aiMessage1.text());


        //第2轮对话
        UserMessage userMessage2 = UserMessage.userMessage("你可以说出我的名字吗?");
        ChatResponse chatResponse2 = qwenChatModel.chat(Arrays.asList(userMessage1, aiMessage1, userMessage2));
        AiMessage aiMessage2 = chatResponse2.aiMessage();
        // 输出大语言模型回复
        logger.info("2输出大语言模型回复 : {}", aiMessage2.text());

    }


    @Test
    public void testChat() {
        Assistant assistant = AiServices.create(Assistant.class, qwenChatModel);
        String answer = assistant.chat("who are you");
        System.out.println(answer);
    }


    @Autowired
    private AssistantV1 assistantV1;

    @Test
    public void testChat1() {
        String answer = assistantV1.chat("你是谁");
        System.out.println(answer);
    }

}

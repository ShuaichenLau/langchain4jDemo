package com.yuan;

import cn.hutool.core.date.DateUtil;
import com.yuan.assistant.Assistant;
import com.yuan.assistant.AssistantV1;
import com.yuan.assistant.MemoryChatAssistant;
import com.yuan.assistant.SeparateChatAssistant;
import com.yuan.assistant.SeparateChatAssistantOld;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.AiServices;
import org.bson.types.ObjectId;
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

    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Autowired
    private SeparateChatAssistantOld separateChatAssistantOld;


    /**
     * 测试聊天记忆功能
     *
     * @v注解的使用
     */
    @Test
    public void testChatMemory8() {

        logger.info("测试 @V 注解 {}", DateUtil.now());
        ObjectId memoryId = new ObjectId("63f7f5f5f5f5f5f5f5f5f5f5");

        String chatV1 = separateChatAssistant.chatV1(memoryId, "Hello, 你好啊， 你知道我是谁谁吗？");
        logger.info("测试@v注解  输出大语言模型回复 : {}", chatV1);

    }

    /**
     *
     */
    @Test
    public void testChatMemory9() {

        logger.info("测试 @V 注解 {}", DateUtil.now());
        ObjectId memoryId = new ObjectId("63f7f5f5f5f5f5f5f5f5f5f5");
        String userName = "hanx";
        int age = 18;

        String chatV1 = separateChatAssistant.chatV2(memoryId, "Hello, 你好啊， 你知道我是谁谁吗？知道我多大吗?", userName, age);
        logger.info("测试@v注解  输出大语言模型回复 : {}", chatV1);

    }


    @Test
    public void testChatMemory7() {

        ObjectId memoryId = new ObjectId("63f7f5f5f5f5f5f5f5f5f5f5");

        String chat1 = separateChatAssistant.chat(memoryId, "我是hanx");
        logger.info("1输出大语言模型回复 : {}", chat1);

        String chat2 = separateChatAssistant.chat(memoryId, "你能说出我是谁吗?");
        logger.info("2输出大语言模型回复 : {}", chat2);

        String chat3 = separateChatAssistant.chat(memoryId, "你能说出我是谁吗?");
        logger.info("3输出大语言模型回复 : {}", chat3);

        String chat4 = separateChatAssistant.chat(memoryId, "你是哪一种模型？");
        logger.info("4输出大语言模型回复 : {}", chat4);
    }

    @Test
    public void testChatMemory5() {

        String chat1 = separateChatAssistantOld.chatOld(1, "我是hanx");
        logger.info("1输出大语言模型回复 : {}", chat1);

        String chat2 = separateChatAssistantOld.chatOld(1, "你能说出我是谁吗?");
        logger.info("2输出大语言模型回复 : {}", chat2);

        String chat3 = separateChatAssistantOld.chatOld(2, "你能说出我是谁吗?");
        logger.info("3输出大语言模型回复 : {}", chat3);

        String chat4 = separateChatAssistantOld.chatOld(1, "你是哪一种模型？");
        logger.info("4输出大语言模型回复 : {}", chat4);
    }

    /**
     * 聊天记忆实现
     */
    @Autowired
    private MemoryChatAssistant memoryChatAssistant;


    /**
     * 添加提示词  添加表情符号
     */
    @Test
    public void testChatMemory6() {
        String chat1 = memoryChatAssistant.chatV1("我是hanx");
        logger.info("1输出大语言模型回复 : {}", chat1);
        String chat2 = memoryChatAssistant.chatV1("你能说出我是谁吗?");
        logger.info("2输出大语言模型回复 : {}", chat2);
    }

    /**
     * 聊天记忆实现
     */
    @Test
    public void testChatMemory4() {
        String chat1 = memoryChatAssistant.chat("我是hanx");
        logger.info("1输出大语言模型回复 : {}", chat1);
        String chat2 = memoryChatAssistant.chat("你能说出我是谁吗?");
        logger.info("2输出大语言模型回复 : {}", chat2);
    }


    /**
     * 聊天记忆实现
     */
    @Test
    public void testChatMemory3() {
        // 创建一个“最多保存最近 10 条消息”的记忆对象。超过的旧消息将被丢弃，只保留最新的 10 条对话内容。
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
        // 构建一个助理对象，使用指定的语言模型和上述记忆对象。
        Assistant assistant = AiServices.builder(Assistant.class).chatLanguageModel(qwenChatModel)
                .chatMemory(chatMemory).build();

        // 用户发送第一句消息，测试记忆功能。
        String chat1 = assistant.chat("我是hanx");
        // 记录第一次聊天的回复内容。
        logger.info("1输出大语言模型回复 : {}", chat1);

        // 用户发送第二句消息，进一步测试记忆功能和对话上下文的关联。
        String chat2 = assistant.chat("你能说出我是谁吗?");
        // 记录第二次聊天的回复内容，检验记忆功能是否正常工作。
        logger.info("2输出大语言模型回复 : {}", chat2);
    }


    /**
     * 简单的聊天记忆测试
     * 此测试用例旨在验证聊天模型的记忆功能，通过连续的对话轮次来测试模型是否能记住并引用之前的对话内容
     */
    @Test
    public void testChatMemory2() {
        // 第一轮对话，用户自我介绍
        UserMessage userMessage1 = UserMessage.userMessage("我是hanx");
        // 使用大语言模型进行对话
        ChatResponse chatResponse1 = qwenChatModel.chat(userMessage1);
        // 获取AI的回复消息
        AiMessage aiMessage1 = chatResponse1.aiMessage();
        // 输出大语言模型回复
        logger.info("1输出大语言模型回复 : {}", aiMessage1.text());

        // 第二轮对话，用户询问AI是否记得自己的名字
        UserMessage userMessage2 = UserMessage.userMessage("你可以说出我的名字吗?");
        // 将第一轮对话的用户消息和AI回复，以及当前的用户消息一起作为输入，以测试模型的记忆功能
        ChatResponse chatResponse2 = qwenChatModel.chat(Arrays.asList(userMessage1, aiMessage1, userMessage2));
        // 获取AI的回复消息
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

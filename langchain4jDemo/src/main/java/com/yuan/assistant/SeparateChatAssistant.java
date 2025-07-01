package com.yuan.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import org.bson.types.ObjectId;

/**
 * 可以隔离的聊天记录会话
 *
 * AiServiceWiringMode.EXPLICIT
 *      AUTO,      // 自动绑定，框架根据配置自动选择服务
 *     EXPLICIT   // 显式绑定，必须手动指定使用哪个服务
 *
 */
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatMemory = "chatMemory",
        chatModel = "qwenChatModel",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface SeparateChatAssistant {

    /**
     *
     * SystemMessage  提示词
     * 独立隔离的聊天记录会话
     * @param memoryId
     * @param userMessage
     * @return
     */
    @SystemMessage("你是我的好朋友，请用东北话回答问题。")
    String chat(@MemoryId ObjectId memoryId, @UserMessage String userMessage);

    /**
     * current_date 获取今天日期
     * 提示词中可以引用系统变量，比如今天日期
     * @param memoryId
     * @param userMessage
     * @return
     */
    @SystemMessage("你是我的好朋友，请用西安话回答问题。今天是{{current_date}}")
    String chatXian(@MemoryId ObjectId memoryId, @UserMessage String userMessage);


    /**
     * 提示词模板文件
     * @param memoryId
     * @param userMessage
     * @return
     */
    @SystemMessage(fromResource = "my-prompt-template.txt")
    String chatTemplate(@MemoryId ObjectId memoryId, @UserMessage String userMessage);
}



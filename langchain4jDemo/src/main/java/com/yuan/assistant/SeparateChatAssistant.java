package com.yuan.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

/**
 * 可以隔离的聊天记录会话
 */
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatMemory = "chatMemory",
        chatModel = "qwenChatModel",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface SeparateChatAssistant {

    /**
     * 独立隔离的聊天记录会话
     * @param memoryId
     * @param userMessage
     * @return
     */
    String chat(@MemoryId int memoryId, @UserMessage String userMessage);
}



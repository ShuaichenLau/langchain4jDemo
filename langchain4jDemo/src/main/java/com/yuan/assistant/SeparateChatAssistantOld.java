package com.yuan.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

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
        chatMemoryProvider = "chatMemoryProviderOld"
)
public interface SeparateChatAssistantOld {

    /**
     *
     * @param memoryId
     * @param userMessage
     * @return
     */
    String chatOld(@MemoryId int memoryId, @UserMessage String userMessage);
}



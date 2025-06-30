package com.yuan.assistant;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

/**
 * 带记忆的会话
 * @author liusc
 * 2025年7月1日05:42:30
 */
@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemory = "chatMemory"

)
public interface MemoryChatAssistant {
    /**
     * 聊天
     * @param message
     * @return
     */
    String chat(String message);

}

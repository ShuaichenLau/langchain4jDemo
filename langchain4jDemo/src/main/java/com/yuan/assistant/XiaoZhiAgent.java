package com.yuan.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

/**
 * 创建硅谷小智
 *
 * AiServiceWiringMode.EXPLICIT
 *      AUTO,      // 自动绑定，框架根据配置自动选择服务
 *     EXPLICIT   // 显式绑定，必须手动指定使用哪个服务
 *
 */
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatMemory = "chatMemory",
        chatModel = "qwenChatModel",
        chatMemoryProvider = "chatMemoryProviderXiaoZhi"
)
public interface XiaoZhiAgent {

    /**
     *
     * @param memoryId
     * @param userMessage
     * @return
     */
    @SystemMessage(fromResource = "xiao-zhi-prompt.txt")
    String chat(@MemoryId Object memoryId, @UserMessage String userMessage);
}



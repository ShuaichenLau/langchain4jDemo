package com.yuan.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

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
//        chatModel = "qwenChatModel",
        streamingChatModel = "qwenStreamingChatModel",
        chatMemoryProvider = "chatMemoryProviderXiaoZhi",
        tools = "appointmentTools",
        // contentRetriever = "contentRetrieverXiaozhi"  // 配置向量存储 (2025年8月8日22:46:22添加)
        contentRetriever = "contentRetrieverXiaozhiPincone"  // 配置AWS云向量存储 (2025年8月9日02:48:35添加)
)
public interface XiaoZhiAgent {

    /**
     *
     * @param memoryId
     * @param userMessage
     * @return
     */
    @SystemMessage(fromResource = "zhaozhi-prompt-template.txt")
    Flux<String> chat(@MemoryId String memoryId, @UserMessage String userMessage);


}



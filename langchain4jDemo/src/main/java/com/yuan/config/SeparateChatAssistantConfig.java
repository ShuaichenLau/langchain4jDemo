package com.yuan.config;

import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 独立隔离的聊天会话窗口
 */
@Configuration
public class SeparateChatAssistantConfig {

    /**
     * 创建一个MemoryId为memoryId的ChatMemory
     * @return
     */
    @Bean
    ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId).maxMessages(10).build();
    }


}

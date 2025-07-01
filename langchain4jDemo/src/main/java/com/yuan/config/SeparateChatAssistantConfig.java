package com.yuan.config;

import com.yuan.store.MongoChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 独立隔离的聊天会话窗口
 */
@Configuration
public class SeparateChatAssistantConfig {

    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    /**
     * 创建一个MemoryId为memoryId的ChatMemory
     *
     * @return
     */
    @Bean
    ChatMemoryProvider chatMemoryProvider() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                //.chatMemoryStore(new InMemoryChatMemoryStore())
                .chatMemoryStore(mongoChatMemoryStore)
                .build();
    }


    /**
     * chatMemoryProviderOld
     * @return
     */
    @Bean
    ChatMemoryProvider chatMemoryProviderOld() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(new InMemoryChatMemoryStore())
//                .chatMemoryStore(mongoChatMemoryStore)
                .build();
    }


}

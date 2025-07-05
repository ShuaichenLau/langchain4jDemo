package com.yuan.config;

import com.yuan.store.MongoChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置小智助手
 * 配置持久化存储和记忆隔离
 */
@Configuration
public class XiaoZhiAgentConfig {

    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    /**
     * chatMemoryProviderXiaoZhi
     * 硅谷小智Bean
     *
     * @return
     */
    @Bean
    ChatMemoryProvider chatMemoryProviderXiaoZhi() {
        return memoryId ->
                MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(20) // 20条记忆消息 只能承受10轮会话
                        .chatMemoryStore(mongoChatMemoryStore)
                        .build();

    }

}

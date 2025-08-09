package com.yuan.config;

import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class LangChain4jConfig {

    // ⚠️ 请替换成你自己的 OpenAI API Key
    private static final String OPENAI_API_KEY = "sk-0ee8c7f6e1034cbe9f948fe80586146e";

    @Bean
    public OpenAiStreamingChatModel openAiStreamingChatModel() {
        return OpenAiStreamingChatModel.builder()
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1") // ⚠️ 重要！替换为阿里云百炼的 OpenAI 兼容接口地址
                .apiKey(OPENAI_API_KEY)                      // 你的 OpenAI API 密钥
                .modelName("qwen-max-latest")    // 或 GPT_4, GPT_4o, GPT_3_5_TURBO_16K 等
                .temperature(0.7)                           // 可选：控制生成随机性
                .topP(1.0)                                  // 可选
                .maxTokens(1000)                             // 可选：最大生成 token 数
                .timeout(Duration.ofSeconds(30))             // 可选：请求超时时间
                .build();                           // ⬅️ 注意这里是 buildStreaming()
    }
}
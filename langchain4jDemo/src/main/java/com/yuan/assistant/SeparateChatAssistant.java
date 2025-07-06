package com.yuan.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
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
        chatMemoryProvider = "chatMemoryProvider",
        tools = "calculatorTools"
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
     * langchat4j @V("message") 注解 参数名称必须和模板参数名称一致
     * @param userMessage
     * @return
     */
    @UserMessage("你是我的好朋友, 请用粤语回答问题 {{message}}")
    String chatV1(@MemoryId ObjectId memoryId,  @V("message") String userMessage);

    /**
     *
     * @param memoryId
     * @param userMessage
     * @return
     */
    @UserMessage("你是我的好朋友, 请用普通话回答问题 {{message}}")
    String chatV2(@MemoryId ObjectId memoryId,  @V("message") String userMessage);
    /**
     *
     *@SystemMessage (系统消息)
     * @SystemMessage 注解用于定义 LLM 在对话中的角色、行为或指令。它为模型设定了上下文、约束和总体的指导原则。系统消息通常在对话的最开始提供，并且通常不会随着对话的进行而改变。
     *
     * 主要特点：
     *
     * 设定角色和行为： 告诉 LLM 它应该扮演什么角色（比如一个助手、一个专家、一个客服）、它应该如何回答问题，或者它在生成回复时需要遵守哪些规则。
     *
     * 提供约束和指导： 可以用于限制模型的输出格式、语言风格，或者指示模型在特定情况下该做什么（比如“如果不知道答案，请说‘我不知道’而不是编造”）。
     *
     * 非对话性输入： 通常不包含用户直接提出的问题或请求，而是关于如何处理这些问题和请求的元指令。
     *
     * 优先于用户消息： LLM 在生成回复时，会优先考虑系统消息中的指令。
     *
     *============================================================================================
     *
     * @UserMessage (用户消息)
     * @UserMessage 注解用于定义由用户直接输入的消息。这通常是用户向 LLM 提出的问题、请求或提供的信息。用户消息是对话的核心驱动力。
     *
     * 主要特点：
     *
     * 用户直接输入： 代表着来自最终用户的原始请求或查询。
     *
     * 触发模型响应： 这是 LLM 需要根据其角色和系统指令来生成回复的内容。
     *
     * 动态变化： 在多轮对话中，用户消息会随着用户输入而不断变化。
     *
     * @param memoryId
     * @param userMessage
     * @return
     */
    @SystemMessage(fromResource = "my-prompt-template.txt")
    String chatV2(@MemoryId ObjectId memoryId,  @UserMessage String userMessage,
                  @V("userName") String userName, @V("age") int age);


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



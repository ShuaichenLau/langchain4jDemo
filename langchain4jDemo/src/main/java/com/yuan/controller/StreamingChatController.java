package com.yuan.controller;

import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 *
 */
@RestController
public class StreamingChatController {
    @Autowired
    private OpenAiStreamingChatModel model;

    /**
     * 验证流式输出回答
     * http://localhost:38080/stream-chat?message=%E4%BD%A0%E5%A5%BD%EF%BC%8C%E8%AF%B7%E4%BB%8B%E7%BB%8D%E4%B8%80%E4%B8%8B%E4%BD%A0%E8%87%AA%E5%B7%B1
     * @param message
     * @return
     */
    @GetMapping(value = "/stream-chat", produces = "text/stream;charset=UTF-8")
    public Flux<String> streamChat(@RequestParam("message") String message) {
        return Flux.create(sink -> {
            model.chat(message, new StreamingChatResponseHandler() {
                @Override
                public void onPartialResponse(String partialResponse) {
                    sink.next(partialResponse); // 推送分块响应
                }

                @Override
                public void onCompleteResponse(ChatResponse chatResponse) {
                    sink.complete();
                    System.out.println("输出 dev.langchain4j.model.chat.response.StreamingChatResponseHandler.onCompleteResponse");
                }

                @Override
                public void onError(Throwable error) {
                    sink.error(error); // 传递错误
                }
            });
        });
    }
}
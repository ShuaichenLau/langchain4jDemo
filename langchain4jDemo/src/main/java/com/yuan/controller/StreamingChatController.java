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
package com.yuan.bean;

import org.springframework.data.annotation.Id;

public class XiaoZhiChatForm {

    @Id
    private String memoryId; // 对话ID

    private String message; // 用户问题


    public String getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(String memoryId) {
        this.memoryId = memoryId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

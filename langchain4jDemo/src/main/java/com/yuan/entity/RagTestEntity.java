package com.yuan.entity;


import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 文档加载器表
 */
@TableName("rag_test")
public class RagTestEntity {

    private String id;
    private String medata;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedata() {
        return medata;
    }

    public void setMedata(String medata) {
        this.medata = medata;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

package com.yuan.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


/**
 * mongoDB 映射
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChatMessagesV1 {

    @Id
    private ObjectId id;

    private String messageId;

    private String content;

}

package com.yuan.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * mongoDB 映射
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
//@Document(collection = "chat_messages")
@Document(collection = "chat_xiaozhi_messages") // mongoDB 集合名称
public class ChatMessages {

    @Id
//    private Long messageId;
    private ObjectId messageId;

    private String memoryId;

    private String content;


}

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
public class ChatMessages {

    @Id
//    private Long messageId;
    private ObjectId messageId;

    private String content;

}

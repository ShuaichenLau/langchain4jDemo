package com.yuan.store;

import com.google.common.collect.Lists;
import com.yuan.bean.ChatMessages;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * mongodb存储
 * @author liusc
 * @date 2025年7月1日21:25:55
 */
@Component
public class MongoChatMemoryStore implements ChatMemoryStore {
    private Logger log = LoggerFactory.getLogger(MongoChatMemoryStore.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        Criteria criteria = Criteria.where("_id").is(memoryId);
        Query query = new Query(criteria);
        ChatMessages chatMessages = mongoTemplate.findOne(query, ChatMessages.class);
        // chatMessages没有获取到数据，则返回空集合
        if (Objects.isNull(chatMessages)) {
            return Lists.newLinkedList();
        }

        // 响应
        return ChatMessageDeserializer.messagesFromJson(chatMessages.getContent());
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> list) {
        log.info("更新memoryId:{}", memoryId);
        Criteria criteria = Criteria.where("_id").is(memoryId);
        Query query = new Query(criteria);
        Update update = new Update();

        update.set("content", ChatMessageSerializer.messagesToJson(list));
        //  修改或新增
        mongoTemplate.upsert(query, update, ChatMessages.class);

    }

    @Override
    public void deleteMessages(Object memoryId) {
        log.info("删除memoryId:{}", memoryId);
        Criteria criteria = Criteria.where("_id").is(memoryId);
        mongoTemplate.remove(new Query(criteria), ChatMessages.class);
    }
}

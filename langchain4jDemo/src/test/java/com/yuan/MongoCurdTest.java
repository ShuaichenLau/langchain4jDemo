package com.yuan;

import com.alibaba.fastjson.JSON;
import com.yuan.bean.ChatMessages;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

/**
 * mongodb 增删改查 单元测试
 */
@SpringBootTest(classes = LangChainDemoMain.class)
public class MongoCurdTest {

    @Autowired
    private MongoTemplate mongoTemplate;


//    @Test
//    public void testInsert(){
//        mongoTemplate.insert(new ChatMessages(1L,"聊天记录"));
//    }


    @Test
    public void testInsert() {
        ChatMessages chatMessages = new ChatMessages();
        chatMessages.setContent("mongodb 增删改查 单元测试");
        mongoTemplate.insert(chatMessages);
    }


    /**
     * 查找
     */
    @Test
    public void testGet() {
        ChatMessages chatMessages = mongoTemplate.findById("6862f1d801b7ae5a0e1bab71", ChatMessages.class);
        System.out.println(JSON.toJSON(chatMessages));
    }


    /**
     * 测试修改
     */
    @Test
    public void testUpdate() {
        ChatMessages chatMessages = mongoTemplate.findById("6862f1d801b7ae5a0e1bab71", ChatMessages.class);
        System.out.println(JSON.toJSON(chatMessages));

        Criteria criteria = Criteria.where("_id").is("6862f1d801b7ae5a0e1bab71");
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("content", new Date().toString() + " _ mongodb 增删改查 单元测试");
        //  修改或新增
        mongoTemplate.upsert(query, update, ChatMessages.class);

        //ChatMessages chatMessages = mongoTemplate.findById("6862f1d801b7ae5a0e1bab71",ChatMessages.class);
        System.out.println(JSON.toJSON(mongoTemplate.findById("6862f1d801b7ae5a0e1bab71", ChatMessages.class)));
    }



    /**
     * 测试 修改or新增
     */
    @Test
    public void testUpdate2() {
        ObjectId id = new ObjectId();

        ChatMessages chatMessages = mongoTemplate.findById(id, ChatMessages.class);
        System.out.println(JSON.toJSON(chatMessages));

        Criteria criteria = Criteria.where("_id").is(id);
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("content", new Date().toString() + " _ mongodb 增删改查 单元测试");
        //  修改或新增
        mongoTemplate.upsert(query, update, ChatMessages.class);

        //ChatMessages chatMessages = mongoTemplate.findById(id,ChatMessages.class);
        System.out.println(JSON.toJSON(mongoTemplate.findById(id, ChatMessages.class)));
    }


    /**
     * 测试 删除
     */
    @Test
    public void testDelete() {
        ObjectId id = new ObjectId();

        ChatMessages chatMessages = mongoTemplate.findById(id, ChatMessages.class);
        System.out.println(JSON.toJSON(chatMessages));

        Criteria criteria = Criteria.where("_id").is(id);
        Query query = new Query(criteria);
        Update update = new Update();
        update.set("content", new Date().toString() + " _ mongodb 增删改查 单元测试");
        //  修改或新增
        mongoTemplate.upsert(query, update, ChatMessages.class);

        System.out.println("删除之前的记录");
        //ChatMessages chatMessages = mongoTemplate.findById(id,ChatMessages.class);
        System.out.println(JSON.toJSON(mongoTemplate.findById(id, ChatMessages.class)));

        // 执行删除
        mongoTemplate.remove(query,ChatMessages.class);

        System.out.println("删除之后的记录");
        System.out.println(JSON.toJSON(mongoTemplate.findById(id, ChatMessages.class)));

    }

}

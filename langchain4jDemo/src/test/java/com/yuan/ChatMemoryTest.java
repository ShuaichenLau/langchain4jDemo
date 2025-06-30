package com.yuan;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 */
@SpringBootTest(classes = LangChainDemoMain.class)
public class ChatMemoryTest {

    @Autowired
    private AssistantV1 assistantV1;
    @Test
    public void testChatMemory(){
        String answer1 = assistantV1.chat("我是哈哈");
        System.out.println(answer1);


        String answer2 = assistantV1.chat("我是谁");
        System.out.println(answer2);
    }

}

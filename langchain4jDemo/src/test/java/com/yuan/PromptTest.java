package com.yuan;

import com.yuan.assistant.SeparateChatAssistant;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LangChainDemoMain.class)
public class PromptTest {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(PromptTest.class);

    @Autowired
    private SeparateChatAssistant separateChatAssistant;

    @Test
    public void testPrompt() {
        ObjectId objectId = new ObjectId();
        String result = separateChatAssistant.chat(objectId, "给我讲个笑话");
        logger.info("result: {}", result);
    }

}

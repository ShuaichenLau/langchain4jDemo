package com.yuan;

import com.alibaba.fastjson.JSON;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 使用云端向量存储测试
 */
@SpringBootTest(classes = LangChainDemoMain.class)
public class EmbeddingTest {

    private Logger logger = LoggerFactory.getLogger(EmbeddingTest.class);

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private EmbeddingStore embeddingStore;;


    @Test
    public void EmbeddingSearchTest() {

        Embedding contented = embeddingModel.embed("北京怎么样?").content();

        EmbeddingSearchRequest searchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(contented).maxResults(5) // 匹配醉相似的5条记录
                .minScore(0.5) //得分最低的
                .build();

        EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(searchRequest);

        EmbeddingMatch<TextSegment> embeddingMatch = searchResult.matches().get(0);

        logger.info("{}", embeddingMatch.score());
        logger.info("{}", embeddingMatch.embedded().text());

    }

    @Test
    public void testUploadKnowledgeLibrary() {



    }



    @Test
    public void EmbeddingTest() {

        Response<Embedding> embed = embeddingModel.embed("你好");

        logger.info("{}", JSON.toJSONString(embed));

        logger.info("向量维度 : {}  " ,embed.content().vector().length);
        logger.info("向量输出 : {}  " ,embed.toString());

//        pcsk_2o3psc_3Kpc1FSVHXoCVyGGNELvTQ97jySBkJCWg5C5AwDn2FqEVE1j8L4Z2Jpy7b9RnPJ
    }


    /**
     * 测试向量存储
     */
    @Test
    public void EmbeddingTest1() {
        TextSegment textSegment1 = TextSegment.from("北京很热1");
        Embedding embedding1 = embeddingModel.embed(textSegment1).content();
        // 存入向量数据库
        embeddingStore.add(embedding1,textSegment1);


        TextSegment textSegment2 = TextSegment.from("北京网速很慢");
        Embedding embedding2 = embeddingModel.embed(textSegment2).content();
        // 存入向量数据库
        embeddingStore.add(embedding2,textSegment2);

        TextSegment textSegment3 = TextSegment.from("北京空气不好");
        Embedding embedding3 = embeddingModel.embed(textSegment3).content();
        // 存入向量数据库
        embeddingStore.add(embedding3,textSegment3);

    }

}

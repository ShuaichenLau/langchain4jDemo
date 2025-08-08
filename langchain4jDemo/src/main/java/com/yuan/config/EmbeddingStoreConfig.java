package com.yuan.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeServerlessIndexConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置小智助手
 * 配置持久化存储和记忆隔离
 */
@Configuration
public class EmbeddingStoreConfig {

    private Logger logger = LoggerFactory.getLogger(EmbeddingStoreConfig.class);

    @Autowired
    private EmbeddingModel embeddingModel;

    /**
     * 声明向量存储
     *
     * @return
     */
    @Bean
    public EmbeddingStore<TextSegment> embeddingStore() {

        // 创建向量存储
        EmbeddingStore<TextSegment> embeddingStore = PineconeEmbeddingStore.builder()
                .apiKey("pcsk_6jMiSp_P76ufQJrrtbXDkekHcNfH4ahTbgXQVuedN6N4XSr4WhaxV4dDDDkePZiN3NJSUt")
                .index("xiaozhi-test")//如果指定的索引不存在 将创建一个新的索引
                .nameSpace("my-namespace") //如果指定的名称空间不存在  将创建一个新的名称空间
                .createIndex(PineconeServerlessIndexConfig.builder()
                        .cloud("AWS")//指定索引部署在AWS云服务器上
                        .region("us-east-1")//指定索引部署在us-east-1
                        .dimension(embeddingModel.dimension()) //指定索引的维度 该维度与embeddingModel生成的向量维度相同
                        .build()).build();


//        EmbeddingStore<TextSegment> embeddingStore = PineconeEmbeddingStore.builder()
//                .apiKey("pcsk_6jMiSp_P76ufQJrrtbXDkekHcNfH4ahTbgXQVuedN6N4XSr4WhaxV4dDDDkePZiN3NJSUt")
//                .index("xiaozhi-index")//如果指定的索引不存在，将创建一个新的索引
//                .nameSpace("xiaozhi-namespace") //如果指定的名称空间不存在，将创建一个新的名称空间
//                .createIndex(PineconeServerlessIndexConfig.builder()
//                        .cloud("AWS") //指定索引部署在 AWS 云服务上。
//                        .region("us-east-1") //指定索引所在的 AWS 区域为 us-east-1。
//                        .dimension(embeddingModel.dimension()) //指定索引的向量维度，该维度与 embeddedModel 生成的向量维度相同。
//                        .build())
//                .build();
        return embeddingStore;
    }


}

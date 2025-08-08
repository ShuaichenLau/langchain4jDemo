package com.yuan.config;

import com.alibaba.fastjson.JSON;
import com.yuan.store.MongoChatMemoryStore;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;

/**
 * 配置小智助手
 * 配置持久化存储和记忆隔离
 */
@Configuration
public class XiaoZhiAgentConfig {

    private Logger logger = LoggerFactory.getLogger(XiaoZhiAgentConfig.class);

    @Autowired
    private MongoChatMemoryStore mongoChatMemoryStore;

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private EmbeddingStore embeddingStore;;


    /**
     * chatMemoryProviderXiaoZhi
     * 硅谷小智Bean
     *
     * @return
     */
    @Bean
    ChatMemoryProvider chatMemoryProviderXiaoZhi() {
        return memoryId ->
                MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(30) // 20条记忆消息 只能承受10轮会话
                        .chatMemoryStore(mongoChatMemoryStore)
                        .build();

    }


    /**
     * 内容检索器
     * @return
     */
    @Bean
    ContentRetriever contentRetrieverXiaozhi() {

        logger.info("初始化内容检索器");
        String path = "D:\\BaiduNetdiskDownload\\尚硅谷AI大模型生态\\大模型应用实战\\硅谷小智（医疗版）\\资料\\knowledge";

        // 从一个目录中加载所有的.md文档     全局查找md文件
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.md");
        List<Document> documents = FileSystemDocumentLoader.loadDocuments(path, pathMatcher);

        logger.info("检索指定的文档 [{}]", JSON.toJSONString(documents));

        // 使用内存向量存储(声明向量)
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        // 使用默认的文档分割器
        EmbeddingStoreIngestor.ingest(documents,embeddingStore);

        // 从嵌入存储(embeddingStore) 检索和查询内容相关的信息
        return EmbeddingStoreContentRetriever.from(embeddingStore);

    }


    /**
     * 上传向量数据库
     */
    private void uploadKnowledgeLibrary(List<Document> documents) {
        logger.info("上传向量数据库 [{}]", JSON.toJSONString(documents));
        EmbeddingStoreIngestor.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .build()
                .ingest(documents);
    }

    /**
     * 创建一个内容检索器，用于从嵌入存储中检索内容
     * 上传向量数据库
     * @return
     */
    @Bean
    ContentRetriever contentRetrieverXiaozhiPincone() {
        // 创建一个 EmbeddingStoreContentRetriever 对象，用于从嵌入存储中检索内容

        String path = "D:\\BaiduNetdiskDownload\\尚硅谷AI大模型生态\\大模型应用实战\\硅谷小智（医疗版）\\资料\\knowledge";

        // 从一个目录中加载所有的.md文档     全局查找md文件
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.md");
        List<Document> documents = FileSystemDocumentLoader.loadDocuments(path, pathMatcher);

        uploadKnowledgeLibrary(documents);

        return EmbeddingStoreContentRetriever
                .builder()
                // 设置用于生成嵌入向量的嵌入模型
                .embeddingModel(embeddingModel)
                // 指定要使用的嵌入存储
                .embeddingStore(embeddingStore)
                // 设置最大检索结果数量，这里表示最多返回 1 条匹配结果
                .maxResults(1)
                // 设置最小得分阈值，只有得分大于等于 0.8 的结果才会被返回
                .minScore(0.8)
                // 构建最终的 EmbeddingStoreContentRetriever 实例
                .build();
    }




}

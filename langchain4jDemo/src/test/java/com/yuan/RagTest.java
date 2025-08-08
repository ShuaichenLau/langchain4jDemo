package com.yuan;

import com.alibaba.fastjson.JSON;
import com.yuan.entity.RagTestEntity;
import com.yuan.service.impl.RagTestServiceImpl;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenizer;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.UUID;

/**
 * 文档分割器 向量检索测试
 */
@SpringBootTest(classes = LangChainDemoMain.class)
public class RagTest {

    private Logger logger = LoggerFactory.getLogger(RagTest.class);


    @Autowired
    private RagTestServiceImpl ragTestService;

    /**
     * 文档加载  by liusc
     * 文档加载器 包含一些文档加载器的各种方式
     * 有正则匹配器
     */
    @Test
    public void testReadDocument() {

        try {
            String path = "D:\\BaiduNetdiskDownload\\尚硅谷AI大模型生态\\大模型应用实战\\硅谷小智（医疗版）\\资料\\knowledge";

            Document document = FileSystemDocumentLoader.loadDocument("D:\\迅雷下载\\README.md");
            System.out.println(document.text());

            // 加载单个文档
            Document document1 = FileSystemDocumentLoader.loadDocument(path + File.separator + "测试.txt", new TextDocumentParser());
            // 从一个目录中加载所有文档
            List<Document> documents2 = FileSystemDocumentLoader.loadDocuments(path, new TextDocumentParser());
            // 从一个目录中加载所有的.txt文档     全局查找txt文件
            PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
            List<Document> documents3 = FileSystemDocumentLoader.loadDocuments(path, pathMatcher, new TextDocumentParser());
            System.out.println("************************************************");
            for (Document document3_2 : documents3) {
//                System.out.println("========================================");
//                System.out.println(document3_2.metadata());
//                System.out.println(document3_2.text());

                RagTestEntity ragTestEntity = new RagTestEntity();
                ragTestEntity.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                ragTestEntity.setMedata(document3_2.metadata().toString());
                ragTestEntity.setText(document3_2.text());
                ragTestService.save(ragTestEntity);

            }

            System.out.println("************************************************");
            System.out.println("#################################################");

            // 从一个目录及其子目录中加载所有文档
            List<Document> documents4 = FileSystemDocumentLoader.loadDocumentsRecursively(path, new TextDocumentParser());
            for (Document document4_2 : documents4) {
                System.out.println("========================================");
                System.out.println(document4_2.metadata());
                System.out.println(document4_2.text());
            }
            System.out.println("#################################################");
        } catch (Exception e) {
            logger.error("异常信息", e);
        }
    }


    /**
     * pdf 文档解析器  ApachePdfBoxDocumentParser
     */
    @Test
    public void testReadPdf() {

        try {
            String path = "D:\\BaiduNetdiskDownload\\尚硅谷AI大模型生态\\大模型应用实战\\硅谷小智（医疗版）\\资料\\knowledge";

            // 从一个目录中加载所有的.txt文档     全局查找txt文件
            PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.pdf");
            List<Document> documents3 = FileSystemDocumentLoader.loadDocuments(path, pathMatcher, new ApachePdfBoxDocumentParser());
            System.out.println("************************************************");
            for (Document document3_2 : documents3) {
                RagTestEntity ragTestEntity = new RagTestEntity();
                ragTestEntity.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                ragTestEntity.setMedata(document3_2.metadata().toString());
                ragTestEntity.setText(document3_2.text());
                ragTestService.save(ragTestEntity);

            }

        } catch (Exception e) {
            logger.error("异常信息", e);
        }
    }



    /**
     *
     * 第三阶段  文档分割器
     * 文档分割器
     *
     * LangChain4j 有一个 “文档分割器”（DocumentSplitter）接口，并且提供了几种开箱即用的实现方式：
     * 按段落文档分割器（DocumentByParagraphSplitter）
     * 按行文档分割器（DocumentByLineSplitter）
     * 按句子文档分割器（DocumentBySentenceSplitter）
     * 按单词文档分割器（DocumentByWordSplitter）
     * 按字符文档分割器（DocumentByCharacterSplitter）
     * 按正则表达式文档分割器（DocumentByRegexSplitter）
     * 递归分割：DocumentSplitters.recursive (...)
     * 默认情况下每个文本片段最多不能超过300个token
     *
     */
    @Test
    public void testReadDocumentSplitter() {

        String filePath = "D:\\BaiduNetdiskDownload\\尚硅谷AI大模型生态\\大模型应用实战\\硅谷小智（医疗版）\\资料\\knowledge\\科室信息.md";

        // 使用FileSystemDocumentLoader读取指定目录下的知识库文档
        Document document = FileSystemDocumentLoader.loadDocument(filePath);
        System.out.println(document.text());

        /**
         * 创建一个基于内存的向量存储检索, 把["D:\\迅雷下载\\README.md"]这个文件向量存储起来
         */
        // 暂时使用基于内存的向量存储检索
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<TextSegment>();

        /**
         * ingest方法包含3个步骤
         * 1.分割文档 默认使用递归分割器 将文档分割为多个文本片段  每个片段包含不超过 300个token，并且有 30个token的重叠部分保证连贯性
         * 2.文本向量化, 使用一个langchain4j内置的轻量化向量模型对每个文本片段进行向量化
         * 3.将原始文本和向量存储在向量数据库中(InMemoryEmbeddingStore)
         */
        EmbeddingStoreIngestor.ingest(document, embeddingStore);

        // 打印输出向量数据库内容信息
        System.out.println(embeddingStore);


        // 自定义文档分割器
        //按段落分割文档：每个片段包含不超过 300个token，并且有 30个token的重叠部分保证连贯性
        //注意：当段落长度总和小于设定的最大长度时，就不会有重叠的必要。
        DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(
                300,
                30,
                //token分词器：按token计算
                new HuggingFaceTokenizer());

        logger.info("文档分割器：{} ==>[{}]", documentSplitter, JSON.toJSON(documentSplitter));

        //按字符计算
        //DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(300, 30);


        /**
         * 让我详细解释这段代码的功能和工作流程：
         *
         * ## 核心功能
         *
         * 这段代码展示了如何使用 [EmbeddingStoreIngestor](file://dev\langchain4j\store\embedding\EmbeddingStoreIngestor.java#L11-L41) 来处理文档并将其存储到向量数据库中，以便后续进行语义搜索。
         *
         * ## 详细工作流程
         *
         * 1. **创建 EmbeddingStoreIngestor 实例**
         *    - 使用构建器模式创建 [EmbeddingStoreIngestor](file://dev\langchain4j\store\embedding\EmbeddingStoreIngestor.java#L11-L41) 对象
         *    - 配置两个核心组件：
         *      - [embeddingStore](file://dev\langchain4j\store\embedding\EmbeddingStoreIngestor.java#L30-L30): 向量存储，用于保存文档的向量表示
         *      - [segmenter](file://dev\langchain4j\document\Document.java#L24-L24): 文档分割器，用于将大文档分割成更小的段落
         *
         * 2. **文档处理流程**
         *    - 调用 [ingestor.ingest(documents)](file://dev\langchain4j\store\embedding\EmbeddingStoreIngestor.java#L35-L35) 方法开始处理文档
         *    - 内部处理步骤包括：
         *      a. 使用 [segmenter](file://dev\langchain4j\document\Document.java#L24-L24) 将输入的文档分割成更小的文本块（segments）
         *      b. 对每个文本块生成向量表示（embedding）
         *      c. 将文本块及其向量表示存储到 [embeddingStore](file://dev\langchain4j\store\embedding\EmbeddingStoreIngestor.java#L30-L30) 中
         *
         * 3. **组件说明**
         *    - [embeddingStore](file://dev\langchain4j\store\embedding\EmbeddingStoreIngestor.java#L30-L30): 向量数据库，用于存储和检索向量数据
         *    - [segmenter](file://dev\langchain4j\document\Document.java#L24-L24): 文档分割策略，决定如何将大文档切分为合适大小的段落
         *
         * ## 应用场景
         *
         * 这种模式通常用于构建基于检索的问答系统（RAG - Retrieval Augmented Generation），其中：
         * - 文档首先被预处理并存储为向量
         * - 用户查询时，系统会找到最相关的文档片段
         * - 然后基于这些片段生成回答
         *
         * 这是构建智能文档搜索和问答系统的关键步骤。
         *
         * 根据代码中的使用情况，让我详细解释 [EmbeddingStoreIngestor](file://dev\langchain4j\store\embedding\EmbeddingStoreIngestor.java#L11-L41) 的目的、作用和好处：
         *
         * ## 目的 (Purpose)
         *
         * [EmbeddingStoreIngestor](file://dev\langchain4j\store\embedding\EmbeddingStoreIngestor.java#L11-L41) 的主要目的是**将文档内容转换为向量表示并存储到向量数据库中**，为后续的语义搜索和相似性检索做准备。这是构建检索增强生成（RAG）系统的关键步骤。
         *
         * ## 作用 (Function)
         *
         * 1. **文档预处理流程管理**：
         *    - 接收原始文档作为输入
         *    - 自动执行文档分割、向量化和存储的完整流程
         *    - 简化了向量数据库的构建过程
         *
         * 2. **自动化处理管道**：
         *    ```java
         *    EmbeddingStoreIngestor.ingest(document, embeddingStore);
         *    ```
         *
         *    这一行代码自动完成了以下三个步骤：
         *    - 使用默认的递归分割器将文档分割成多个文本片段
         *    - 对每个文本片段进行向量化处理
         *    - 将文本片段及其向量表示存储到向量数据库中
         *
         * 3. **可配置的处理流程**：
         *    ```java
         *    EmbeddingStoreIngestor.builder()
         *        .embeddingStore(embeddingStore)
         *        .documentSplitter(documentSplitter)
         *        .build()
         *        .ingest(document);
         *    ```
         *
         *    允许自定义分割器和其他组件，提供灵活的配置选项。
         *
         * ## 好处 (Benefits)
         *
         * 1. **简化开发流程**：
         *    - 将复杂的多步骤处理封装成简单的API调用
         *    - 开发者无需手动管理文档分割、向量化和存储的每个步骤
         *
         * 2. **提高效率**：
         *    - 自动化处理减少了手动编码错误
         *    - 内置优化确保处理流程高效执行
         *
         * 3. **灵活性**：
         *    - 支持自定义分割策略（如代码中的 [DocumentByParagraphSplitter](file://dev\langchain4j\data\document\splitter\DocumentByParagraphSplitter.java#L5-L13)）
         *    - 可以配置不同的token长度和重叠大小
         *    - 支持不同的分词器（如 [HuggingFaceTokenizer](file://dev\langchain4j\model\embedding\onnx\HuggingFaceTokenizer.java#L9-L20)）
         *
         * 4. **为语义搜索做准备**：
         *    - 处理后的向量数据可以用于：
         *      - 相似性搜索
         *      - 语义匹配
         *      - RAG系统的知识库构建
         *
         * 5. **模块化设计**：
         *    - 可以轻松替换不同的组件（分割器、向量模型等）
         *    - 支持多种文档类型和存储后端
         *
         * 这种方式使得构建智能文档检索系统变得更加简单和标准化，是现代AI应用中处理大量文本数据的标准做法。
         *
         *
         */
        EmbeddingStoreIngestor.builder()
                .embeddingStore(embeddingStore)
                .documentSplitter(documentSplitter)
                .build()
                .ingest(document);


    }

}

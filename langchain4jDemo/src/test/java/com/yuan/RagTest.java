package com.yuan;

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
     * 文档分割器
     */
    @Test
    public void testReadDocumentSplitter() {

        // 使用FileSystemDocumentLoader读取指定目录下的知识库文档
        Document document = FileSystemDocumentLoader.loadDocument("D:\\迅雷下载\\README.md");
        System.out.println(document.text());


        /**
         * 创建一个基于内存的向量存储检索, 把["D:\\迅雷下载\\README.md"]这个文件向量存储起来
         */
        // 暂时使用基于内存的向量存储检索
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<TextSegment>();


        /**
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

        //按字符计算
        //DocumentByParagraphSplitter documentSplitter = new DocumentByParagraphSplitter(300, 30);


    }

}

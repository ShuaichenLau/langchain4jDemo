package com.yuan;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.HuggingFaceTokenizer;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.List;

/**
 * 文档分割器 向量检索测试
 */
@SpringBootTest(classes = LangChainDemoMain.class)
public class RagTest {

    private Logger logger = LoggerFactory.getLogger(RagTest.class);


    /**
     * TODO 文档加载  by liusc 2025年7月16日23:08:35
     *
     */
    @Test
    public void testReadDocument() {

        Document document = FileSystemDocumentLoader.loadDocument("D:\\迅雷下载\\README.md");
        System.out.println(document.text());


        // 加载单个文档
        Document document1 = FileSystemDocumentLoader.loadDocument("E:/knowledge/file.txt", new
                TextDocumentParser());
        // 从一个目录中加载所有文档
        List<Document> documents2 = FileSystemDocumentLoader.loadDocuments("E:/knowledge", new
                TextDocumentParser());
        // 从一个目录中加载所有的.txt文档
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.txt");
        List<Document> documents3 = FileSystemDocumentLoader.loadDocuments("E:/knowledge",
                pathMatcher, new TextDocumentParser());
        // 从一个目录及其子目录中加载所有文档
        List<Document> documents4 =
                FileSystemDocumentLoader.loadDocumentsRecursively("E:/knowledge", new
                        TextDocumentParser());
        for (Document document2 : documents4) {
            System.out.println("========================================");
            System.out.println(document2.metadata());
            System.out.println(document2.text());
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


        // 暂时使用基于内存的向量存储检索
        InMemoryEmbeddingStore<TextSegment> inMemoryChatMemoryStore = new InMemoryEmbeddingStore<TextSegment>();

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

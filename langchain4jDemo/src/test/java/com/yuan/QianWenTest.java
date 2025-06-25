package com.yuan;


import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * deepseek-r1 demo 测试
 * 2025年6月24日21:05:28
 * by liusc
 */
@SpringBootTest(classes = LangChainDemoMain.class)
public class QianWenTest {

    private final Logger logger = LoggerFactory.getLogger(QianWenTest.class);


    /**
     * 阿里通义百炼平台  千问
     */
    @Autowired
    private QwenChatModel qwenChatModel;

    /**
     * 阿里通义百炼平台  千问
     * 2025年6月24日22:43:01
     */
    @Test
    public void testSpringBoot() {
        String chat = qwenChatModel.chat("我是谁?");
        System.out.println(chat);
        logger.info(chat);
    }


    /**
     * 文生图 demo
     */
    @Test
    public void testDashScopeWanx() {
        WanxImageModel wanxImageModel = WanxImageModel.builder().modelName("wanx2.1-t2i-turbo")
                .apiKey("sk-0ee8c7f6e1034cbe9f948fe80586146e1").build();

        Response<Image> imageResponse = wanxImageModel.generate("奇幻森林精灵：在一片弥漫着轻柔薄雾的" +
                "古老森林深处，阳光透过茂密枝叶洒下金色光斑。一位身材娇小、长着透明薄翼的精灵少女站在一朵硕大的蘑菇上。她" +
                "有着海藻般的绿色长发，发间点缀着蓝色的小花，皮肤泛着珍珠般的微光。身上穿着由翠绿树叶和白色藤蔓编织而成的" +
                "连衣裙，手中捧着一颗散发着柔和光芒的水晶球，周围环绕着五彩斑斓的蝴蝶，脚下是铺满苔藓的地面，蘑菇和蕨类植" +
                "物丛生，营造出神秘而梦幻的氛围。");
        logger.info("{}", imageResponse.content().url());

        // https://www.bilibili.com/video/BV1cpLTz1EVp?spm_id_from=333.788.player.switch&vd_source=ceb671c2d6d65ca9240098b709c35ceb&p=15
        // https://docs.langchain4j.dev/tutorials/spring-boot-integration

        // file:///D:/BaiduNetdiskDownload/%E5%B0%9A%E7%A1%85%E8%B0%B7AI%E5%A4%A7%E6%A8%A1%E5%9E%8B%E7%94%9F%E6%80%81/%E5%A4%A7%E6%A8%A1%E5%9E%8B%E5%BA%94%E7%94%A8%E5%AE%9E%E6%88%98/%E7%A1%85%E8%B0%B7%E5%B0%8F%E6%99%BA%EF%BC%88%E5%8C%BB%E7%96%97%E7%89%88%EF%BC%89/%E8%AF%BE%E4%BB%B6/%E5%B0%9A%E7%A1%85%E8%B0%B7-Java+%E5%A4%A7%E6%A8%A1%E5%9E%8B%E5%BA%94%E7%94%A8-%E7%A1%85%E8%B0%B7%E5%B0%8F%E6%99%BA%EF%BC%88%E5%8C%BB%E7%96%97%E7%89%88%EF%BC%89.pdf

        // file:///D:/Users/WeChat%20Files/lliujian8485/FileStorage/File/2025-06/20250430Java+%E5%A4%A7%E6%A8%A1%E5%9E%8B%E5%BA%94%E7%94%A8%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C-pdf%E5%8D%95%E9%A1%B5.pdf

        // https://bailian.console.aliyun.com/?spm=5176.12818093_47.console-base_product-drawer-right.dsfm.46be2cc9sf6PWA&tab=api#/api/?type=model&url=https%3A%2F%2Fhelp.aliyun.com%2Fdocument_detail%2F2862677.html

        // https://ollama.com/library/deepseek-r1:latest

    }

}

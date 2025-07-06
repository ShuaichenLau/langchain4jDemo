package com.yuan.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.agent.tool.ToolMemoryId;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

/**
 * function caling工具定义 和 使用
 */
@Component
public class CalculatorTools {

    private Logger log = org.slf4j.LoggerFactory.getLogger(CalculatorTools.class);

    @Tool
    double sum(double a, double b) {
        log.info("sum: {} + {}", a, b);
        return a + b;
    }

    @Tool(name = "加法运算",value = "将两个参数a和b相加计算并返回结果")
    double sumV1(double a, double b) {
        log.info("sum: {} + {}", a, b);
        return a + b;
    }

    /**
     * @P P注解的应用
     * @param a
     * @param b
     * @return
     */
    @Tool(name = "加法运算",value = "将两个参数a和b相加计算并返回结果")
    double sumV2(@P(value = "加数a",required = true) double a,
                 @P(value = "加数b",required = true) double b) {
        log.info("sum: {} + {}", a, b);
        return a + b;
    }

    /**
     * @P P注解的应用
     * @param a
     * @param b
     * @return
     */
    @Tool(name = "加法运算",value = "将两个参数a和b相加计算并返回结果")
    double sumV3(
            @ToolMemoryId Object memoryId,
            @P(value = "加数a",required = true) double a,
                 @P(value = "加数b",required = true) double b) {
        log.info("sum: {} + {}", a, b);
        return a + b;
    }


    @Tool(name = "平方根运算",value = "将参数a的平方根计算并返回结果")
    double squareRoot(double a) {
        log.info("squareRoot: {}", a);
        return Math.sqrt(a);
    }
}

package com.yuan;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 简化版Java文生图调用示例
 * 适合快速测试使用
 *
 * TODO 暂时不可用 2025年8月13日20:52:16
 */
public class SimpleText2Image {
    
    /**
     * 生成图片的简化方法
     * @param apiKey API密钥
     * @param prompt 图片描述提示词
     * @param outputFilename 输出文件名
     * @return 是否成功
     */
    public static boolean generateImageSimple(String apiKey, String prompt, String outputFilename) {
        try {
            // 构建请求JSON
            String requestJson = String.format(
                "{"
                + "\"model\":\"wanx-v1\","
                + "\"input\":{"
                + "\"prompt\":\"%s\","
                + "\"size\":\"1024*1024\","
                + "\"steps\":20"
                + "}"
                + "}", 
                prompt.replace("\"", "\\\"")
            );
            
            // 发送请求
            String responseJson = sendRequest(apiKey, requestJson);
            
            // 解析响应获取图片URL
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseNode = mapper.readTree(responseJson);
            
            if (responseNode.has("output") && 
                responseNode.get("output").has("results") &&
                responseNode.get("output").get("results").isArray() &&
                responseNode.get("output").get("results").size() > 0) {
                
                String imageUrl = responseNode.get("output")
                    .get("results").get(0).get("url").asText();
                
                System.out.println("生成成功！图片URL: " + imageUrl);
                
                // 下载并保存图片
                return downloadImage(imageUrl, outputFilename);
            } else {
                System.err.println("生成失败: " + responseJson);
                return false;
            }
            
        } catch (Exception e) {
            System.err.println("错误: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 发送HTTP请求
     */
    private static String sendRequest(String apiKey, String jsonBody) throws IOException {
//        URL url = new URL("https://dashscope.aliyuncs.com/api/v1/services/aigc/text2image/image-synthesis");
        URL url = new URL("https://dashscope.aliyuncs.com/api/v1/services/aigc/text2image/image-synthesis");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        // 设置请求头
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setDoOutput(true);
        connection.setConnectTimeout(30000);
        connection.setReadTimeout(60000);
        
        // 发送请求体
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        
        // 读取响应
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                connection.getInputStream(), "utf-8"))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        
        return response.toString();
    }
    
    /**
     * 下载图片
     */
    private static boolean downloadImage(String imageUrl, String filename) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);
            
            try (InputStream inputStream = connection.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(filename)) {
                
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            
            System.out.println("图片已保存为: " + filename);
            return true;
            
        } catch (IOException e) {
            System.err.println("下载图片失败: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 使用示例
     */
    public static void main(String[] args) {
        // 请替换为你的实际API Key
        String apiKey = "sk-0ee8c7f6e1034cbe9f948fe80586146e";
        
        // 生成图片
        boolean success = generateImageSimple(
            apiKey,
            "一只橙色的小猫在阳光下打瞌睡，温馨可爱，高清照片风格",
            "cute_cat.png"
        );
        
        if (success) {
            System.out.println("图片生成完成！");
        } else {
            System.out.println("图片生成失败！");
        }
    }
}
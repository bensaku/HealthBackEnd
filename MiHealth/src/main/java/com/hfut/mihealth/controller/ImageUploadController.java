package com.hfut.mihealth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hfut.mihealth.DTO.*;
import com.hfut.mihealth.entity.Image;
import com.hfut.mihealth.interceptor.UserToken;
import com.hfut.mihealth.service.ImageService;
import com.hfut.mihealth.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ImageUploadController {
    private static final String UPLOAD_DIR = "/Users/wangke/myFile/uploads/";

    private final WebClient webClient;

    public ImageUploadController(WebClient webClient) {
        this.webClient = webClient;
    }


    @Autowired
    private ImageService imageService;

    @PostMapping("/updateAIData")
    public ResponseEntity<String> updateAIData(@RequestParam("foodInfo") String foodInfo,
                                               @RequestParam("imageId") int imageId) {
        if (foodInfo == null) {
            //AI识别出错了
            foodInfo = "bread";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String foodName = null;
        String amount = null;
        String calories = null;
        try {
            // 将JSON字符串映射到Java对象
            JsonNode rootNode = objectMapper.readTree(foodInfo);

            // 提取变量，如果键不存在则返回null
            foodName = rootNode.path("foodname").asText();
            amount = rootNode.path("amount").asText();
            calories = rootNode.path("calories").asText();

            System.out.println("食品名称：" + foodName + "，数量：" + amount + "，卡路里：" + calories);

        } catch (JsonMappingException e) {
            System.out.println("JSON映射错误，请检查JSON结构是否正确。");
        } catch (JsonProcessingException e) {
            System.out.println("JSON处理错误，请确认输入为有效的JSON格式。");
        }
        // AI识别后更新数据库
        imageService.updateImage(imageId, foodName, amount, calories);
        return ResponseEntity.ok("updateAIData");
    }


    @PostMapping("/upload")
    @UserToken
    public ResponseEntity<String> uploadFile(@RequestParam("image") MultipartFile image, @RequestHeader(value = "Authorization") String token) {
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("Failed to upload, the selected file is empty.");
        }
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        Integer userId = TokenUtil.getGuestIdFromToken(token);
        //使用时间戳和userid来确保唯一性，生成新的文件名称
        long timestamp = Instant.now().toEpochMilli();
        String newFileName = timestamp + "_" + userId + ".jpg";
        // 保存文件逻辑
        try {
            byte[] bytes = image.getBytes();
            Path path = Paths.get(UPLOAD_DIR + newFileName);
            Files.write(path, bytes);

            // 在这里可以调用Service层的方法将文件信息保存到数据库中
            Image image1 = saveFileInfoToDatabase(timestamp, userId);
            doAIWorkflow(image1.getImageId(),newFileName);
            return ResponseEntity.ok("File uploaded successfully: " + image.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload " + image.getOriginalFilename() + "!");
        }
    }

    private Image saveFileInfoToDatabase(long timestamp, Integer userId) {
        // 保存文件信息到数据库的方法
        Image image = new Image();
        image.setTimestamp(timestamp);
        image.setDate(new Date());
        image.setUserId(userId);
        return imageService.insertImage(image);
    }

    public void doAIWorkflow(Integer imageId, String newFileName) throws JsonProcessingException {
        // 创建请求体对象
        Pic pic = new Pic();
        pic.setType("image");
        pic.setTransfer_method("remote_url");
        pic.setUrl("http://192.168.1.102:8000/images/"+newFileName);

        Inputs inputs = new Inputs();
        inputs.setPic(pic);
        inputs.setImageId(imageId);

        WorkflowRequest request = new WorkflowRequest();
        request.setInputs(inputs);
        request.setResponse_mode("streaming");
        request.setUser("backEnd");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(request);
        System.out.println(jsonString); // 打印出请求体以确认其格式

        Map<String, Object> body = new HashMap<>();
        body.put("inputs", inputs);
        body.put("user", "backEnd");
        body.put("response_mode", "streaming");

        webClient.post()
                .uri("/workflows/run")
                .header("Authorization", "Bearer app-VWdn4vVz8oyJ22R2tb9mZmwp")
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                //.bodyValue(request)
                .body(BodyInserters.fromValue(body))// 设置请求体
                .retrieve()
                .onStatus(httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(), clientResponse -> {
                    return clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                        System.err.println("Error body: " + errorBody);
                        return Mono.error(new RuntimeException("Error from API: " + errorBody));
                    });
                })
                .bodyToMono(String.class) // 获取响应体作为String
                .subscribe(response -> {
                    System.out.println("Response received: " + response);
                }, error -> {
                    if (error instanceof WebClientResponseException) {
                        WebClientResponseException exception = (WebClientResponseException) error;
                        System.err.println("HTTP Status Code: " + exception.getRawStatusCode());
                        System.err.println("Response Body: " + new String(exception.getResponseBodyAsString()));
                    }
                    System.err.println("Error occurred: " + error.getMessage());
                });
    }
}

package com.hfut.mihealth.service.serviceImpl;

import com.hfut.mihealth.entity.WeekReport;
import com.hfut.mihealth.mapper.WeekReportMapper;
import com.hfut.mihealth.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiServiceImpl implements AiService {

    private final WebClient webClient;

    @Autowired
    private WeekReportMapper weekReportMapper;

    public AiServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public String getWeekReport(Long reportId, String food, String calories, String fat, String carbs, String protein) {
        AiInputs aiInputs = new AiInputs();
        aiInputs.setReportId(reportId);
        aiInputs.setFood(food);
        aiInputs.setCalories(calories);
        aiInputs.setFat(fat);
        aiInputs.setCarbs(carbs);
        aiInputs.setProtein(protein);
        Map<String, Object> body = new HashMap<>();
        body.put("inputs", aiInputs);
        body.put("user", "backEnd");
        body.put("response_mode", "streaming");

        webClient.post()
                .uri("/workflows/run")
                .header("Authorization", "Bearer app-G53jIdYITi2gP5eHHlpI1QRT")
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
                    updateReportWithContent(reportId, null, "failed");
                    System.err.println("Error occurred: " + error.getMessage());
                });
        return "ok";
    }

    public void updateReportWithContent(Long reportId, String content, String status) {
        WeekReport report = weekReportMapper.selectById(reportId);
        if (report != null) {
            report.setReportContent(content);
            report.setStatus(status);
            report.setUpdatedAt(new java.util.Date());
            weekReportMapper.updateById(report);
        }
    }

    class AiInputs {
        private Long reportId;
        private String food;
        private String calories;
        private String fat;
        private String carbs;
        private String protein;

        public Long getReportId() {
            return reportId;
        }

        public void setReportId(Long reportId) {
            this.reportId = reportId;
        }

        public String getFood() {
            return food;
        }

        public void setFood(String food) {
            this.food = food;
        }

        public String getCalories() {
            return calories;
        }

        public void setCalories(String calories) {
            this.calories = calories;
        }

        public String getFat() {
            return fat;
        }

        public void setFat(String fat) {
            this.fat = fat;
        }

        public String getCarbs() {
            return carbs;
        }

        public void setCarbs(String carbs) {
            this.carbs = carbs;
        }

        public String getProtein() {
            return protein;
        }

        public void setProtein(String protein) {
            this.protein = protein;
        }
    }
}

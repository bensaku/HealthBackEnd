package com.hfut.mihealth.service.serviceImpl;

import com.hfut.mihealth.entity.UserFoodRating;
import com.hfut.mihealth.entity.WeekReport;
import com.hfut.mihealth.mapper.WeekReportMapper;
import com.hfut.mihealth.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WeekReportServiceImpl implements WeekReportService {
    @Autowired
    private WeekReportMapper weekReportMapper;

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserFoodRatingService userFoodRatingService;

    @Autowired
    private FoodRecommendService foodRecommendService;

    @Autowired
    private AiService aiService;
    @Autowired
    private FoodService foodService;

    /**
     * 获取周报。如果不存在或已过期，则尝试开始生成过程。
     */
    public WeekReport getOrCreateWeekReport(Integer userId, LocalDate date) {
        LocalDate weekStartDate = date.with(DayOfWeek.MONDAY);
        ZoneId zone = ZoneId.of("Asia/Shanghai"); // 或者你需要的任何时区
        ZonedDateTime zdt = weekStartDate.atStartOfDay(zone);
        java.sql.Date sqlStartDate = java.sql.Date.valueOf(zdt.toLocalDate());
        Optional<WeekReport> optionalReport = Optional.ofNullable(
                weekReportMapper.selectByUserIdAndWeekStartDate(userId, sqlStartDate));

        if (optionalReport.isPresent()) {
            WeekReport report = optionalReport.get();
            if ("completed".equals(report.getStatus())) {
                return report; // 返回已完成的报告
            } else if ("generating".equals(report.getStatus())) {
                return report; // 返回正在生成中的报告
            } else {
                // 如果报告处于其他状态（如 pending 或 failed），重新生成
                startAsyncGeneration(userId, weekStartDate, report.getId());
                report.setStatus("generating");
                return report;
            }
        } else {
            // 创建新记录并开始异步生成
            WeekReport newReport = createNewReport(userId, weekStartDate);
            startAsyncGeneration(userId, weekStartDate, newReport.getId());
            newReport.setStatus("generating");
            return newReport;
        }
    }

    private WeekReport createNewReport(Integer userId, LocalDate weekStartDate) {
        WeekReport report = new WeekReport();
        report.setUserId(userId);
        report.setWeekStartDate(java.sql.Date.valueOf(weekStartDate));
        report.setWeekEndDate(java.sql.Date.valueOf(weekStartDate.plusDays(6)));
        report.setStatus("pending");
        report.setGeneratedAt(new java.util.Date());
        report.setIsExpired(false);
        weekReportMapper.insert(report);
        return report;
    }

    @Async
    public void startAsyncGeneration(Integer userId, LocalDate weekStartDate, Long reportId) {
        try {
            List<UserFoodRating> allRecords = userFoodRatingService.getAllRecords();
            Map<Integer, Map<Integer, Double>> userItemMatrix = foodRecommendService.buildUserItemMatrix(allRecords);
            Map<Integer, Map<Integer, Double>> similarityMatrix = foodRecommendService.calculateSimilarity(userItemMatrix);
            List<Integer> food= foodRecommendService.recommend(userId, userItemMatrix, similarityMatrix, 1);
            String foodName = foodService.getFoodNameById(food.get(0));
            Map<String,Double> takeValue = recordService.getWeekValue(userId, weekStartDate);

            aiService.getWeekReport(
                    reportId,
                    foodName,
                    takeValue.get("calories").toString(),
                    takeValue.get("fat").toString(),
                    takeValue.get("carbs").toString(),
                    takeValue.get("protein").toString()
            );

        } catch (Exception e) {
            updateReportWithContent(reportId, null, "failed");
        }
    }

    @Override
    public void updateReportWithContent(Long reportId, String content, String status) {
        WeekReport report = weekReportMapper.selectById(reportId);
        if (report != null) {
            report.setReportContent(content);
            report.setStatus(status);
            report.setUpdatedAt(new java.util.Date());
            weekReportMapper.updateById(report);
        }
    }
}
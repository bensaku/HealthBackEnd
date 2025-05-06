package com.hfut.mihealth.service;

import com.hfut.mihealth.entity.WeekReport;

import java.time.LocalDate;

public interface WeekReportService {
    WeekReport getOrCreateWeekReport(Integer userId, LocalDate date);

    void updateReportWithContent(Long reportId, String content, String status);
}

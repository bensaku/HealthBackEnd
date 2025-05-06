package com.hfut.mihealth.service;

public interface AiService {
    String getWeekReport(Long reportId, String food, String calories, String fat, String carbs, String protein);
}

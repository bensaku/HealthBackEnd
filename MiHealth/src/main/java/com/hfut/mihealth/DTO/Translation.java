package com.hfut.mihealth.DTO;


import java.util.List;
import java.util.Map;

public class Translation {
    private String from;
    private String to;
    private Map<String,String> trans_result;

    // Getters and Setters
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Map<String,String> getTransResult() {
        return trans_result;
    }

    public void setTransResult(Map<String,String> trans_result) {
        this.trans_result = trans_result;
    }
}

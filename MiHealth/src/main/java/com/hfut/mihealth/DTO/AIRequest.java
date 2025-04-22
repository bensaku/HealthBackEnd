package com.hfut.mihealth.DTO;

public class AIRequest {
    // 定义私有变量，封装数据
    private String foodName;
    private Integer amount;

    // 获取foodName的方法
    public String getFoodName() {
        return foodName;
    }

    // 设置foodName的方法
    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    // 获取amount的方法
    public Integer getAmount() {
        return amount;
    }

    // 设置amount的方法
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
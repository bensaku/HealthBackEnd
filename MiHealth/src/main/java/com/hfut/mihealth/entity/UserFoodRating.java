package com.hfut.mihealth.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

@TableName("UserFoodRating") // 指定数据库表名
public class UserFoodRating {

    @TableId
    private Integer userId;

    @TableField("food_id")
    private Integer foodId;

    @TableField("count")
    private Integer count; // 选择次数

    // 最后记录日期
    @TableField("last_selected_date")
    private Date lastSelectedDate;

    // Getters and Setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getLastSelectedDate() {
        return lastSelectedDate;
    }

    public void setLastSelectedDate(Date lastSelectedDate) {
        this.lastSelectedDate = lastSelectedDate;
    }
}

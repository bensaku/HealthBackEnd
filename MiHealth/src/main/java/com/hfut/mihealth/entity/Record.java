package com.hfut.mihealth.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;

/**
 * 记录;
 * @author : wangke
 * @date : 2025-4-2
 */
@ApiModel(value = "记录",description = "")
public class Record implements Serializable,Cloneable{
    /** 标识符 */
    @ApiModelProperty(name = "标识符",notes = "")
    private Integer recordid ;
    /** 用户id */
    @ApiModelProperty(name = "用户id",notes = "")
    private Integer userid ;
    /** 食物id */
    @ApiModelProperty(name = "食物id",notes = "")
    private Integer foodid ;
    /** 餐别 */
    @ApiModelProperty(name = "餐别",notes = "")
    private String meals ;
    /** 摄入量 */
    @ApiModelProperty(name = "摄入量",notes = "")
    private Integer amount ;
    /** 日期 */
    @ApiModelProperty(name = "日期",notes = "")
    private Date date ;

    /** 标识符 */
    public Integer getRecordid(){
        return this.recordid;
    }
    /** 标识符 */
    public void setRecordid(Integer recordid){
        this.recordid=recordid;
    }
    /** 用户id */
    public Integer getUserid(){
        return this.userid;
    }
    /** 用户id */
    public void setUserid(Integer userid){
        this.userid=userid;
    }
    /** 食物id */
    public Integer getFoodid(){
        return this.foodid;
    }
    /** 食物id */
    public void setFoodid(Integer foodid){
        this.foodid=foodid;
    }
    /** 餐别 */
    public String getMeals(){
        return this.meals;
    }
    /** 餐别 */
    public void setMeals(String meals){
        this.meals=meals;
    }
    /** 摄入量 */
    public Integer getAmount(){
        return this.amount;
    }
    /** 摄入量 */
    public void setAmount(Integer amount){
        this.amount=amount;
    }
    /** 日期 */
    public Date getDate(){
        return this.date;
    }
    /** 日期 */
    public void setDate(Date date){
        this.date=date;
    }
}
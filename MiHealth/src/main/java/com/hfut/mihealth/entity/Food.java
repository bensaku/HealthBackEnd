package com.hfut.mihealth.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 食物;
 * @author : wangke
 * @date : 2025-4-1
 */

@ApiModel(value = "食物",description = "")
public class Food implements Serializable,Cloneable{
    /** 食物ID */
    @ApiModelProperty(name = "食物ID",notes = "")
    @TableId
    private Integer foodid ;
    /** 食物 */
    @ApiModelProperty(name = "食物",notes = "")
    private String name ;
    /** 卡路里 */
    @ApiModelProperty(name = "卡路里",notes = "")
    private Integer calories ;
    /** 蛋白质 */
    @ApiModelProperty(name = "蛋白质",notes = "")
    private Double protein ;
    /** 碳水化合物 */
    @ApiModelProperty(name = "碳水化合物",notes = "")
    private Double carbs ;
    /** 脂肪 */
    @ApiModelProperty(name = "脂肪",notes = "")
    private Double fat ;
    /** 食物类型 */
    @ApiModelProperty(name = "食物类型",notes = "")
    private String foodtype ;
    /** 其他营养信息 */
    @ApiModelProperty(name = "其他营养信息",notes = "")
    private String othernutritionalinfo ;
    /** 图片 */
    @ApiModelProperty(name = "图片",notes = "")
    private String imageurl ;

    /** 食物ID */
    public Integer getFoodid(){
        return this.foodid;
    }
    /** 食物ID */
    public void setFoodid(Integer foodid){
        this.foodid=foodid;
    }
    /** 食物 */
    public String getName(){
        return this.name;
    }
    /** 食物 */
    public void setName(String name){
        this.name=name;
    }
    /** 卡路里 */
    public Integer getCalories(){
        return this.calories;
    }
    /** 卡路里 */
    public void setCalories(Integer calories){
        this.calories=calories;
    }
    /** 蛋白质 */
    public Double getProtein(){
        return this.protein;
    }
    /** 蛋白质 */
    public void setProtein(Double protein){
        this.protein=protein;
    }
    /** 碳水化合物 */
    public Double getCarbs(){
        return this.carbs;
    }
    /** 碳水化合物 */
    public void setCarbs(Double carbs){
        this.carbs=carbs;
    }
    /** 脂肪 */
    public Double getFat(){
        return this.fat;
    }
    /** 脂肪 */
    public void setFat(Double fat){
        this.fat=fat;
    }
    /** 食物类型 */
    public String getFoodtype(){
        return this.foodtype;
    }
    /** 食物类型 */
    public void setFoodtype(String foodtype){
        this.foodtype=foodtype;
    }
    /** 其他营养信息 */
    public String getOthernutritionalinfo(){
        return this.othernutritionalinfo;
    }
    /** 其他营养信息 */
    public void setOthernutritionalinfo(String othernutritionalinfo){
        this.othernutritionalinfo=othernutritionalinfo;
    }
    /** 图片 */
    public String getImageurl(){
        return this.imageurl;
    }
    /** 图片 */
    public void setImageurl(String imageurl){
        this.imageurl=imageurl;
    }
}
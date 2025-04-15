package com.hfut.mihealth.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户;
 * @author : http://www.chiner.pro
 * @date : 2025-4-5
 */
@ApiModel(value = "用户",description = "")
@TableName("User")
public class User implements Serializable,Cloneable{
    /** 用户ID */
    @ApiModelProperty(name = "用户ID",notes = "")
    @TableId
    private Integer userid ;
    /** 用户名 */
    @ApiModelProperty(name = "用户名",notes = "")
    private String username ;
    /** 密码哈希值 */
    @ApiModelProperty(name = "密码哈希值",notes = "")
    private String passwordhash ;
    /** 电子邮件 */
    @ApiModelProperty(name = "电子邮件",notes = "")
    private String email ;
    /** 账户创建日期 */
    @ApiModelProperty(name = "账户创建日期",notes = "")
    private Date createdate ;
    /** 是否为游客账户 */
    @ApiModelProperty(name = "是否为游客账户",notes = "")
    private Boolean isguest ;

    /** 用户ID */
    public Integer getUserid(){
        return this.userid;
    }
    /** 用户ID */
    public void setUserid(Integer userid){
        this.userid=userid;
    }
    /** 用户名 */
    public String getUsername(){
        return this.username;
    }
    /** 用户名 */
    public void setUsername(String username){
        this.username=username;
    }
    /** 密码哈希值 */
    public String getPasswordhash(){
        return this.passwordhash;
    }
    /** 密码哈希值 */
    public void setPasswordhash(String passwordhash){
        this.passwordhash=passwordhash;
    }
    /** 电子邮件 */
    public String getEmail(){
        return this.email;
    }
    /** 电子邮件 */
    public void setEmail(String email){
        this.email=email;
    }
    /** 账户创建日期 */
    public Date getCreatedate(){
        return this.createdate;
    }
    /** 账户创建日期 */
    public void setCreatedate(Date createdate){
        this.createdate=createdate;
    }
    /** 是否为游客账户 */
    public Boolean getIsguest(){
        return this.isguest;
    }
    /** 是否为游客账户 */
    public void setIsguest(Boolean isguest){
        this.isguest=isguest;
    }
}
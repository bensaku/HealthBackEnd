package com.hfut.mihealth.DTO;

public class GuestResponse {
    private String token;
    private Integer userid;

    // Constructors, getters and setters

    public GuestResponse(String token, Integer userid) {
        this.token = token;
        this.userid = userid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}

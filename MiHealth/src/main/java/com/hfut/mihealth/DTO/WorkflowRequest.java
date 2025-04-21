package com.hfut.mihealth.DTO;


public class WorkflowRequest {
    private Inputs inputs;
    private String response_mode;
    private String user;

    // Getters and Setters
    public Inputs getInputs() { return inputs; }
    public void setInputs(Inputs inputs) { this.inputs = inputs; }
    public String getResponse_mode() { return response_mode; }
    public void setResponse_mode(String response_mode) { this.response_mode = response_mode; }
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
}


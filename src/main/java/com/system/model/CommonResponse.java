package com.system.model;

public class CommonResponse {

    private Object data;
    private int status;
    private String message;

    public CommonResponse() {
    }

    public CommonResponse(Object data, int status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static CommonResponse generateResponse(Object data, int status, String message){
        return new CommonResponse(data,status,message);
    }
}

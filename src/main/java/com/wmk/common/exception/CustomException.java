package com.wmk.common.exception;

public class CustomException extends Exception{
    private String code;
    private String message;
    public CustomException(String code,String message){
        this.code = code;
        this.message = message;
    }

    public CustomException(String message){
        super(message);
        this.message = message;
    }

    public CustomException(String message, Throwable throwable) {
        super(message,throwable);
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

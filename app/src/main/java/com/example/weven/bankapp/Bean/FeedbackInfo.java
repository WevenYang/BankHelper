package com.example.weven.bankapp.Bean;

public class FeedbackInfo {


    /**
     * Code : 200
     * Data : 18-11-09 10:01:18
     * Success : false
     * Message : 认证失败，请重新登陆
     */

    private int Code;
    private String Data;
    private boolean Success;
    private String Message;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}

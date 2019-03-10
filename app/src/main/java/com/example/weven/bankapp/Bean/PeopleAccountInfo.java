package com.example.weven.bankapp.Bean;

public class PeopleAccountInfo {
    /**
     * Code : 200
     * Data : {"token":"825847e99b3fef724cc28c7fad67c782-e10adc3949ba59abbe56e057f20f883e-1543504663","userId":"1"}
     * Success : true
     * Message : 登陆成功
     */

    private int Code;
    private DataBean Data;
    private boolean Success;
    private String Message;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
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

    public static class DataBean {

        private String userId;
        private String nickName;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
    }
}

package com.example.weven.bankapp.Bean;

public class MyAccountInfo {

    /**
     * Code : 200
     * Data : {"account":"10000","fixed_deposit":"5003","current_deposit":"4995"}
     * Success : true
     * Message : 查询成功
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
        /**
         * account : 10000
         * fixed_deposit : 5003
         * current_deposit : 4995
         */

        private String account;
        private String fixed_deposit;
        private String current_deposit;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getFixed_deposit() {
            return fixed_deposit;
        }

        public void setFixed_deposit(String fixed_deposit) {
            this.fixed_deposit = fixed_deposit;
        }

        public String getCurrent_deposit() {
            return current_deposit;
        }

        public void setCurrent_deposit(String current_deposit) {
            this.current_deposit = current_deposit;
        }
    }
}

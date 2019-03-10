package com.example.weven.bankapp.Bean;

import java.util.List;

public class GetBankCardInfo {

    /**
     * Code : 200
     * Data : [{"id":"1","u_id":"1","cardnum":"1234567890123456"},{"id":"2","u_id":"1","cardnum":"1234567890123455"}]
     * Success : true
     * Message : 操作成功
     */

    private int Code;
    private boolean Success;
    private String Message;
    private List<DataBean> Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * id : 1
         * u_id : 1
         * cardnum : 1234567890123456
         */

        private String id;
        private String u_id;
        private String cardnum;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getU_id() {
            return u_id;
        }

        public void setU_id(String u_id) {
            this.u_id = u_id;
        }

        public String getCardnum() {
            return cardnum;
        }

        public void setCardnum(String cardnum) {
            this.cardnum = cardnum;
        }
    }
}

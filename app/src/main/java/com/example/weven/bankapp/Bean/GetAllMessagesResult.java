package com.example.weven.bankapp.Bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */

public class GetAllMessagesResult {


    /**
     * Code : 200
     * Data : [{"id":"1","type":"0","num":"1000","date":"2019-01-09"}]
     * Success : true
     * Message : 获取成功
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
         * type : 0
         * num : 1000
         * date : 2019-01-09
         */

        private String id;
        private String type;
        private String num;
        private String date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}

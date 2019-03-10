package com.example.weven.bankapp.Bean;

import java.util.List;

public class PersonInfo {

    /**
     * Code : 200
     * Data : [{"id":"1","account":"weven","password":"654321","paypassword":"222222","nickname":"weven","sex":"0","phonenum":"324234","cardnum":"23423","cardtype":"0","idcard":"0","icon":"http://img4q.duitang.com/uploads/item/201408/27/20140827154904_NRkaa.thumb.700_0.jpeg"}]
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
         * account : weven
         * password : 654321
         * paypassword : 222222
         * nickname : weven
         * sex : 0
         * phonenum : 324234
         * cardnum : 23423
         * cardtype : 0
         * idcard : 0
         * icon : http://img4q.duitang.com/uploads/item/201408/27/20140827154904_NRkaa.thumb.700_0.jpeg
         */

        private String id;
        private String account;
        private String password;
        private String paypassword;
        private String nickname;
        private String sex;
        private String phonenum;
        private String cardnum;
        private String cardtype;
        private String idcard;
        private String icon;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPaypassword() {
            return paypassword;
        }

        public void setPaypassword(String paypassword) {
            this.paypassword = paypassword;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhonenum() {
            return phonenum;
        }

        public void setPhonenum(String phonenum) {
            this.phonenum = phonenum;
        }

        public String getCardnum() {
            return cardnum;
        }

        public void setCardnum(String cardnum) {
            this.cardnum = cardnum;
        }

        public String getCardtype() {
            return cardtype;
        }

        public void setCardtype(String cardtype) {
            this.cardtype = cardtype;
        }

        public String getIdcard() {
            return idcard;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}

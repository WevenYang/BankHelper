package com.example.weven.bankapp.Bean;

public class Url {
//    private static String baseUrl = "http://192.168.43.94/dashboard/www/Bank/home/index/";
//    private static String baseUrl = "http://localhost/dashboard/www/Bank/home/index/";
    private static String baseUrl = "http://192.168.6.46/dashboard/www/Thinkphp3.2/Bank/home/index/";

    public static String login = baseUrl + "login";     //登陆接口
    public static String feedback = baseUrl + "feedback";  //意见反馈
    public static String risk = baseUrl + "riskReport";     //风险举报
    public static String resetPwd = baseUrl + "resetPwd";   //登陆密码修改
    public static String resetPayPwd = baseUrl + "resetPayPwd";    //支付密码修改
    public static String getPeopleAccount = baseUrl + "getPeopleAccount";       //获取转账用户
    public static String transferToAccount = baseUrl + "transferToAccount";      //转账到用户账户
    public static String queryBalance = baseUrl + "queryBalance";       //获取余额
    public static String register = baseUrl + "register";       //注册
    public static String transfer = baseUrl + "transfer";       //定期操作
    public static String editInfo = baseUrl + "editPersonInfo";       //修改资料
    public static String getInfo = baseUrl + "getPersonInfo";        //获取个人资料
    public static String getMyAccount = baseUrl + "getMyAccount";       //获取个人账户
    public static String getMyMessage = baseUrl + "getBankMessage";       //获取个人信息
    public static String recharge = baseUrl + "recharge";       //充值
    public static String withDraw = baseUrl + "withDraw";       //提现
    public static String getFixDeposit = baseUrl + "getFixDeposit";         //获取定期余额
    public static String getCurrentDeposit = baseUrl + "getCurrentDeposit";     //获取活期定额
    public static String getPersonCard = baseUrl + "getPersonCard";         //获取银行卡信息
    public static String addPersonCard = baseUrl + "addPersonCard";         //增加银行卡信息
    public static String deletePersonCard = baseUrl + "deletePersonCard";       //解绑银行卡
}

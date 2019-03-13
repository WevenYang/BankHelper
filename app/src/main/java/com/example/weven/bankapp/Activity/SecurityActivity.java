package com.example.weven.bankapp.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.weven.bankapp.Adapter.CommonRecyclerAdapter;
import com.example.weven.bankapp.Adapter.ViewHolderR;
import com.example.weven.bankapp.Bean.AddBankCard;
import com.example.weven.bankapp.Bean.ExitAndRelogin;
import com.example.weven.bankapp.Bean.LeftHorizontalBarItemInfo;
import com.example.weven.bankapp.Bean.LoginInfo;
import com.example.weven.bankapp.Bean.ResultInfo;
import com.example.weven.bankapp.Bean.Url;
import com.example.weven.bankapp.R;
import com.example.weven.bankapp.View.ModifyPayPassWordDig;
import com.example.weven.bankapp.View.RecyclerViewLinearLayoutDivider;
import com.example.weven.bankapp.View.TextViewPlus;
import com.example.weven.bankapp.util.HttpUtil;
import com.example.weven.bankapp.util.IntentUtil;
import com.example.weven.bankapp.util.LogUtil;
import com.example.weven.bankapp.util.MD5Util;
import com.example.weven.bankapp.util.ToastUtil;
import com.example.weven.bankapp.util.okhttp.callback.ObjectCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class SecurityActivity extends BaseActivity {

    RecyclerView rv_security;
    ArrayList itemInfoList;
    ModifyPayPassWordDig loginPwdDig, payPwdDig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        rv_security = (RecyclerView) findViewById(R.id.rv_security);
        EventBus.getDefault().register(SecurityActivity.this);
        initItemInfoList();
        initData();
    }

    @Override
    protected void initData() {
        rv_security.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_security.addItemDecoration(new RecyclerViewLinearLayoutDivider(RecyclerViewLinearLayoutDivider.VERTICAL_LIST, getResources().getDrawable(R.drawable.sp_divider_horizontal_rv)));
        rv_security.setAdapter(new CommonRecyclerAdapter<LeftHorizontalBarItemInfo>(this, R.layout.cv_horizontal_bar_settings_acy, itemInfoList) {

            @Override
            public void convert(final ViewHolderR holder, LeftHorizontalBarItemInfo horizontalBarInfo) {
                holder.setOnClickListener(R.id.prl_mine_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (holder.getAdapterPosition()) {
                            case 0:
                                IntentUtil.startActivity(SecurityActivity.this, BandCardActivity.class);
                                break;
                            case 1:
                                loginPwdDig = new ModifyPayPassWordDig(SecurityActivity.this, "修改登陆密码");
                                loginPwdDig.show();
                                loginPwdDig.setOnConfirmClickListener(new ModifyPayPassWordDig.OnConfirmClickListener() {
                                    @Override
                                    public void onConfirmClick(String originalPassWord, String newPassWord) {
                                        if (MD5Util.GetMD5Code(originalPassWord).equals(BaseApplication.getPassword())){
                                            changePwd(originalPassWord, newPassWord);
                                        }else {
                                            ToastUtil.showBottomToast("原登陆密码错误，请重新输入");
                                        }
                                    }
                                });
                                break;
                            case 2:
                                if (BaseApplication.getPayPassword().equals("")){
                                    ToastUtil.showBottomToast("您还没有设置支付密码");
                                }else {
                                    payPwdDig = new ModifyPayPassWordDig(SecurityActivity.this, "修改支付密码");
                                    payPwdDig.show();
                                    payPwdDig.setOnConfirmClickListener(new ModifyPayPassWordDig.OnConfirmClickListener() {
                                        @Override
                                        public void onConfirmClick(String originalPassWord, String newPassWord) {
                                            if (MD5Util.GetMD5Code(originalPassWord).equals(BaseApplication.getPayPassword())){
                                                changePayPwd(originalPassWord, newPassWord);
                                            }else {
                                                ToastUtil.showBottomToast("原支付密码错误，请重新输入");
                                            }
                                        }
                                    });
                                }
                                break;
                            case 3:
//                                    IntentUtil.startActivity(SecurityActivity.this, MachineInfoActivity.class);
                                IntentUtil.startActivity(SecurityActivity.this, RiskActivity.class);
                                break;
//                            case 4:
//                                    IntentUtil.startActivity(SecurityActivity.this, RiskActivity.class);
//                                break;
                            default:
                                break;

                        }
                    }
                });
                TextViewPlus textViewPlus = holder.getView(R.id.tvp_left_horizontalBarCvSettingsAcy);
                textViewPlus.setCompoundImg(TextViewPlus.LEFT_IMG, horizontalBarInfo.getLeftImgResourceId(), 0.035f, 0.035f, "ScreenHeight");
                textViewPlus.setText(horizontalBarInfo.getLeftText());
            }
        });
    }

    private void initItemInfoList() {
        itemInfoList = new ArrayList<>();
        itemInfoList.add(new LeftHorizontalBarItemInfo("绑定银行卡", R.mipmap.nav_icon_bank));
        itemInfoList.add(new LeftHorizontalBarItemInfo("修改登陆密码", R.mipmap.nav_icon_changepwd));
        itemInfoList.add(new LeftHorizontalBarItemInfo("修改支付密码", R.mipmap.nav_icon_lock));
//        itemInfoList.add(new LeftHorizontalBarItemInfo("绑定设备", R.mipmap.nav_icon_band));
        itemInfoList.add(new LeftHorizontalBarItemInfo("风险举报", R.mipmap.nav_icon_alert));
    }

    public void changePwd(String originalPassWord, String newPassWord){
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getToken());
        params.put("formerPwd", originalPassWord);
        params.put("newPwd", MD5Util.GetMD5Code(newPassWord));
        params.put("userId", BaseApplication.getUserId() + "");
        HttpUtil.postResponse(Url.resetPwd, params, this, new ObjectCallBack<ResultInfo>(ResultInfo.class) {
            @Override
            public void onSuccess(ResultInfo response) {
                if (response == null){
                    ToastUtil.showBottomToast(R.string.load_failure);
                }else {
                    if (response.isSuccess()){
                        ToastUtil.showBottomToast(response.getMessage());
                        loginPwdDig.dismiss();
                        EventBus.getDefault().post(new ExitAndRelogin());
                    }else {
                        ToastUtil.showBottomToast(response.getMessage());
                    }
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                ToastUtil.showBottomToast(R.string.load_failure);
            }
        });
    }

    public void changePayPwd(String originalPassWord, final String newPassWord){
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getToken());
        params.put("formerPwd", originalPassWord);
        params.put("newPwd", MD5Util.GetMD5Code(newPassWord));
        params.put("userId", BaseApplication.getUserId() + "");
        HttpUtil.postResponse(Url.resetPayPwd, params, this, new ObjectCallBack<ResultInfo>(ResultInfo.class) {
            @Override
            public void onSuccess(ResultInfo response) {
                if (response == null){
                    ToastUtil.showBottomToast(R.string.load_failure);
                }else {
                    if (response.isSuccess()){
                        ToastUtil.showBottomToast(response.getMessage());
                        payPwdDig.dismiss();
                        BaseApplication.setPayPassword(newPassWord);
                    }else {
                        ToastUtil.showBottomToast(response.getMessage());
                    }
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                ToastUtil.showBottomToast(R.string.load_failure);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void exitAndRelogin(ExitAndRelogin event){
        BaseApplication.setPayPassword("");
        BaseApplication.setPassword("");
        BaseApplication.setToken("");
        BaseApplication.setUserId("");
        IntentUtil.startActivity(SecurityActivity.this, LoginActivity.class);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

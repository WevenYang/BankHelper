package com.example.weven.bankapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.weven.bankapp.Bean.FeedbackInfo;
import com.example.weven.bankapp.Bean.RechargeSuccessful;
import com.example.weven.bankapp.Bean.Url;
import com.example.weven.bankapp.R;
import com.example.weven.bankapp.util.HttpUtil;
import com.example.weven.bankapp.util.IntentUtil;
import com.example.weven.bankapp.util.ToastUtil;
import com.example.weven.bankapp.util.okhttp.callback.ObjectCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class Exchange1 extends BaseActivity {

    TextView importIn, tv_deposit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange1);
        importIn = (TextView) findViewById(R.id.importIn);
        tv_deposit = (TextView) findViewById(R.id.tv_deposit);
        EventBus.getDefault().register(Exchange1.this);
        initData();
        importIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtil.startActivity(Exchange1.this, RechargeActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getToken());
        params.put("id", BaseApplication.getUserId());
        HttpUtil.postResponse(Url.getCurrentDeposit, params, this, new ObjectCallBack<FeedbackInfo>(FeedbackInfo.class) {
            @Override
            public void onSuccess(FeedbackInfo response) {
                if (response == null){
                    ToastUtil.showBottomToast(R.string.load_failure);
                }else {
                    if (response.isSuccess()){
                        ToastUtil.showBottomToast(response.getMessage());
                        tv_deposit.setText(response.getData().toString());
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
    public void refresh(RechargeSuccessful event){
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(Exchange1.this);
    }
}

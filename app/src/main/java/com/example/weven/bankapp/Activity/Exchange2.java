package com.example.weven.bankapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.weven.bankapp.Bean.FeedbackInfo;
import com.example.weven.bankapp.Bean.RefreshFixDeposit;
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

public class Exchange2 extends BaseActivity {

    TextView importIn, importOut, tv_deposit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange2);
        EventBus.getDefault().register(Exchange2.this);
        importIn = (TextView) findViewById(R.id.importIn);
        importOut = (TextView) findViewById(R.id.importOut);
        tv_deposit = (TextView) findViewById(R.id.tv_deposit);
        initData();
        importIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", 0);
                bundle.putString("num", tv_deposit.getText().toString());
                IntentUtil.startActivity(Exchange2.this, TransferActivity.class, bundle);
            }
        });
        importOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 = new Bundle();
                bundle1.putInt("type", 1);
                bundle1.putString("num", tv_deposit.getText().toString());
                IntentUtil.startActivity(Exchange2.this, TransferActivity.class, bundle1);
            }
        });
    }

    @Override
    protected void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getToken());
        params.put("id", BaseApplication.getUserId());
        HttpUtil.postResponse(Url.getFixDeposit, params, this, new ObjectCallBack<FeedbackInfo>(FeedbackInfo.class) {
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
    public void refreshEvent(RefreshFixDeposit event){
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

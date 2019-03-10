package com.example.weven.bankapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.weven.bankapp.Bean.BalanceInfo;
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

public class QueryBalanceActivity extends BaseActivity {

    TextView tv_money;
    Button bt_recharge;
    Button bt_with_draws;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_balance);
        tv_money = (TextView) findViewById(R.id.tv_money);
        bt_recharge = (Button) findViewById(R.id.bt_recharge);
        bt_with_draws = (Button) findViewById(R.id.bt_with_draws);
        EventBus.getDefault().register(QueryBalanceActivity.this);
        initData();
        bt_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtil.startActivity(QueryBalanceActivity.this, RechargeActivity.class);
            }
        });
        bt_with_draws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("num", tv_money.getText().toString());
                IntentUtil.startActivity(QueryBalanceActivity.this, WithDrawActivity.class, b);
            }
        });
    }

    public void initData(){
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getToken());
        params.put("id", BaseApplication.getUserId());
        HttpUtil.postResponse(Url.queryBalance, params, this, new ObjectCallBack<BalanceInfo>(BalanceInfo.class) {
            @Override
            public void onSuccess(BalanceInfo response) {
                if (response == null){
                    ToastUtil.showBottomToast(R.string.load_failure);
                }else {
                    if (response.isSuccess()){
                        tv_money.setText(response.getData().getBalance());
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
        EventBus.getDefault().unregister(QueryBalanceActivity.this);
    }
}

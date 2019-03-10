package com.example.weven.bankapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weven.bankapp.Bean.FeedbackInfo;
import com.example.weven.bankapp.Bean.RechargeSuccessful;
import com.example.weven.bankapp.Bean.RefreshFixDeposit;
import com.example.weven.bankapp.Bean.Url;
import com.example.weven.bankapp.R;
import com.example.weven.bankapp.View.CommonToolBar;
import com.example.weven.bankapp.util.HttpUtil;
import com.example.weven.bankapp.util.TextUtil;
import com.example.weven.bankapp.util.ToastUtil;
import com.example.weven.bankapp.util.okhttp.callback.ObjectCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class TransferActivity extends BaseActivity {

    Button bt_transfer_next;
    EditText et_num;
    TextView tv_hint, tv_money;
    CommonToolBar cb_title_announcement;
    Bundle bundle;
    String currentDeposit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        EventBus.getDefault().register(TransferActivity.this);
        bundle = getIntent().getBundleExtra("Bundle");
        bt_transfer_next = (Button) findViewById(R.id.bt_transfer_next);
        et_num = (EditText)findViewById(R.id.et_num);
        cb_title_announcement = (CommonToolBar) findViewById(R.id.cb_title_announcement);
        tv_hint = (TextView) findViewById(R.id.tv_hint);
        tv_money = (TextView) findViewById(R.id.tv_money);
        if (bundle.getInt("type") == 0){
            tv_hint.setText("当前可转入定期的余额为");
            getCurrentDeposit();
        }else if(bundle.getInt("type") == 1){
            tv_hint.setText("当前可转出定期的余额为");
            tv_money.setText(bundle.get("num").toString());
        }else {

        }
        et_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtil.isValidate(charSequence)){
                    bt_transfer_next.setClickable(true);
                    bt_transfer_next.setSelected(true);
                }else {
                    bt_transfer_next.setClickable(false);
                    bt_transfer_next.setSelected(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        bt_transfer_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtil.isValidate(et_num.getText())){
                    switch (bundle.getInt("type")){
                        case 0:
                            if (Integer.valueOf(currentDeposit) >= Integer.valueOf(et_num.getText().toString())){
                                postToTransfer();
                            }else {
                                ToastUtil.showBottomToast("不可超出当前余额");
                            }
                            break;
                        case 1:
                            if (Integer.valueOf(bundle.getString("num")) >= Integer.valueOf(et_num.getText().toString())){
                                postToTransfer();
                            }else {
                                ToastUtil.showBottomToast("不可超出当前余额");
                            }
                            break;
                        default:
                            break;
                    }

                }

            }
        });
    }

    //获取活期余额
    public void getCurrentDeposit(){
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
                        currentDeposit = response.getData().toString();
                        tv_money.setText(currentDeposit);
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

    public void postToTransfer(){
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getToken());
        params.put("userid", BaseApplication.getUserId());
        params.put("num", et_num.getText().toString());
        params.put("type", bundle.getInt("type") + "");
        params.put("fix_deposit", bundle.getString("num"));
        HttpUtil.postResponse(Url.transfer, params, this, new ObjectCallBack<FeedbackInfo>(FeedbackInfo.class) {
            @Override
            public void onSuccess(FeedbackInfo response) {
                if (response == null){
                    ToastUtil.showBottomToast(R.string.load_failure);
                }else {
                    if (response.isSuccess()){
                        ToastUtil.showBottomToast(response.getMessage());
                        bt_transfer_next.setSelected(false);
                        bt_transfer_next.setEnabled(false);
                        EventBus.getDefault().post(new RefreshFixDeposit());
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
       finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

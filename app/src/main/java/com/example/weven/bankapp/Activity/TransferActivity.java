package com.example.weven.bankapp.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
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
import com.example.weven.bankapp.View.EnterPayPassWordPpw;
import com.example.weven.bankapp.View.PassWordView;
import com.example.weven.bankapp.util.BigDecimalUtil;
import com.example.weven.bankapp.util.HttpUtil;
import com.example.weven.bankapp.util.IntentUtil;
import com.example.weven.bankapp.util.MD5Util;
import com.example.weven.bankapp.util.TextUtil;
import com.example.weven.bankapp.util.ToastUtil;
import com.example.weven.bankapp.util.okhttp.callback.ObjectCallBack;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class TransferActivity extends BaseActivity {

    Button bt_transfer_next;
    EditText et_num, et_month;
    TextView tv_hint, tv_money, tv_hint_money;
    CommonToolBar cb_title_announcement;
    Bundle bundle;
    String currentDeposit;
    EnterPayPassWordPpw enterPayPassWordPpw;
    PercentLinearLayout pll_parent, pll_dingqi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        EventBus.getDefault().register(TransferActivity.this);
        bundle = getIntent().getBundleExtra("Bundle");
        bt_transfer_next = (Button) findViewById(R.id.bt_transfer_next);
        et_num = (EditText)findViewById(R.id.et_num);
        et_month = (EditText) findViewById(R.id.et_month);
        cb_title_announcement = (CommonToolBar) findViewById(R.id.cb_title_announcement);
        tv_hint = (TextView) findViewById(R.id.tv_hint);
        tv_money = (TextView) findViewById(R.id.tv_money);
        pll_parent = (PercentLinearLayout) findViewById(R.id.pll_parent);
        pll_dingqi = (PercentLinearLayout) findViewById(R.id.pll_dingqi);
        tv_hint_money = (TextView) findViewById(R.id.tv_hint_money);
        if (bundle.getInt("type") == 0){
            pll_dingqi.setVisibility(View.VISIBLE);
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
                    if (bundle.getInt("type") == 0){
                        tv_hint_money.setVisibility(View.VISIBLE);
                        if (TextUtil.isValidate(et_month.getText().toString())){
                            setGiveMoneyText(Integer.valueOf(charSequence.toString()), Integer.valueOf(et_month.getText().toString()));
                        }else {
                            setGiveMoneyText(Integer.valueOf(charSequence.toString()), 1);
                        }

                    }
                }else {
                    bt_transfer_next.setClickable(false);
                    bt_transfer_next.setSelected(false);
                    tv_hint_money.setVisibility(View.GONE);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_month.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtil.isValidate(et_num.getText().toString())){
                    if (TextUtil.isValidate(charSequence.toString())){
                        setGiveMoneyText(Integer.valueOf(et_num.getText().toString()),
                                Integer.valueOf(charSequence.toString()));
                    }else {
                        setGiveMoneyText(Integer.valueOf(et_num.getText().toString()), 1);
                    }

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
                                showEnterPayPassWordPpw();
                            }else {
                                ToastUtil.showBottomToast("不可超出当前余额");
                            }
                            break;
                        case 1:
                            if (Integer.valueOf(bundle.getString("num")) >= Integer.valueOf(et_num.getText().toString())){
                                showEnterPayPassWordPpw();
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

    //输入支付密码进行转入转出操作
    private void showEnterPayPassWordPpw() {
        if (enterPayPassWordPpw != null && !enterPayPassWordPpw.isShowing()) {
            enterPayPassWordPpw.restore();
            enterPayPassWordPpw.showAtLocation(pll_parent, 0, 0, Gravity.BOTTOM);
        } else if (enterPayPassWordPpw != null && enterPayPassWordPpw.isShowing()) {
            return;
        } else {
            enterPayPassWordPpw = new EnterPayPassWordPpw(this);
            enterPayPassWordPpw.setOnPassWordEnterCompletedListener(new PassWordView.OnPassWordEnterCompletedListener() {
                @Override
                public void onPassWordEnterCompleted(String passWord) {
                    enterPayPassWordPpw.startLoading();
                    if (MD5Util.GetMD5Code(passWord).equals(BaseApplication.getPayPassword())){
                        postToTransfer();

                    }else {
                        enterPayPassWordPpw.completeLoading(false);
                        enterPayPassWordPpw.setToastMessage("操作失败");
                    }

                }
            });

            enterPayPassWordPpw.setOnDialogClickListener(new EnterPayPassWordPpw.OnDialogClickListener() {
                @Override
                public void onDialogClick() {
                    enterPayPassWordPpw.dismiss();
//                    EventBus.getDefault().post(new GoToSetPayPassWordMessageEvent());
                    finish();
                }
            });
            enterPayPassWordPpw.setOnOperateCompletedListener(new EnterPayPassWordPpw.OnOperateCompletedListener() {
                @Override
                public void onOperateCompleted(boolean isSuccess) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            EventBus.getDefault().post(new OrderPaySuccessfullyMessageEvent());
                            finish();
                        }
                    }, 1500);
                }
            });
            enterPayPassWordPpw.showAtLocation(pll_parent, 0, 0, Gravity.BOTTOM);
        }
    }

    //获取活期余额
    public void getCurrentDeposit(){
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getToken());
        params.put("id", BaseApplication.getCardId());
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
        params.put("userid", BaseApplication.getCardId());
        params.put("num", et_num.getText().toString());
        params.put("type", bundle.getInt("type") + "");
        params.put("fix_deposit", bundle.getString("num"));
        params.put("duration", et_month.getText().toString());
        HttpUtil.postResponse(Url.transfer, params, this, new ObjectCallBack<FeedbackInfo>(FeedbackInfo.class) {
            @Override
            public void onSuccess(FeedbackInfo response) {
                if (response == null){
                    ToastUtil.showBottomToast(R.string.load_failure);
                }else {
                    if (response.isSuccess()){
                        enterPayPassWordPpw.completeLoading(true);
                        enterPayPassWordPpw.setToastMessage("操作成功");
                        bt_transfer_next.setSelected(false);
                        bt_transfer_next.setEnabled(false);

                        IntentUtil.startActivity(TransferActivity.this, TransferFlowActivity.class);
                    }else {
                        enterPayPassWordPpw.completeLoading(false);
                        enterPayPassWordPpw.setToastMessage("操作失败("+ response.getMessage() + ")");
                    }

                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                ToastUtil.showBottomToast(R.string.load_failure);
            }
        });
    }

    private void setGiveMoneyText(double rechargeMoney, int month) {
        double giveMoney = rechargeMoney * 0.023 / 365 * month * 30;
        tv_hint_money.setText("到期可获得" + String.format("%.2f", giveMoney) + "元");
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

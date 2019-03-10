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
import com.example.weven.bankapp.Bean.Url;
import com.example.weven.bankapp.R;
import com.example.weven.bankapp.View.EnterPayPassWordPpw;
import com.example.weven.bankapp.View.PassWordView;
import com.example.weven.bankapp.util.HttpUtil;
import com.example.weven.bankapp.util.LogUtil;
import com.example.weven.bankapp.util.TextUtil;
import com.example.weven.bankapp.util.ToastUtil;
import com.example.weven.bankapp.util.okhttp.callback.ObjectCallBack;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class PeopleAccountActivity extends BaseActivity {

    Button bt_transfer_next;
    EnterPayPassWordPpw enterPayPassWordPpw;
    PercentLinearLayout pll_parent;
    TextView tv_name;
    EditText et_num;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_account);
        bundle = getIntent().getBundleExtra("Bundle");
        bt_transfer_next = (Button) findViewById(R.id.bt_transfer_next);
        pll_parent = (PercentLinearLayout) findViewById(R.id.pll_parent);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name.setText(bundle.getString("nickname").toString());
        et_num = (EditText)findViewById(R.id.tv_num);
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
                showEnterPayPassWordPpw();
            }
        });
    }

    //输入支付密码进行支付
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
                    if (passWord.equals(BaseApplication.getPayPassword().toString())){
                        payOrder();

                    }else {
                        enterPayPassWordPpw.completeLoading(false);
                        enterPayPassWordPpw.setToastMessage("支付失败");
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

    public void payOrder(){
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getToken());
        params.put("id", BaseApplication.getUserId());
        params.put("num", et_num.getText().toString());
        params.put("toAccountId", bundle.getString("account"));
        HttpUtil.postResponse(Url.transferToAccount, params, this, new ObjectCallBack<FeedbackInfo>(FeedbackInfo.class) {
            @Override
            public void onSuccess(FeedbackInfo response) {
                if (response.isSuccess()){
                    enterPayPassWordPpw.completeLoading(true);
                    enterPayPassWordPpw.setToastMessage("支付成功");
                }else {
                    enterPayPassWordPpw.completeLoading(false);
                    enterPayPassWordPpw.setToastMessage("支付失败("+ response.getMessage() + ")");
                }
            }

            @Override
            public void onFail(Call call, Exception e) {

            }
        });
    }


}

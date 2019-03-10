package com.example.weven.bankapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.weven.bankapp.Bean.FeedbackInfo;
import com.example.weven.bankapp.Bean.Url;
import com.example.weven.bankapp.R;
import com.example.weven.bankapp.util.HttpUtil;
import com.example.weven.bankapp.util.TextUtil;
import com.example.weven.bankapp.util.ToastUtil;
import com.example.weven.bankapp.util.okhttp.callback.ObjectCallBack;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class RegisterActivity extends BaseActivity {

    EditText et_aco, et_pwd, et_idcard, et_nickname;
    Button bt_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_aco = (EditText) findViewById(R.id.et_aco);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_idcard = (EditText) findViewById(R.id.et_idcard);
        et_nickname = (EditText) findViewById(R.id.et_nickname);
        bt_submit = (Button) findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtil.isValidate(et_aco.getText()) && TextUtil.isValidate(et_pwd.getText()) && TextUtil.isValidate(et_idcard.getText().toString()) && TextUtil.isValidate(et_nickname.getText().toString())){
                    postToRegister();
                }else {
                    ToastUtil.showBottomToast("请填写完整资料");
                }
            }
        });
    }

    public void postToRegister(){
        Map<String, String> params = new HashMap<>();
        params.put("account", et_aco.getText().toString());
        params.put("password", et_pwd.getText().toString());
        params.put("nickname", et_nickname.getText().toString());
        params.put("idcard", et_idcard.getText().toString());
        HttpUtil.postResponse(Url.register, params, this, new ObjectCallBack<FeedbackInfo>(FeedbackInfo.class) {
            @Override
            public void onSuccess(FeedbackInfo response) {
                if (response == null){
                    ToastUtil.showBottomToast(R.string.load_failure);
                }else {
                    if (response.isSuccess()){
                        ToastUtil.showBottomToast(response.getMessage());
                        finish();
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
}

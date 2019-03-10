package com.example.weven.bankapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.weven.bankapp.Bean.FeedbackInfo;
import com.example.weven.bankapp.Bean.Url;
import com.example.weven.bankapp.R;
import com.example.weven.bankapp.util.HttpUtil;
import com.example.weven.bankapp.util.ToastUtil;
import com.example.weven.bankapp.util.okhttp.callback.ObjectCallBack;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class FeedbackActivity extends BaseActivity {

    Button bt_commit_feedBackAcy;
    EditText et_content_feedBackAcy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        bt_commit_feedBackAcy = (Button) findViewById(R.id.bt_commit_feedBackAcy);
        et_content_feedBackAcy = (EditText) findViewById(R.id.et_content_feedBackAcy);
        et_content_feedBackAcy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.equals("")){

                }else {
                    bt_commit_feedBackAcy.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        bt_commit_feedBackAcy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> params = new HashMap<>();

                if( BaseApplication.getToken() == null){
                    params.put("token", "");
                    params.put("user", "");
                }else {
                    params.put("token", BaseApplication.getToken().toString());
                    params.put("user", BaseApplication.getUserId());
                }

                params.put("content", et_content_feedBackAcy.getText().toString());
                HttpUtil.postResponse(Url.feedback, params, this, new ObjectCallBack<FeedbackInfo>(FeedbackInfo.class) {
                    @Override
                    public void onSuccess(FeedbackInfo response) {
                        if (response.isSuccess()){

                            ToastUtil.showBottomToast(response.getMessage());
                            bt_commit_feedBackAcy.setClickable(false);
                        }else {
                            ToastUtil.showBottomToast(response.getMessage());
                        }
                    }

                    @Override
                    public void onFail(Call call, Exception e) {
                        ToastUtil.showBottomToast(R.string.upload_failure);
                    }
                });
            }
        });
    }
}

package com.example.weven.bankapp.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.weven.bankapp.Bean.FeedbackInfo;
import com.example.weven.bankapp.Bean.PersonInfo;
import com.example.weven.bankapp.Bean.Url;
import com.example.weven.bankapp.R;
import com.example.weven.bankapp.View.CommonToolBar;
import com.example.weven.bankapp.util.HttpUtil;
import com.example.weven.bankapp.util.TextUtil;
import com.example.weven.bankapp.util.ToastUtil;
import com.example.weven.bankapp.util.okhttp.callback.ObjectCallBack;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class PersonInfoActivity extends Activity {

    CommonToolBar rl_commonToolBar;
    boolean isEdit = true;
    EditText et_content2, et_content3, et_content4, et_content5, et_content6;
    int isMan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        rl_commonToolBar = (CommonToolBar) findViewById(R.id.rl_commonToolBar);
        et_content2 = (EditText) findViewById(R.id.content2);
        et_content3 = (EditText) findViewById(R.id.content3);
        et_content4 = (EditText) findViewById(R.id.content4);
        et_content5 = (EditText) findViewById(R.id.content5);
        et_content6 = (EditText) findViewById(R.id.content6);
        initData();
        rl_commonToolBar.setRightImgOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit){
                    isEdit = false;
                    rl_commonToolBar.setIv_rightImg(getResources().getDrawable(R.mipmap.finish));
                    et_content2.setEnabled(true);
                    et_content3.setEnabled(true);
                }else {
                    editPersonInfo();

                }
            }
        });
    }

    public void initData(){
        Map<String, String> map = new HashMap<>();
        map.put("token", BaseApplication.getToken());
        map.put("userid", BaseApplication.getUserId());
        HttpUtil.postResponse(Url.getInfo, map, this, new ObjectCallBack<PersonInfo>(PersonInfo.class) {
            @Override
            public void onSuccess(PersonInfo response) {
                if (response == null){
                    ToastUtil.showBottomToast(R.string.load_failure);
                }else {
                    if (response.isSuccess()){
                        et_content2.setText(response.getData().get(0).getNickname());
                        et_content3.setText(response.getData().get(0).getPhonenum());
                        if (response.getData().get(0).getSex().equals("1")){
                            et_content4.setText("男");
                            isMan = 1;
                        }else{
                            et_content4.setText("女");
                            isMan = 0;
                        }

                        et_content6.setText(response.getData().get(0).getCardnum());
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

    public void editPersonInfo(){
        if (TextUtil.isValidate(et_content2.getText().toString()) && TextUtil.isValidate(et_content3.getText().toString()) &&TextUtil.isValidate(et_content4.getText().toString()) &&TextUtil.isValidate(et_content5.getText().toString()) &&TextUtil.isValidate(et_content6.getText().toString())){

            Map<String, String> params = new HashMap<>();
            params.put("id", BaseApplication.getUserId());
            params.put("nickname", et_content2.getText().toString());
            params.put("phonenum", et_content3.getText().toString());
            if (et_content5.getText().equals("男")){
                isMan = 1;
            }else {
                isMan = 0;
            }
            params.put("sex", isMan + "");
            params.put("idcard", et_content6.getText().toString());
            HttpUtil.postResponse(Url.editInfo, params, this, new ObjectCallBack<FeedbackInfo>(FeedbackInfo.class) {
                @Override
                public void onSuccess(FeedbackInfo response) {
                    if (response == null){
                        ToastUtil.showBottomToast(R.string.load_failure);
                    }else {
                        if (response.isSuccess()){
                            isEdit = true;
                            rl_commonToolBar.setIv_rightImg(getResources().getDrawable(R.mipmap.edit));
                            ToastUtil.showBottomToast(response.getMessage());
                            et_content2.setEnabled(false);
                            et_content3.setEnabled(false);
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
        }else{
            ToastUtil.showBottomToast("请检查填写内容");
        }
    }
}

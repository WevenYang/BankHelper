package com.example.weven.bankapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.weven.bankapp.Adapter.AllMessagesAdapter;
import com.example.weven.bankapp.Bean.FeedbackInfo;
import com.example.weven.bankapp.Bean.GetAllMessagesResult;
import com.example.weven.bankapp.Bean.Url;
import com.example.weven.bankapp.R;
import com.example.weven.bankapp.util.HttpUtil;
import com.example.weven.bankapp.util.ToastUtil;
import com.example.weven.bankapp.util.okhttp.callback.ObjectCallBack;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Call;

public class MyMessageActivity extends BaseActivity {

    RecyclerView rv_allMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        rv_allMessage = (RecyclerView) findViewById(R.id.rv_allMessage);
        initData();
    }

    @Override
    protected void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getToken());
        params.put("id", BaseApplication.getUserId());
        HttpUtil.postResponse(Url.getMyMessage, params, this, new ObjectCallBack<GetAllMessagesResult>(GetAllMessagesResult.class) {
            @Override
            public void onSuccess(GetAllMessagesResult response) {
                if (response == null){
                    ToastUtil.showBottomToast(R.string.load_failure);
                }else {
                    ToastUtil.showBottomToast(response.getMessage());
                    if (response.isSuccess()){
                        AllMessagesAdapter adapter = new AllMessagesAdapter(MyMessageActivity.this, response.getData());
                        rv_allMessage.setLayoutManager(new LinearLayoutManager(MyMessageActivity.this));
                        rv_allMessage.setAdapter(adapter);
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

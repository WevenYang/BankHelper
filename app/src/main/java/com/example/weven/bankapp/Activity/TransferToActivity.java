package com.example.weven.bankapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.weven.bankapp.Bean.PeopleAccountInfo;
import com.example.weven.bankapp.Bean.Url;
import com.example.weven.bankapp.R;
import com.example.weven.bankapp.util.HttpUtil;
import com.example.weven.bankapp.util.IntentUtil;
import com.example.weven.bankapp.util.TextUtil;
import com.example.weven.bankapp.util.ToastUtil;
import com.example.weven.bankapp.util.okhttp.callback.ObjectCallBack;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class TransferToActivity extends BaseActivity {

    Button bt_transfer_next;
    EditText et_people_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_to);
        bt_transfer_next = (Button) findViewById(R.id.bt_transfer_next);
        et_people_account = (EditText) findViewById(R.id.et_people_account);
        et_people_account.addTextChangedListener(new TextWatcher() {
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
                bt_transfer_next.setEnabled(false);
                Map<String, String> params = new HashMap<>();
                params.put("account", et_people_account.getText().toString());
                HttpUtil.postResponse(Url.getPeopleAccount, params, this, new ObjectCallBack<PeopleAccountInfo>(PeopleAccountInfo.class) {
                    @Override
                    public void onSuccess(PeopleAccountInfo response) {
                        if (response == null){
                            ToastUtil.showBottomToast("该用户不存在");
                        }else {
                            if (response.isSuccess()){
                                ToastUtil.showBottomToast(response.getMessage());
                                Bundle b = new Bundle();
                                b.putString("account", response.getData().getUserId());
                                b.putString("nickname", response.getData().getNickName());
                                IntentUtil.startActivity(TransferToActivity.this, PeopleAccountActivity.class, b);
                                bt_transfer_next.setEnabled(true);
                            }else{
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
        });
    }
}

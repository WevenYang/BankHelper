package com.example.weven.bankapp.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.weven.bankapp.Bean.LoginInfo;
import com.example.weven.bankapp.Bean.Url;
import com.example.weven.bankapp.R;
import com.example.weven.bankapp.util.HttpUtil;
import com.example.weven.bankapp.util.IntentUtil;
import com.example.weven.bankapp.util.LogUtil;
import com.example.weven.bankapp.util.TextUtil;
import com.example.weven.bankapp.util.ToastUtil;
import com.example.weven.bankapp.util.okhttp.callback.ObjectCallBack;

import okhttp3.Call;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    Button login;
    FloatingActionButton register;
    EditText aco, pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.login);
        register = (FloatingActionButton) findViewById(R.id.register);
        aco = (EditText) findViewById(R.id.aco);
        pwd = (EditText) findViewById(R.id.pwd);
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtil.isValidate(aco.getText().toString()) && TextUtil.isValidate(pwd.getText().toString())){

                    Map<String, String> params = new HashMap<>();
                    params.put("account", aco.getText().toString());
                    params.put("password", pwd.getText().toString());
                    HttpUtil.postResponse(Url.login, params, this, new ObjectCallBack<LoginInfo>(LoginInfo.class) {
                        @Override
                        public void onSuccess(LoginInfo response) {
                            if (response == null){
                                ToastUtil.showBottomToast(R.string.load_failure);
                            }else {
                                if (response.isSuccess()){
                                    if (TextUtil.isValidate(response.getData().getUserId())){
                                        BaseApplication.setUserId(response.getData().getUserId() + "");
                                        BaseApplication.setToken(response.getData().getToken());
                                        BaseApplication.setPassword(pwd.getText().toString());
                                        BaseApplication.setPayPassword(response.getData().getPaypwd());
                                        ToastUtil.showBottomToast(response.getMessage());
                                        LogUtil.i("token and id", BaseApplication.getToken().toString() + "  id is " + BaseApplication.getUserId());
                                        Bundle b = new Bundle();
                                        b.putString("name", response.getData().getName());
                                        b.putString("icon", response.getData().getIcon());
                                        IntentUtil.startActivity(LoginActivity.this, MainActivity.class, b);
                                        finish();
                                    }else {
                                        ToastUtil.showBottomToast("账号或者密码有误");
                                    }

                                }else {
                                    ToastUtil.showBottomToast(R.string.load_failure);
                                }
                            }
                        }

                        @Override
                        public void onFail(Call call, Exception e) {
                            ToastUtil.showBottomToast(R.string.load_failure);
                        }
                    });
                }else {

                    ToastUtil.showBottomToast("账户密码不能为空");

                }

            }
        });
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtil.startActivity(LoginActivity.this, RegisterActivity.class);
            }
        });
    }

}


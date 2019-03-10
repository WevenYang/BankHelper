package com.example.weven.bankapp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.weven.bankapp.R;

public class AboutActivity extends BaseActivity {

    TextView tv_versionCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
//        tv_versionCode = (TextView)findViewById(R.id.tv_versionCode_aboutPuHuiAcy);
    }

    @Override
    protected void initData() {
        super.initData();
//        tv_versionCode.setText("版本："+ PackageUtil.getVersionName());
    }
}

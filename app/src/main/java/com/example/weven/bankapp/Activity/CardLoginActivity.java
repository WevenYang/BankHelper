package com.example.weven.bankapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.weven.bankapp.R;

public class CardLoginActivity extends BaseActivity {

    TextView tv_change;
    Button btn_sure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_login);
        tv_change = (TextView) findViewById(R.id.tv_change);
        btn_sure = (Button) findViewById(R.id.btn_sure);
    }
}

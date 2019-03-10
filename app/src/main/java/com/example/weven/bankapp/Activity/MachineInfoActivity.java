package com.example.weven.bankapp.Activity;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.weven.bankapp.R;
import com.example.weven.bankapp.util.BuildHelper;
import com.example.weven.bankapp.util.MyLog;

public class MachineInfoActivity extends BaseActivity {

    TextView tv_model, tv_band;
    boolean isBand = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_info);
        tv_band = (TextView) findViewById(R.id.tv_band);
        tv_model = (TextView) findViewById(R.id.tv_model);
        if (isBand == false){
            tv_band.setText("绑定");
            tv_band.setTextColor(getResources().getColor(R.color.middleBlue));
            tv_band.setClickable(true);
            isBand = true;
        }else {
            tv_band.setText("已绑定");
            tv_band.setTextColor(getResources().getColor(R.color.grey));
            tv_band.setClickable(false);
            isBand = false;
        }
        tv_band.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBand == false){
                    tv_band.setText("绑定");
                    tv_band.setTextColor(getResources().getColor(R.color.middleBlue));
                    isBand = true;
                }else {
                    tv_band.setText("已绑定");
                    tv_band.setTextColor(getResources().getColor(R.color.grey));
                    tv_band.setClickable(false);
                    isBand = false;
                }
            }
        });
    }
}

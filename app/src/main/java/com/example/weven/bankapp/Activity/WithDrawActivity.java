package com.example.weven.bankapp.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.weven.bankapp.Bean.AddBankCard;
import com.example.weven.bankapp.Bean.BandCardInfo;
import com.example.weven.bankapp.Bean.FeedbackInfo;
import com.example.weven.bankapp.Bean.GetBankCardInfo;
import com.example.weven.bankapp.Bean.RechargeSuccessful;
import com.example.weven.bankapp.Bean.Url;
import com.example.weven.bankapp.R;
import com.example.weven.bankapp.View.CommonToolBar;
import com.example.weven.bankapp.View.EnterPayPassWordPpw;
import com.example.weven.bankapp.View.PassWordView;
import com.example.weven.bankapp.util.HttpUtil;
import com.example.weven.bankapp.util.MD5Util;
import com.example.weven.bankapp.util.TextUtil;
import com.example.weven.bankapp.util.ToastUtil;
import com.example.weven.bankapp.util.okhttp.callback.ObjectCallBack;
import com.zhy.android.percent.support.PercentLinearLayout;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class WithDrawActivity extends BaseActivity {

    CommonToolBar commonToolBar;
    Button bt_transfer_next;
    EditText et_money;
    PercentRelativeLayout rl_card1;
    PopupWindow typeSelectPopup;
    TextView tv_sim_card, tv_hint;
    Bundle b;
    EnterPayPassWordPpw enterPayPassWordPpw;
    PercentLinearLayout pll_parent;
    private ListView mTypeCard;
    private List<String> card;
    String currentDeposit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        b = getIntent().getBundleExtra("Bundle");
        commonToolBar = (CommonToolBar) findViewById(R.id.cb_title_announcement);
        et_money = (EditText) findViewById(R.id.et_money);
        bt_transfer_next = (Button) findViewById(R.id.bt_transfer_next);
        rl_card1 = (PercentRelativeLayout) findViewById(R.id.rl_card1);
        tv_sim_card = (TextView) findViewById(R.id.tv_sim_card);
        tv_hint = (TextView) findViewById(R.id.tv_hint);
        pll_parent = (PercentLinearLayout) findViewById(R.id.pll_parent);
        commonToolBar.setMiddleTitleText("提现");
        tv_hint.setText("提现金额");
        EventBus.getDefault().register(this);
        initData();
        initCurrentDeposit();
        et_money.addTextChangedListener(new TextWatcher() {
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
        rl_card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSelectCard();
                if (typeSelectPopup != null && !typeSelectPopup.isShowing()){
                    typeSelectPopup.showAsDropDown(tv_sim_card, 0, 10);
                }
            }
        });
    }

    //输入支付密码进行提现
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
                    if (MD5Util.GetMD5Code(passWord).equals(BaseApplication.getPayPassword())){
                        rechargeMyAccount();

                    }else {
                        enterPayPassWordPpw.completeLoading(false);
                        enterPayPassWordPpw.setToastMessage("提现失败");
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

    public void initCurrentDeposit(){
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getToken());
        params.put("id", BaseApplication.getUserId());
        HttpUtil.postResponse(Url.getCurrentDeposit, params, this, new ObjectCallBack<FeedbackInfo>(FeedbackInfo.class) {
            @Override
            public void onSuccess(FeedbackInfo response) {
                if (response == null){
                    ToastUtil.showBottomToast(R.string.load_failure);
                }else {
                    if (response.isSuccess()){
                        currentDeposit = response.getData().toString();
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

    public void initData(){
        Map<String, String> params = new HashMap<>();
        params.put("id", BaseApplication.getUserId());
        params.put("token", BaseApplication.getToken());
        HttpUtil.postResponse(Url.getPersonCard, params, this, new ObjectCallBack<GetBankCardInfo>(GetBankCardInfo.class) {
            @Override
            public void onSuccess(GetBankCardInfo response) {
                if (response == null){
                    ToastUtil.showBottomToast(R.string.load_failure);
                }else {
                    if (response.isSuccess()){
                        card = new ArrayList<>();
                        if (card.size() != 0){
                            card.clear();
                        }
                        for (int i = 0; i < response.getData().size(); i++){
                            BandCardInfo info = new BandCardInfo(response.getData().get(i).getId(), R.mipmap.ic_launcher, "小不懂银行", response.getData().get(i).getCardnum());
                            String content = "建设银行(" + info.getNum().substring(12) + ")";
                            card.add(content);
                        }
                        tv_sim_card.setText("建设银行(" + response.getData().get(0).getCardnum().substring(12) + ")");
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

    public void initSelectCard(){
        mTypeCard = new ListView(this);
        mTypeCard.setAdapter(new ArrayAdapter<String>(this, R.layout.ppw_text_item, card));
        mTypeCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value = card.get(position);
                tv_sim_card.setText(value);
//                SharedPreferencesUtil.writeSharedPreferences("PackagePrice", "cardType", position + "");
                typeSelectPopup.dismiss();
            }
        });
        typeSelectPopup = new PopupWindow(mTypeCard, tv_sim_card.getWidth(), ActionBar.LayoutParams.WRAP_CONTENT, true);
        //取的popup窗口背景图片
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.sel_grade);
        typeSelectPopup.setBackgroundDrawable(drawable);
        typeSelectPopup.setFocusable(true);
        typeSelectPopup.setOutsideTouchable(true);
        typeSelectPopup.setOnDismissListener(new PopupWindow.OnDismissListener(){
            @Override
            public void onDismiss() {
                typeSelectPopup.dismiss();
            }
        });
    }

    public void rechargeMyAccount(){
        if (Integer.valueOf(currentDeposit) < Integer.valueOf(et_money.getText().toString())){
            ToastUtil.showBottomToast("不可超出活期余额");
        }else {
            Map<String, String> params = new HashMap<>();
            params.put("token", BaseApplication.getToken());
            params.put("id", BaseApplication.getCardId());
            params.put("num", et_money.getText().toString());
            HttpUtil.postResponse(Url.withDraw, params, this, new ObjectCallBack<FeedbackInfo>(FeedbackInfo.class) {
                @Override
                public void onSuccess(FeedbackInfo response) {
                    if (response == null){
                        ToastUtil.showBottomToast(R.string.load_failure);
                    }else {
                            if (response.isSuccess()){
                                enterPayPassWordPpw.completeLoading(true);
                                enterPayPassWordPpw.setToastMessage("提现成功");
                                EventBus.getDefault().post(new RechargeSuccessful());
                                finish();
                            }else {
                                enterPayPassWordPpw.completeLoading(false);
                                enterPayPassWordPpw.setToastMessage("提现失败("+ response.getMessage() + ")");
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshActivity(AddBankCard event){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

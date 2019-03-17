package com.example.weven.bankapp.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.weven.bankapp.Adapter.CommonRecyclerAdapter;
import com.example.weven.bankapp.Adapter.ViewHolderR;
import com.example.weven.bankapp.Bean.AddBankCard;
import com.example.weven.bankapp.Bean.BandCardInfo;
import com.example.weven.bankapp.Bean.FeedbackInfo;
import com.example.weven.bankapp.Bean.GetBankCardInfo;
import com.example.weven.bankapp.Bean.Url;
import com.example.weven.bankapp.R;
import com.example.weven.bankapp.View.CommonToolBar;
import com.example.weven.bankapp.View.DeleteAccountDialog;
import com.example.weven.bankapp.View.ModifyBankCardDig;
import com.example.weven.bankapp.util.HttpUtil;
import com.example.weven.bankapp.util.IntentUtil;
import com.example.weven.bankapp.util.MD5Util;
import com.example.weven.bankapp.util.MyToast;
import com.example.weven.bankapp.util.TextUtil;
import com.example.weven.bankapp.util.ToastUtil;
import com.example.weven.bankapp.util.okhttp.callback.ObjectCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class ChooseCardActivity extends BaseActivity {
    RecyclerView rv_card_list;
    List<BandCardInfo> itemCardList;
    DeleteAccountDialog unbindDialog;
    CommonToolBar toolbar;
    ModifyBankCardDig dig;
    RadioButton rb_item;
    String id = "";
    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_card);
        bundle = getIntent().getBundleExtra("Bundle");
        toolbar = (CommonToolBar) findViewById(R.id.rl_commonToolBar);
        rb_item = (RadioButton) findViewById(R.id.rb_item);
        itemCardList = new ArrayList<>();
        rv_card_list = (RecyclerView) findViewById(R.id.rv_card_list);
        initData();
        toolbar.setRightImgOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtil.isValidate(BaseApplication.getCardId())){

                    Bundle b = new Bundle();
                    b.putString("name", bundle.get("name").toString());
                    b.putString("icon", bundle.get("icon").toString());
                    IntentUtil.startActivity(ChooseCardActivity.this, MainActivity.class, b);
                }else {
                    ToastUtil.showBottomToast("请选择一张银行卡");
                }
            }
        });
    }

    @Override
    protected void initData() {
        initCardList();
    }

    public void initCardList(){
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
                        if (itemCardList.size() != 0){
                            itemCardList.clear();
                        }
                        for (int i = 0; i < response.getData().size(); i++){
                            BandCardInfo info = new BandCardInfo(response.getData().get(i).getId(), R.mipmap.ic_launcher, "小不懂银行", response.getData().get(i).getCardnum());
                            itemCardList.add(info);
                        }
                        rv_card_list.setLayoutManager(new LinearLayoutManager(ChooseCardActivity.this));
                        rv_card_list.setAdapter(new CommonRecyclerAdapter<BandCardInfo>(ChooseCardActivity.this, R.layout.item_card, itemCardList) {
                            @Override
                            public void convert(final ViewHolderR holder, final BandCardInfo o) {
                                String card = o.getNum().substring(12);
                                TextView cardNum = holder.getView(R.id.tv_num);
                                final RadioButton rb = holder.getView(R.id.rb_item);
                                rb.setVisibility(View.VISIBLE);
                                cardNum.setText("**** **** **** " + card);
                                rb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                        if (TextUtil.isValidate(BaseApplication.getCardId())){
                                            rb.setChecked(false);
                                        }else {
                                            BaseApplication.setCardId(o.getId());
                                        }
                                    }
                                });
                            }
                        });
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

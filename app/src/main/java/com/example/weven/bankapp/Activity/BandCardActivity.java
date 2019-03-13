package com.example.weven.bankapp.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.example.weven.bankapp.util.MD5Util;
import com.example.weven.bankapp.util.MyToast;
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

public class BandCardActivity extends BaseActivity {

    RecyclerView rv_card_list;
    List<BandCardInfo> itemCardList;
    DeleteAccountDialog unbindDialog;
    CommonToolBar toolbar;
    ModifyBankCardDig dig;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_card);
        toolbar = (CommonToolBar) findViewById(R.id.rl_commonToolBar);
        itemCardList = new ArrayList<>();
        rv_card_list = (RecyclerView) findViewById(R.id.rv_card_list);
        EventBus.getDefault().register(this);
        initData();
        toolbar.setRightImgOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dig = new ModifyBankCardDig(BandCardActivity.this, "添加银行卡");
                dig.show();
                dig.setOnConfirmClickListener(new ModifyBankCardDig.OnConfirmClickListener() {
                    @Override
                    public void onConfirmClick(String originalPassWord) {
                        addBankCard(originalPassWord);
                    }
                });
            }
        });
    }

    //添加银行卡
    public void addBankCard(String num){
        Map<String, String> params = new HashMap<>();
        params.put("id", BaseApplication.getUserId());
        params.put("token", BaseApplication.getToken());
        params.put("num", num);
        HttpUtil.postResponse(Url.addPersonCard, params, this, new ObjectCallBack<FeedbackInfo>(FeedbackInfo.class) {
            @Override
            public void onSuccess(FeedbackInfo response) {
                if (response == null){
                    ToastUtil.showBottomToast(R.string.load_failure);
                }else {
                    if (response.isSuccess()){
                        EventBus.getDefault().post(new AddBankCard());
                        ToastUtil.showBottomToast("添加成功");
                        dig.dismiss();
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
                        rv_card_list.setLayoutManager(new LinearLayoutManager(BandCardActivity.this));
                        rv_card_list.setAdapter(new CommonRecyclerAdapter<BandCardInfo>(BandCardActivity.this, R.layout.item_card, itemCardList) {
                            @Override
                            public void convert(final ViewHolderR holder, final BandCardInfo o) {
                                String card = o.getNum().substring(12);
                                TextView cardNum = holder.getView(R.id.tv_num);
                                cardNum.setText("**** **** **** " + card);
                                holder.setOnLongClickListener(R.id.pll_card_item, new View.OnLongClickListener() {
                                    @Override
                                    public boolean onLongClick(View view) {
//                        TextView bankName = holder.getView(R.id.tv_bank);
//                        TextView bankNum = holder.getView(R.id.tv_num);
//                        ImageView iv_icon = holder.getView(R.id.iv_icon);
                                        showUnbindAccountDialog(o.getId());
                                        return true;
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

    //弹出解除绑定对话框
    private void showUnbindAccountDialog(final String userId){
        unbindDialog = new DeleteAccountDialog(this, R.style.BindAccountDialogSty);
        unbindDialog.setOnUnBindClickListener(new DeleteAccountDialog.OnUnBindClickListener() {
            @Override
            public void onUnBindClick(String passWord) {
//                    studentAccountPassWord = passWord;
                if (MD5Util.GetMD5Code(passWord).equals(BaseApplication.getPayPassword())){
                    deleteAccount(userId);
                }else {
                    MyToast.showMyToast(BandCardActivity.this, "密码错误");
                    unbindDialog.dismiss();
                }

            }
        });
        unbindDialog.show();
//        }
    }

    private void deleteAccount(String id){
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("token", BaseApplication.getToken());
        HttpUtil.postResponse(Url.deletePersonCard, params, this, new ObjectCallBack<FeedbackInfo>(FeedbackInfo.class) {
            @Override
            public void onSuccess(FeedbackInfo response) {
                if (response == null){
                    ToastUtil.showBottomToast(R.string.load_failure);
                }else {
                    if (response.isSuccess()){

                        EventBus.getDefault().post(new AddBankCard());
                        unbindDialog.dismiss();
                        MyToast.showMyToast(BandCardActivity.this, response.getMessage());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshActivity(AddBankCard event){
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(BandCardActivity.this);
    }
}

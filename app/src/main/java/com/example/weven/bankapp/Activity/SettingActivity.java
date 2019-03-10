package com.example.weven.bankapp.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.weven.bankapp.Adapter.CommonRecyclerAdapter;
import com.example.weven.bankapp.Adapter.ViewHolderR;
import com.example.weven.bankapp.Bean.LeftHorizontalBarItemInfo;
import com.example.weven.bankapp.R;
import com.example.weven.bankapp.View.RecyclerViewLinearLayoutDivider;
import com.example.weven.bankapp.View.TextViewPlus;
import com.example.weven.bankapp.util.IntentUtil;
import com.example.weven.bankapp.util.MyToast;

import java.util.ArrayList;

public class SettingActivity extends BaseActivity {

    RecyclerView rv_setting;
    ArrayList itemInfoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        rv_setting = (RecyclerView) findViewById(R.id.rv_setting);
        initItemInfoList();
        initData();
    }

    @Override
    protected void initData() {
        rv_setting.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_setting.addItemDecoration(new RecyclerViewLinearLayoutDivider(RecyclerViewLinearLayoutDivider.VERTICAL_LIST, getResources().getDrawable(R.drawable.sp_divider_horizontal_rv)));
        rv_setting.setAdapter(new CommonRecyclerAdapter<LeftHorizontalBarItemInfo>(this, R.layout.cv_horizontal_bar_settings_acy, itemInfoList) {

            @Override
            public void convert(final ViewHolderR holder, LeftHorizontalBarItemInfo horizontalBarInfo) {
                holder.setOnClickListener(R.id.prl_mine_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (holder.getAdapterPosition()) {
                            case 0:
                                MyToast.showMyToast(SettingActivity.this, "请联系"+getString(R.string.connection));
                                break;
                            case 1:
                                    IntentUtil.startActivity(SettingActivity.this, FeedbackActivity.class);
                                break;
                            case 2:
                                    IntentUtil.startActivity(SettingActivity.this, AboutActivity.class);
                                break;
                            default:
                                break;

                        }
                    }
                });
                TextViewPlus textViewPlus = holder.getView(R.id.tvp_left_horizontalBarCvSettingsAcy);
                textViewPlus.setCompoundImg(TextViewPlus.LEFT_IMG, horizontalBarInfo.getLeftImgResourceId(), 0.035f, 0.035f, "ScreenHeight");
                textViewPlus.setText(horizontalBarInfo.getLeftText());
            }
        });
    }

    private void initItemInfoList() {
        itemInfoList = new ArrayList<>();
        itemInfoList.add(new LeftHorizontalBarItemInfo("联系我们", R.mipmap.nav_icon_connect));
        itemInfoList.add(new LeftHorizontalBarItemInfo("意见反馈", R.mipmap.nav_icon_recall));
        itemInfoList.add(new LeftHorizontalBarItemInfo("关于", R.mipmap.nav_icon_about));
    }
}

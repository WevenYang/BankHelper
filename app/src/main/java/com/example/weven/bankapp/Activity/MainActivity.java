package com.example.weven.bankapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weven.bankapp.R;
import com.example.weven.bankapp.View.BannerLayout;
import com.example.weven.bankapp.util.IntentUtil;
import com.example.weven.bankapp.util.TextUtil;
import com.example.weven.bankapp.util.ToastUtil;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    BannerLayout bl_main;
    Toolbar toolbar;
    DrawerLayout drawer;
    RecyclerView recycler;
    TabLayout mTablayout;
    ViewPager mViewPager;
    Button bt_query;
    PercentRelativeLayout prl_transfer;
    PercentRelativeLayout prl_exchange1;
    PercentRelativeLayout prl_exchange2;
    PercentRelativeLayout prl_item4;
    PercentRelativeLayout prl_item5;
    Bundle bundle;
    TextView account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bundle = getIntent().getBundleExtra("Bundle");
        initAccount();
        initBase();     //初始化基本控件，包括标题栏和侧栏
//        initTab();
    }

    public void initAccount(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View HeaderView = navigationView.inflateHeaderView(R.layout.header_layout);
        account = (TextView) HeaderView.findViewById(R.id.account);
        ImageView image = (ImageView) HeaderView.findViewById(R.id.head_iv);
        if (TextUtil.isValidate(BaseApplication.getUserId())){
            account.setText(bundle.get("name").toString());
        }else {
            account.setText("未登陆");
        }

    }

    public void initBase(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        bl_main = (BannerLayout) findViewById(R.id.bl_main);
        prl_transfer = (PercentRelativeLayout) findViewById(R.id.prl_transfer);
        prl_exchange1 = (PercentRelativeLayout) findViewById(R.id.prl_exchange1);
        prl_exchange2 = (PercentRelativeLayout) findViewById(R.id.prl_exchange2);
        prl_item4 = (PercentRelativeLayout) findViewById(R.id.prl_item4);
        prl_item5 = (PercentRelativeLayout) findViewById(R.id.prl_item5);
        bt_query = (Button) findViewById(R.id.bt_query);

        prl_transfer.setOnClickListener(this);
        prl_exchange1.setOnClickListener(this);
        prl_exchange2.setOnClickListener(this);
        prl_item4.setOnClickListener(this);
        prl_item5.setOnClickListener(this);
        bt_query.setOnClickListener(this);
        final List<String> urls = new ArrayList<>();
        urls.add("http://img.zjol.com.cn/pic/0/05/90/83/5908394_767160.jpg");
        urls.add("http://i1.sinaimg.cn/cj/U5902P31T722D698F20320DT20110621092512_sw608.jpg");
        urls.add("http://img.mp.itc.cn/upload/20161221/625814d56bbf4a75b9ea9e837af70559_th.jpg");
        bl_main.setViewUrls(urls);
        toolbar.setTitle("首页");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name);
        toggle.syncState();
        drawer.addDrawerListener(toggle);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_setting:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case R.id.action_about:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:
                if (TextUtil.isValidate(BaseApplication.getUserId())){
                    IntentUtil.startActivity(this, PersonInfoActivity.class);
                }else {
                    ToastUtil.showBottomToast("请先登陆");
                    IntentUtil.startActivity(this, LoginActivity.class);

                }

                break;
            case R.id.nav_account:
                if (TextUtil.isValidate(BaseApplication.getUserId())){
                    IntentUtil.startActivity(this, MyAccountActivity.class);

                }else {
                    ToastUtil.showBottomToast("请先登陆");
                    IntentUtil.startActivity(this, LoginActivity.class);
                }

                break;
            case R.id.nav_message:
                if (TextUtil.isValidate(BaseApplication.getUserId())){
                    IntentUtil.startActivity(this, MyMessageActivity.class);
                }else {
                    ToastUtil.showBottomToast("请先登陆");
                    IntentUtil.startActivity(this, LoginActivity.class);
                }

                break;
            case R.id.nav_setting:
                IntentUtil.startActivity(this, SettingActivity.class);
                break;
            case R.id.nav_security:
                if (TextUtil.isValidate(BaseApplication.getUserId())){
                    IntentUtil.startActivity(this, SecurityActivity.class);
                }else {
                    ToastUtil.showBottomToast("请先登陆");
                    IntentUtil.startActivity(this, LoginActivity.class);
                }

                break;
            case R.id.nav_loginout:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        BaseApplication.setPayPassword("");
                        BaseApplication.setPassword("");
                        BaseApplication.setToken("");
                        BaseApplication.setUserId("");
                        BaseApplication.setCardId("");
                        account.setText("未登陆");
                        IntentUtil.startActivity(MainActivity.this, LoginActivity.class);
                        finish();
                    }
                });
                builder.setMessage("确定退出该账号吗");
                builder.show();
                break;
            default:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_query:
                if (TextUtil.isValidate(BaseApplication.getUserId())){
                    IntentUtil.startActivity(this, QueryBalanceActivity.class);
                }else {
                    ToastUtil.showBottomToast("请先登陆");
                    IntentUtil.startActivity(this, LoginActivity.class);
                }

                break;
            case R.id.prl_transfer:
                if (TextUtil.isValidate(BaseApplication.getUserId())){
                    IntentUtil.startActivity(MainActivity.this, TransferToActivity.class);
                }else {
                    ToastUtil.showBottomToast("请先登陆");
                    IntentUtil.startActivity(this, LoginActivity.class);
                }

                break;
            case R.id.prl_exchange1:
                if (TextUtil.isValidate(BaseApplication.getUserId())){
                    IntentUtil.startActivity(MainActivity.this, Exchange1.class);
                }else {
                    ToastUtil.showBottomToast("请先登陆");
                    IntentUtil.startActivity(this, LoginActivity.class);
                }

                break;
            case R.id.prl_exchange2:
                if (TextUtil.isValidate(BaseApplication.getUserId())){
                    IntentUtil.startActivity(MainActivity.this, Exchange2.class);
                }else {
                    ToastUtil.showBottomToast("请先登陆");
                    IntentUtil.startActivity(this, LoginActivity.class);
                }

                break;
            case R.id.prl_item4:
                IntentUtil.startActivity(MainActivity.this, SettingActivity.class);
                break;
            case R.id.prl_item5:
                IntentUtil.startActivity(MainActivity.this, AboutActivity.class);
                break;
            default:
                break;
        }
    }
}

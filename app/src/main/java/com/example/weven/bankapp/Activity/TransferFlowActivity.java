package com.example.weven.bankapp.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.weven.bankapp.Adapter.MyAdapter;
import com.example.weven.bankapp.R;
import com.example.weven.bankapp.View.DividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;

public class TransferFlowActivity extends BaseActivity {

    private RecyclerView Rv;
    private ArrayList<HashMap<String,Object>> listItem;
    private MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_flow);
        // 初始化显示的数据
        initData();

        // 绑定数据到RecyclerView
        initView();
    }
    // 初始化显示的数据
    public void initData(){
        listItem = new ArrayList<HashMap<String, Object>>();/*在数组中存放数据*/

        HashMap<String, Object> map1 = new HashMap<String, Object>();
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        HashMap<String, Object> map4 = new HashMap<String, Object>();
        HashMap<String, Object> map5 = new HashMap<String, Object>();
        HashMap<String, Object> map6 = new HashMap<String, Object>();

        map1.put("ItemTitle", "申请已提交");
        map1.put("ItemText", "今天");
        listItem.add(map1);

        map2.put("ItemTitle", "等待审核");
        map2.put("ItemText", "");
        listItem.add(map2);

        map3.put("ItemTitle", "成功转入");
        map3.put("ItemText", "");
        listItem.add(map3);

    }

    // 绑定数据到RecyclerView
    public void initView(){
        Rv = (RecyclerView) findViewById(R.id.my_recycler_view);
        //使用线性布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        Rv.setLayoutManager(layoutManager);
        Rv.setHasFixedSize(true);

        //用自定义分割线类设置分割线
        Rv.addItemDecoration(new DividerItemDecoration(this));

        //为ListView绑定适配器
        myAdapter = new MyAdapter(this,listItem);
        Rv.setAdapter(myAdapter);
    }

}

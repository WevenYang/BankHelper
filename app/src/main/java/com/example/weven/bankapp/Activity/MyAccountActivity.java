package com.example.weven.bankapp.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.weven.bankapp.Bean.FeedbackInfo;
import com.example.weven.bankapp.Bean.MyAccountInfo;
import com.example.weven.bankapp.Bean.Url;
import com.example.weven.bankapp.R;
import com.example.weven.bankapp.util.HttpUtil;
import com.example.weven.bankapp.util.ToastUtil;
import com.example.weven.bankapp.util.okhttp.callback.ObjectCallBack;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Call;

public class MyAccountActivity extends BaseActivity {

    TextView content2, content3, content4;
    PieChart bc_piechart;
    //饼图数据
    private TreeMap<String, Integer> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        content2 = (TextView) findViewById(R.id.content2);
        content3 = (TextView) findViewById(R.id.content3);
        content4 = (TextView) findViewById(R.id.content4);
        bc_piechart = (PieChart) findViewById(R.id.bc_piechart_reportAcy);
        initData();
    }

    @Override
    protected void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("token", BaseApplication.getToken());
        params.put("id", BaseApplication.getUserId());
        HttpUtil.postResponse(Url.getMyAccount, params, this, new ObjectCallBack<MyAccountInfo>(MyAccountInfo.class) {
            @Override
            public void onSuccess(MyAccountInfo response) {
                if (response == null){
                    ToastUtil.showBottomToast(R.string.load_failure);
                }else {
                    ToastUtil.showBottomToast(response.getMessage());
                    if (response.isSuccess()){
                        content2.setText(response.getData().getAccount());
                        content3.setText(response.getData().getCurrent_deposit());
                        content4.setText(response.getData().getFixed_deposit());
                        int a = Integer.valueOf(response.getData().getCurrent_deposit());
//                        int b = Integer.valueOf(response.getData().getAccount());
//                        double current = a / b;
                        int c = Integer.valueOf(response.getData().getFixed_deposit());
//                        int d = Integer.valueOf(response.getData().getAccount());
//                        double fixed = c / d;
                        data = new TreeMap<>();
                        data.put("活期", a);
                        data.put("定期", c);
                        initPieChart();
                        setPieData(data);
                    }
                }
            }

            @Override
            public void onFail(Call call, Exception e) {
                ToastUtil.showBottomToast(R.string.load_failure);
            }
        });

    }

    private void initPieChart(){
        // 显示百分比
        bc_piechart.setUsePercentValues(true);
        // 设置偏移量
        bc_piechart.setExtraOffsets(5, 10, 5, 5);
        // 数据描述
        bc_piechart.setDescription("");
        // 设置滑动减速摩擦系数
        bc_piechart.setDragDecelerationFrictionCoef(0.95f);
        /*
            设置饼图中心是否是空心的
            true 中间是空心的，环形图
            false 中间是实心的 饼图
         */
        bc_piechart.setDrawHoleEnabled(false);
        /*
            设置中间空心圆孔的颜色是否透明
            true 透明的
            false 非透明的
         */
        bc_piechart.setHoleColorTransparent(true);
        // 设置环形图和中间空心圆之间的圆环的颜色
        bc_piechart.setTransparentCircleColor(Color.WHITE);
        // 设置环形图和中间空心圆之间的圆环的透明度
        bc_piechart.setTransparentCircleAlpha(110);

        // 设置圆孔半径
        bc_piechart.setHoleRadius(58f);
        // 设置空心圆的半径
        bc_piechart.setTransparentCircleRadius(61f);
        // 设置是否显示中间的文字
        bc_piechart.setDrawCenterText(true);
        // 设置旋转角度
        bc_piechart.setRotationAngle(0);
        // enable rotation of the chart by touch
        bc_piechart.setRotationEnabled(true);
//        bc_piechart.setHighlightPerTapEnabled(false);
        // 设置动画
        bc_piechart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // 设置显示的比例
        Legend l = bc_piechart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    public void setPieData(TreeMap<String, Integer> data){
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        int i = 0;
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            // entry的输出结果如key0=value0等
            Map.Entry entry = (Map.Entry) it.next();
            String key = (String) entry.getKey();
            Integer value = (Integer) entry.getValue();
            xVals.add(key);
            yVals1.add(new Entry(value, i++));
        }
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        // 设置饼图区块之间的距离
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
        // 添加颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.lightGreen));
        colors.add(getResources().getColor(R.color.lightOrange));
        colors.add(getResources().getColor(R.color.pink));
//        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        PieData data1 = new PieData(xVals, dataSet);
        data1.setValueFormatter(new PercentFormatter());
        data1.setValueTextSize(10f);
        data1.setValueTextColor(Color.BLACK);
        bc_piechart.setData(data1);
    }
}

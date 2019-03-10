package com.example.weven.bankapp.util;

import android.util.TypedValue;

import com.example.weven.bankapp.Activity.BaseApplication;

/**
 * Created by Administrator on 2016/6/14.
 */
public class DensityUtil {
    public static int dip2px(float dpValue) {
        final float scale = BaseApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static int px2dip(float pxValue) {
        final float scale = BaseApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * sp转px
     *
     * @param spVal
     * @return
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, BaseApplication.getContext().getResources().getDisplayMetrics());
    }
    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     *
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale = BaseApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


}

package com.example.weven.bankapp.View;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.weven.bankapp.Activity.BaseApplication;

public class ScreenUtil {

	public static int getScreenWidth() {

		WindowManager wm = (WindowManager) BaseApplication.getContext()
				.getSystemService(Context.WINDOW_SERVICE);

		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}


	public static int getScreenHeight() {
		WindowManager wm = (WindowManager) BaseApplication.getContext()
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}
}

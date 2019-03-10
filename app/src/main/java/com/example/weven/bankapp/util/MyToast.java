package com.example.weven.bankapp.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class MyToast{

    private static Toast toast;

    public static void showMyToast(Context context, String msg){
        if(toast == null){
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }
}

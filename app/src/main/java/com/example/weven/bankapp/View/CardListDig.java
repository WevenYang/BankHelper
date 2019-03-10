package com.example.weven.bankapp.View;

import android.app.Dialog;
import android.content.Context;

import com.example.weven.bankapp.R;

public class CardListDig extends Dialog {

    public CardListDig(Context context, int themeResId) {
        super(context, R.style.BindAccountDialogSty);
        initCardListDialog();
    }

    public void initCardListDialog(){
        setContentView(R.layout.dig_card_list);
    }
}

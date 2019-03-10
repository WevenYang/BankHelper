package com.example.weven.bankapp.View;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.weven.bankapp.R;
import com.example.weven.bankapp.util.TextUtil;
import com.example.weven.bankapp.util.ToastUtil;

public class ModifyBankCardDig extends Dialog {
    EditText et_enterOriginalPassWord;
    EditText et_enterNewPassWord;
    EditText et_enterNewPassWordAgain;
    Button bt_confirm;
    TextView tv_title;
    TextView tv_forgetPassword;
    private boolean isEnterOriginalPassWord = false;
    private boolean isEnterNewPassWord = false;
    private boolean isEnterNewPassWordAgain = false;
    private OnConfirmClickListener onConfirmClickListener;

    public ModifyBankCardDig(@NonNull Context context) {
        super(context);
    }

    public ModifyBankCardDig(Context context, String title) {
        super(context, R.style.BindAccountDialogSty);
        initModifyPassWordDialog(title);
    }

    public void onForgetPasswordClick() {
        ToastUtil.showBottomToast(R.string.unfinish);
    }

    public void onConfirmClick() {
        if (onConfirmClickListener != null) {
            onConfirmClickListener.onConfirmClick(et_enterOriginalPassWord.getText().toString());
        }
    }

    private void initModifyPassWordDialog(String t) {
        setContentView(R.layout.dig_modify_bank_card);
        et_enterOriginalPassWord = (EditText)findViewById(R.id.et_enterOriginalPassWord_modifyPayPassWordDig);
        et_enterNewPassWord = (EditText)findViewById(R.id.et_enterNewPassWord_modifyPayPassWordDig);
        et_enterNewPassWordAgain = (EditText)findViewById(R.id.et_enterNewPassWordAgain_modifyPayPassWordDig);
        bt_confirm = (Button)findViewById(R.id.bt_confirm_modifyPayPassWordDig);
        tv_forgetPassword = (TextView)findViewById(R.id.tv_forgetPassword_modifyPayPassWordDig);
        tv_title = (TextView) findViewById(R.id.tv_title);
        bt_confirm.setClickable(false);
        tv_title.setText(t);
        tv_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onForgetPasswordClick();
            }
        });
        et_enterOriginalPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtil.isValidate(charSequence) && charSequence.length() == 16) {
                    isEnterOriginalPassWord = true;
                } else {
                    isEnterOriginalPassWord = false;
                }
                if (isEnterOriginalPassWord) {
                    bt_confirm.setClickable(true);
                    bt_confirm.setSelected(true);
                } else {
                    bt_confirm.setClickable(false);
                    bt_confirm.setSelected(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_enterNewPassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (com.example.weven.bankapp.util.TextUtil.isValidate(charSequence)) {
                    isEnterNewPassWord = true;
                } else {
                    isEnterNewPassWord = false;
                }
                if (isEnterOriginalPassWord && isEnterNewPassWord && isEnterNewPassWordAgain) {
                    bt_confirm.setClickable(true);
                    bt_confirm.setSelected(true);
                } else {
                    bt_confirm.setClickable(false);
                    bt_confirm.setSelected(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_enterNewPassWordAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtil.isValidate(charSequence)) {
                    isEnterNewPassWordAgain = true;
                } else {
                    isEnterNewPassWordAgain = false;
                }
                if (isEnterOriginalPassWord && isEnterNewPassWord && isEnterNewPassWordAgain) {
                    bt_confirm.setClickable(true);
                    bt_confirm.setSelected(true);
                } else {
                    bt_confirm.setClickable(false);
                    bt_confirm.setSelected(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onConfirmClick();
            }
        });
    }

    public void setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        this.onConfirmClickListener = onConfirmClickListener;
    }

    public interface OnConfirmClickListener {
        void onConfirmClick(String originalPassWord);
    }

    public void clear() {
        et_enterOriginalPassWord.setText("");
        et_enterNewPassWord.setText("");
        et_enterNewPassWordAgain.setText("");
    }

    //忘记密码点击
    public void setOnForgetPasswordClickListener(View.OnClickListener onClickListener) {
        tv_forgetPassword.setOnClickListener(onClickListener);
    }
}

package com.example.weven.bankapp.View;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.weven.bankapp.R;

/**
 * Created by Administrator on 2017/11/23.
 */

public class DeleteAccountDialog extends Dialog {
    EditText et_passWord;
    ImageView iv_passWordSwitch;
    Button bt_bind;
    ImageView iv_dig;
    private boolean isEnterPhoneNumber = false;
    private boolean isEnterPassWord = false;
    private boolean isHidePassWord = true;
    private DeleteAccountDialog.OnUnBindClickListener onUnBindClickListener;

    public DeleteAccountDialog(Context context, int themeResId) {
        super(context, themeResId);
        initBindAccountDialog();
    }

    public void onPasswordSwitchClick() {
        if (isHidePassWord) {
            iv_passWordSwitch.setImageResource(R.mipmap.password_on);
            et_passWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            iv_passWordSwitch.setImageResource(R.mipmap.password_off);
            et_passWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        isHidePassWord = !isHidePassWord;
        et_passWord.postInvalidate();
        //切换后将EditText光标置于末尾
        CharSequence charSequence = et_passWord.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    public void onPasswordTextChanged(CharSequence s) {
        if (TextUtil.isValidate(s)) {
            isEnterPassWord = true;
        } else {
            isEnterPassWord = false;
        }
        setButtonState();
    }

    public void onBindClick() {
        if (onUnBindClickListener != null) {
            onUnBindClickListener.onUnBindClick(et_passWord.getText().toString());
        }
    }

    private void initBindAccountDialog() {
        setContentView(R.layout.dig_unbind_student_account);
        et_passWord = (EditText)findViewById(R.id.et_passWord_unbindStudentAccountDig);
        iv_passWordSwitch = (ImageView) findViewById(R.id.iv_passWordSwitch_unbindStudentAccountDig);
        bt_bind = (Button) findViewById(R.id.bt_bind_unbindStudentAccountDig);
        iv_dig = (ImageView) findViewById(R.id.iv_passWordSwitch_unbindStudentAccountDig);
        bt_bind.setClickable(false);
        et_passWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
        iv_dig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPasswordSwitchClick();
            }
        });
        bt_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBindClick();
            }
        });
        et_passWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                onPasswordTextChanged(editable.toString());
            }
        });
    }

    private void setButtonState() {
        if (isEnterPassWord) {
            bt_bind.setClickable(true);
            bt_bind.setSelected(true);
        } else {
            bt_bind.setClickable(false);
            bt_bind.setSelected(false);
        }
    }

    public void setOnUnBindClickListener(DeleteAccountDialog.OnUnBindClickListener onUnBindClickListener) {
        this.onUnBindClickListener = onUnBindClickListener;
    }

    public void clearEnterInfo() {
        et_passWord.setText("");
    }

    public interface OnUnBindClickListener {
        void onUnBindClick(String passWord);
    }
}

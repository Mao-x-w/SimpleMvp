package com.weknowall.cn.wuwei.widget.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.weknowall.cn.wuwei.R;


/**
 * User: laomao
 * Date: 2016-11-10
 * Time: 19-06
 */

public class CustomLimitNumEditText extends FrameLayout {

    private EditText mEdittext;
    private TextView mTotalNum;
    private TextView mCurrentNum;

    private int totalNum=150;
    private Context mContext;
    private String mHint;

    public CustomLimitNumEditText(Context context) {
        this(context,null);
        mContext = context;
    }

    public CustomLimitNumEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomLimitNumEditText);
        mHint=typedArray.getString(R.styleable.CustomLimitNumEditText_edit_text_hint);
        totalNum=typedArray.getInteger(R.styleable.CustomLimitNumEditText_edit_text_total_num,150);
        View v = View.inflate(context, R.layout.custom_limit_num_edittext, null);
        addView(v);
        initView();
    }

    private void initView() {
        mEdittext= (EditText) findViewById(R.id.custom_limit_num_edittext);
        mTotalNum= (TextView) findViewById(R.id.custom_limit_num_edittext_total_num);
        mCurrentNum= (TextView) findViewById(R.id.custom_limit_num_edittext_current_num);

        mEdittext.setHint(mHint);
        mTotalNum.setText(totalNum+"");

        mEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = mEdittext.getText();
                int length = editable.length();
                if (length>totalNum){
                    mCurrentNum.setText(totalNum+"");
                    Toast.makeText(mContext,"字数超过限制",Toast.LENGTH_SHORT).show();

                    int selEndIndex = Selection.getSelectionEnd(editable);
                    String str = editable.toString();
                    //截取新字符串
                    String newStr = str.substring(0,totalNum);
                    mEdittext.setText(newStr);
                    editable = mEdittext.getText();

                    //新字符串的长度
                    int newLen = editable.length();
                    //旧光标位置超过字符串长度
                    if(selEndIndex > newLen)
                    {
                        selEndIndex = editable.length();
                    }
                    //设置新光标所在的位置
                    Selection.setSelection(editable, selEndIndex);
                }else {
                    mCurrentNum.setText(length+"");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 获取EditText中的内容
     */
    public String getText(){
        return mEdittext.getText().toString().trim();
    }

    public int getTotalNum() {
        return totalNum;
    }

    /**
     * 设置字符总数
     * @param totalNum
     */
    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
        mTotalNum.setText(totalNum);
    }

    /**
     * 设置EditText的默认显示文字
     * @param hint
     */
    public void setHint(String hint) {
        mHint = hint;
        mEdittext.setHint(hint);
    }
}

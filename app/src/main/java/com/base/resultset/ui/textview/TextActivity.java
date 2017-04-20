package com.base.resultset.ui.textview;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.base.resultset.R;
import com.base.resultset.base.BaseActivity;

/**
 * Created by Administrator on 2017/4/20.
 */

public class TextActivity extends BaseActivity {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private SpannableString spanString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_activity);
        init();
    }

    private void init() {
        textView1 = (TextView) this.findViewById(R.id.textView1);
        textView2 = (TextView) this.findViewById(R.id.textView2);
        textView3 = (TextView) this.findViewById(R.id.textView3);
        textView4 = (TextView) this.findViewById(R.id.textView4);
        textView5 = (TextView) this.findViewById(R.id.textView5);
        textView6 = (TextView) this.findViewById(R.id.textView6);
        textView7 = (TextView) this.findViewById(R.id.textView7);
        setTextColor();
        setTextBackground();
        setTextSize();
        setTextBoldInclined();
        setTextStrikethrough();
        setTextUnderline();
        setTextImageReplacement();

    }

    private void setTextImageReplacement() {
        SpannableString spanString = new SpannableString("单个或者几个字图片置换");
        Drawable d = getResources().getDrawable(R.mipmap.ic_launcher);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        spanString.setSpan(span, 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView7.setText(spanString);
    }

    private void setTextUnderline() {
        SpannableString spanString = new SpannableString("单个或者几个字下划线");
        UnderlineSpan span = new UnderlineSpan();
        spanString.setSpan(span, 1, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView6.setText(spanString);
    }

    private void setTextStrikethrough() {
        SpannableString spanString = new SpannableString("单个或者几个字删除线");
        StrikethroughSpan span = new StrikethroughSpan();
        spanString.setSpan(span, 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView5.setText(spanString);
    }

    private void setTextBoldInclined() {
        spanString = new SpannableString("单个或者几个字体粗体、斜体");
        StyleSpan span = new StyleSpan(Typeface.BOLD_ITALIC);
        spanString.setSpan(span, 1, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView4.setText(spanString);
    }

    private void setTextSize() {
        spanString = new SpannableString("单个或者几个字体大小");
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(20);
        spanString.setSpan(span, 2, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView3.setText(spanString);
    }

    private void setTextColor() {
        spanString = new SpannableString("单个或者几个字体颜色设置");
        ForegroundColorSpan span = new ForegroundColorSpan(Color.BLUE);
        spanString.setSpan(span, 1, 5, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        textView1.setText(spanString);
    }

    private void setTextBackground() {
        spanString = new SpannableString("单个或者几个字体背景颜色");
        BackgroundColorSpan span = new BackgroundColorSpan(Color.YELLOW);
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView2.setText(spanString);
    }


    @Override
    public void onClick(View view) {

    }
}

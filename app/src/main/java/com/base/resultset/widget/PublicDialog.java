package com.base.resultset.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.org.civilianhelper.R;

/**
 * @Title: PublicDialog
 * @Description: 公共Dialog
 */
public class PublicDialog {
    private Button leftBT = null;
    private Button rightBT = null;
    private TextView titleTV = null;
    private TextView contentTV = null;
    private View lineView = null;
    private View view = null;
    private OnClickListener mLeftOl = null;
    private OnClickListener mRightOl = null;
    private AlertDialog alertDialog = null;
    private LinearLayout contentView;
    private boolean isDialogCancel = true;
    private LayoutInflater inflater = null;
    private View btLineView = null;
    private View btLayout = null;
    private boolean isAnim = false;

    public PublicDialog(Context context) {
        this(context, 0, 0);
    }

    public boolean isAnim() {
        return isAnim;
    }

    public void setAnim(boolean isAnim) {
        this.isAnim = isAnim;
    }

    public PublicDialog(Context context, int titleId, int contentId) {
        this(context, titleId, contentId, 0, 0);
    }

    public PublicDialog(Context context, String title, String content) {
        this(context, title, content, "", "");
    }

    public PublicDialog(Context context, int titleId, int contentId,
                        int leftButtonId, int rightButtonId) {
        this(context, titleId, contentId, leftButtonId, rightButtonId, null,
                null);
    }

    public PublicDialog(Context context, String title, String content,
                        String leftButton, String rightButton) {
        this(context, title, content, leftButton, rightButton, null, null);
    }

    public PublicDialog(Context context, int titleId, int contentId,
                        int leftButtonId, int rightButtonId, OnClickListener leftOl,
                        OnClickListener rightOl) {
        init(context, (titleId != 0 ? context.getString(titleId) : ""),
                (contentId != 0 ? context.getString(contentId) : ""),
                (leftButtonId != 0 ? context.getString(leftButtonId) : ""),
                (rightButtonId != 0 ? context.getString(rightButtonId) : ""),
                leftOl, rightOl);
    }

    public PublicDialog(Context context, String title, String content,
                        String leftButton, String rightButton, OnClickListener leftOl,
                        OnClickListener rightOl) {
        init(context, title, content, leftButton, rightButton, leftOl, rightOl);
    }

    private void init(Context context, String title, String content,
                      String leftButton, String rightButton, OnClickListener leftOl,
                      OnClickListener rightOl) {
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.public_dialog_orange_layout, null);
        leftBT = (Button) view.findViewById(R.id.public_dialog_left_bt);
        rightBT = (Button) view.findViewById(R.id.public_dialog_right_bt);
        titleTV = (TextView) view.findViewById(R.id.public_dialog_title);
        contentTV = (TextView) view.findViewById(R.id.public_dialog_content);
        contentView = (LinearLayout) view
                .findViewById(R.id.public_dialog_content_layout);
        btLineView = view.findViewById(R.id.public_dialog_btline_view);
        lineView = view.findViewById(R.id.public_dialog_btline_view1);
        btLayout = view.findViewById(R.id.public_dialog_bt_layout);
        leftBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLeftOl != null)
                    mLeftOl.onClick(v);
                alertDialog.dismiss();
            }
        });
        rightBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRightOl != null)
                    mRightOl.onClick(v);
                alertDialog.dismiss();
            }
        });
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mRightOl != null)
                    mRightOl.onClick(rightBT);
            }
        });
        setTitle(title.equals("") ? titleTV.getText().toString() : title);
        setContent(content.equals("") ? contentTV.getText().toString()
                : content);
        setLeftButton("".equals(leftButton) ? leftBT.getText().toString()
                : leftButton);
        setRightButton("".equals(rightButton) ? rightBT.getText().toString()
                : rightButton);
        setLeftButtonClick(leftOl);
        setRightButtonClick(rightOl);
    }

    public void showDialog() {
        if (isAnim) {
            alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            // alertDialog.getWindow().setWindowAnimations(R.style.AnimBottom);
        }
        // alertDialog.setAnimationStyle(R.style.AnimBottom);
        alertDialog.show();
        alertDialog.setCancelable(isDialogCancel);
        alertDialog.setContentView(view);
    }

    public void dismiss() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }


    public boolean isShowing() {
        return alertDialog != null && alertDialog.isShowing();
    }

    public void setDialogCancel(boolean isDialogCancel) {
        this.isDialogCancel = isDialogCancel;
    }

    public void setContentView(View view) {
        if (alertDialog != null) {
            contentTV.setVisibility(View.GONE);
            contentView.addView(view);
        }
    }

    public void setContentView(int resId) {
        if (alertDialog != null) {
            contentTV.setVisibility(View.GONE);
            contentView.addView(inflater.inflate(resId, null));
        }
    }

    public void setTitle(String title) {
        titleTV.setText(title);
    }

    public void setTitle(int stringId) {
        titleTV.setText(stringId);
    }

    public void setTitle(boolean isVisible) {
        if (!isVisible) {
            titleTV.setVisibility(View.GONE);
            lineView.setVisibility(View.GONE);
        } else {
            titleTV.setVisibility(View.VISIBLE);
            lineView.setVisibility(View.VISIBLE);
        }
    }

    public void setContent(String content) {
        contentTV.setText(content);
    }

    public void setContent(int stringId) {
        contentTV.setText(stringId);
    }

    public void setLeftButton(String leftButton) {
        leftBT.setText(leftButton);
    }

    public void setLeftButton(int stringId) {
        leftBT.setText(stringId);
    }

    // 隐藏左边按钮
    public void setLeftButtonVisible(boolean isVisible) {
        if (isVisible) {
            leftBT.setVisibility(View.VISIBLE);
        } else {
            leftBT.setVisibility(View.GONE);
            if (btLineView != null)
                btLineView.setVisibility(View.GONE);
        }

    }

    public void setRightButton(String rightButton) {
        rightBT.setText(rightButton);
    }

    public void setRightButton(int stringId) {
        rightBT.setText(stringId);
    }

    // 隐藏右边按钮
    public void setRightButtonVisible(boolean isVisible) {
        if (isVisible) {
            rightBT.setVisibility(View.VISIBLE);
        } else {
            rightBT.setVisibility(View.GONE);
            if (btLineView != null)
                btLineView.setVisibility(View.GONE);
        }

    }

    public void setLeftAndRightButtonVisible(boolean isVisible) {
        if (isVisible) {
            btLayout.setVisibility(View.VISIBLE);
        } else {
            btLayout.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentView
                    .getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            contentView.setLayoutParams(params);

        }
    }

    public void setBottomVisible(boolean isVisible) {
        if (isVisible) {
            btLayout.setVisibility(View.VISIBLE);
        } else {
            btLayout.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentView
                    .getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            contentView.setLayoutParams(params);

        }
    }

    public void setLeftButtonClick(OnClickListener ol) {
        mLeftOl = ol;
    }

    public void setRightButtonClick(OnClickListener ol) {
        mRightOl = ol;
    }

    public interface OnClickListener {
        void onClick(View v);
    }

}

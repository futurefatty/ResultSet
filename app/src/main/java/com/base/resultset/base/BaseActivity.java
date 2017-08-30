package com.base.resultset.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;

import com.base.resultset.R;
import com.base.resultset.utils.SPUtils;
import com.base.resultset.widget.SystemBarTintManager;

import java.lang.reflect.Method;

public abstract class BaseActivity extends FragmentActivity implements
        OnClickListener {
    private ViewGroup rootView = null;// 全部布局
    private ViewGroup headView = null;// 标题
    protected ViewGroup contentView;// 内容
    protected LayoutInflater mInflater;
    protected SPUtils spUtils;// 偏好
    protected int statusBarColour;// 状态栏颜色
    protected int headViewColour;// 标题栏颜色
    // 用于解决TabActvivty有虚拟键盘的安卓手机 顶部显示横条问题在setContentView前设置成true
    protected boolean isShowStatusBar = false;
    protected boolean isLucency;//设置状态栏是否透明  默认否
    protected boolean isBlack;//设置状态栏是否黑色  默认否
    protected boolean isStick;//设置是否状态栏置顶  默认否

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(this);
        spUtils = new SPUtils(this, "base");
        statusBarColour = getColor(R.color.status_bar_colour);
        headViewColour = getColor(R.color.title_bar_colour);
        if (isLucency) {
            statusBarColour = getColor(R.color.status_bar_colour_lucency);
        }
        if (isBlack) {
            statusBarColour = getColor(R.color.black);
        }
        setStatusBarColour(statusBarColour);
    }

    /**
     * 设置titlebar是否可见
     *
     * @param flag flag为true可见
     */
    public void setTitleBarVisible(boolean flag) {
        if (flag) {
            headView.setVisibility(View.VISIBLE);
        } else {
            headView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(mInflater.inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        if (view != null) {
            rootView = (ViewGroup) mInflater.inflate(R.layout.base_activity,
                    null);
            if (isStick) {
                rootView.setFitsSystemWindows(false);
            }
            headView = (ViewGroup) rootView.findViewById(R.id.base_titlebar);
            headView.setBackgroundColor(headViewColour);// 设置标题栏颜色
            contentView = (ViewGroup) rootView.findViewById(R.id.base_content);
            contentView.addView(view, params);
            super.setContentView(rootView);
        }
    }

    /**
     * 继承这个类字体大小不受系统影响
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    /**
     * 设置状态栏颜色
     */
    @SuppressLint("NewApi")
    public void setStatusBarColour(int colour) {

        if (!isShowStatusBar) {
            // 4.4以上有虚拟键盘
            if (checkDeviceHasNavigationBar(this)
                    & Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                setTranslucentStatus(true);
                SystemBarTintManager tintManager = new SystemBarTintManager(
                        this);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintColor(getResources().getColor(
                        colour));
                tintManager.setNavigationBarTintEnabled(true);
                tintManager.setTintColor(getResources().getColor(colour));

                // 4.4以上并且没有虚拟键盘
            } else if (!(checkDeviceHasNavigationBar(this))
                    & Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                    & Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                setTranslucentStatus(true);
                SystemBarTintManager tintManager = new SystemBarTintManager(
                        this);

                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintColor(getResources().getColor(
                        colour));
                tintManager.setNavigationBarTintEnabled(true);
                tintManager.setTintColor(getResources().getColor(colour));

            }
            // 安卓版本大于等于5.0
            if (!checkDeviceHasNavigationBar(this)
                    & Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(colour);
            }
        }

    }

    /**
     * 判断当前设备是否在屏幕上有虚拟键盘（类似华为手机）是true
     *
     * @param context
     * @return
     */
    public boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs
                .getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class
                    .forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass,
                    "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }

    /**
     * 跟随沉浸状态栏的方法
     *
     * @param on
     */
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}

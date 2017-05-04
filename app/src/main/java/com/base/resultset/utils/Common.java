package com.base.resultset.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/5/4.
 */

public class Common {
    /**
     * 公共普通跳转:适用于直接跳转
     *
     * @param context
     * @param cls
     */
    public static void ActivitySkip(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

}

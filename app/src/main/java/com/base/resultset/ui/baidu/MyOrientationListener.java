package com.base.resultset.ui.baidu;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

/**
 * @author liutao
 *         created at 2017\8\31 0031 20:52
 *         方向传感器
 */
public class MyOrientationListener implements SensorEventListener {

    private SensorManager mSensorManager;//传感器管理者
    private Sensor mSensor;//传感器
    private Context mContext;
    private float lastX;
    //传感器监听
    private OnOrientationListener mOnOrientationListener;

    //初始化方法
    public MyOrientationListener(Context mContext) {
        this.mContext = mContext;
    }

    //方向发生改变
    @SuppressWarnings(
            {"deprecation"})
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float x = event.values[SensorManager.DATA_X];//拿到x
            System.out.println(x + "+++++++++++++++++++++++++++++++++++++++++++++++++");
            if (Math.abs(x - lastX) > 1.0) {//绝对值
                if (mOnOrientationListener != null) {
                    mOnOrientationListener.onOrientationChanged(x);
                }
            }
            lastX = x;
        }
    }

    // 精确发生改变
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    /**
     * 开始传感器发方法
     */
    @SuppressWarnings("deprecation")
    public void start() {
        //赋值传感器管理者
        mSensorManager = (SensorManager) mContext
                .getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            // 获得方向传感器
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        } else {
            Toast.makeText(mContext, "传感器管理者获取失败", Toast.LENGTH_SHORT).show();
        }
        if (mSensor != null) {
            mSensorManager.registerListener(this, mSensor,
                    SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(mContext, "传感器获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 结束传感器方法
     */
    public void stop() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    //set方法
    public void setmOnOrientationListener(OnOrientationListener mOnOrientationListener) {
        this.mOnOrientationListener = mOnOrientationListener;
    }

    //方向监听
    public interface OnOrientationListener {
        void onOrientationChanged(float x);
    }
}

package com.base.resultset.ui.baidu;

import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.MapView;
import com.base.resultset.R;
import com.base.resultset.base.BaseActivity;

/**
 * Created by Administrator on 2017\8\29 0029.
 */

public class MapHomePageActivity extends BaseActivity {

    private MapView mMapView;//百度地图控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_home_page_activity);
        init();
    }

    //初始化
    private void init() {
        mMapView = (MapView) this.findViewById(R.id.map_home_page_bmapView);

    }

    //点击事件
    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

}

package com.base.resultset.ui.baidu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.base.resultset.R;
import com.base.resultset.base.BaseActivity;

/**
 * Created by Administrator on 2017\8\29 0029.
 */

public class MapHomePageActivity extends BaseActivity {
    //地图相关的
    private MapView mMapView;//百度地图控件
    private BaiduMap mBaiduMap;//
    //控件
    private Button generalMap_tv;
    private Button satellitelMap_tv;//
    private Button trafficMap_tv;//
    private Button heatMap_tv;//
    //其他
    private boolean isTrafficMap = false;//是否已开启交通地图 默认felse
    private boolean isHeatMap = false;//是否已开启热力地图 默认felse

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isBlack = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_home_page_activity);
        setTitleBarVisible(false);
        init();
    }

    //初始化
    private void init() {
        mMapView = (MapView) this.findViewById(R.id.map_home_page_bmapView);
        mBaiduMap = mMapView.getMap();
        setBaiduMap();
        generalMap_tv = (Button) this.findViewById(R.id.map_home_page_generalMap_tv);
        satellitelMap_tv = (Button) this.findViewById(R.id.map_home_page_satellitelMap_tv);
        trafficMap_tv = (Button) this.findViewById(R.id.map_home_page_trafficMap_tv);
        heatMap_tv = (Button) this.findViewById(R.id.map_home_page_heatMap_tv);
        generalMap_tv.setOnClickListener(this);
        satellitelMap_tv.setOnClickListener(this);
        trafficMap_tv.setOnClickListener(this);
        heatMap_tv.setOnClickListener(this);
    }

    //设置地图信息
    private void setBaiduMap() {
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//        //卫星地图
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
//        //空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
//        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
//        //开启交通图
//        mBaiduMap.setTrafficEnabled(true);
//        //开启热力地图
//        mBaiduMap.setBaiduHeatMapEnabled(true);
        mMapView.setLogoPosition(LogoPosition.logoPostionleftTop);//百度LOG标记位置logoPostionleftBottom 为左下

    }


    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.map_home_page_generalMap_tv://普通地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.map_home_page_satellitelMap_tv://卫星地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.map_home_page_trafficMap_tv://交通地图
                isTrafficMap = !isTrafficMap;
                mBaiduMap.setTrafficEnabled(isTrafficMap);
                break;
            case R.id.map_home_page_heatMap_tv://热力地图
                isHeatMap = !isHeatMap;
                mBaiduMap.setBaiduHeatMapEnabled(isHeatMap);
                break;
            default:
                break;
        }

    }

    //设置定位
    private void setLocation() {

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

    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                return;
            }
            //定位成功
        }
    }
}

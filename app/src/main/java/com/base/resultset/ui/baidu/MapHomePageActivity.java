package com.base.resultset.ui.baidu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.base.resultset.R;
import com.base.resultset.base.BaseActivity;

/**
 * @author liutao
 *         created at 2017\8\29 0031 20:51
 */
public class MapHomePageActivity extends BaseActivity {
    //地图相关的
    private MapView mMapView;//百度地图控件
    private BaiduMap mBaiduMap;//
    private MapStatusUpdate msu;//设置缩放
    //定位相关的
    private LocationClient mLocationClient = null;
    private BDAbstractLocationListener myListener;
    private LatLng point;//定位经纬度
    private BitmapDescriptor mLocationIcon;//定位图标
    private LocationMode mLocationMode;//图标模式
    private MyOrientationListener myOrientationListener;
    private float mCurrentX = 0;//方向角度 默认0垂直
    //控件
    private Button generalMap_tv;
    private Button satellitelMap_tv;//
    private Button trafficMap_tv;//
    private Button heatMap_tv;//
    private Button location_tv;//
    private Button direction_tv;//map_home_page_direction_tv
    //其他
    private boolean isTrafficMap = false;//是否已开启交通地图 默认felse
    private boolean isHeatMap = false;//是否已开启热力地图 默认felse
    private boolean isFirst = true;

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
        location_tv = (Button) this.findViewById(R.id.map_home_page_location_tv);
        direction_tv = (Button) this.findViewById(R.id.map_home_page_direction_tv);
        generalMap_tv.setOnClickListener(this);
        satellitelMap_tv.setOnClickListener(this);
        trafficMap_tv.setOnClickListener(this);
        heatMap_tv.setOnClickListener(this);
        location_tv.setOnClickListener(this);
        direction_tv.setOnClickListener(this);
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
        msu = MapStatusUpdateFactory.zoomTo(15.0f);//设置缩放距离
        mBaiduMap.setMapStatus(msu);
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
            case R.id.map_home_page_location_tv://我的位置
                switchTo();
                break;
            case R.id.map_home_page_direction_tv://跟随方向
                myOrientationListener.start();//开启传感器
                break;
            default:
                break;
        }

    }


    //初始化定位设置
    private void initLocation() {
        mLocationMode = LocationMode.NORMAL;
        myListener = new MyLocationListener();
        // 开启定位图层
        if (!mBaiduMap.isMyLocationEnabled()) {
            mBaiduMap.setMyLocationEnabled(true);
        }
        //获取到方向传感器监听
        myOrientationListener = new MyOrientationListener(this);
        myOrientationListener.setmOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;//改变方向值
                System.out.println("--------------------------------" + mCurrentX);
            }
        });
        //初始化定位图标
        mLocationIcon = BitmapDescriptorFactory.fromResource(R.mipmap.arrow);
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        //创建
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        int span = 1000;
        option.setScanSpan(span);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        // option.setIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);
        //可选，7.2版本新增能力，如果您设置了这个接口，首次启动定位时，会先判断当前WiFi是否超出有效期，超出有效期的话，会先重新扫描WiFi，然后再定位
//        option.setWifiValidTime(5*60*1000);
        mLocationClient.setLocOption(option);
        mLocationClient.start();//开启定位
    }


    @Override
    protected void onStart() {
        super.onStart();
        //进入页面时
        initLocation();
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
        if (mLocationClient != null && mLocationClient.isStarted()) {//如果还存在
            mLocationClient.stop();//者关闭
            myListener = null;
            mLocationClient = null;
        }
        //关闭定位图层
        if (mBaiduMap.isMyLocationEnabled()) {
            mBaiduMap.setMyLocationEnabled(false);
        }
        //关闭方向监听
        if (myOrientationListener != null) {
            myOrientationListener.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    //定位监听
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                return;
            }
            locationTab(bdLocation);
            if (isFirst) {
                point = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                switchTo();
                isFirst = false;
                System.out.println(bdLocation.getCity() + "-----------------");
            }

            //定位成功.获取定位结果
            bdLocation.getTime();    //获取定位时间
            bdLocation.getLocationID();    //获取定位唯一ID，v7.2版本新增，用于排查定位问题
            bdLocation.getLocType();    //获取定位类型
            bdLocation.getLatitude();    //获取纬度信息
            bdLocation.getLongitude();    //获取经度信息
            bdLocation.getRadius();    //获取定位精准度
            bdLocation.getAddrStr();    //获取地址信息
            bdLocation.getCountry();    //获取国家信息
            bdLocation.getCountryCode();    //获取国家码
            bdLocation.getCity();    //获取城市信息
            bdLocation.getCityCode();    //获取城市码
            bdLocation.getDistrict();    //获取区县信息
            bdLocation.getStreet();    //获取街道信息
            bdLocation.getStreetNumber();    //获取街道码
            bdLocation.getLocationDescribe();    //获取当前位置描述信息
            bdLocation.getPoiList();    //获取当前位置周边POI信息
            bdLocation.getBuildingID();    //室内精准定位下，获取楼宇ID
            bdLocation.getBuildingName();    //室内精准定位下，获取楼宇名称
            bdLocation.getFloor();    //室内精准定位下，获取当前位置所处的楼层信息
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                //当前为GPS定位结果，可获取以下信息
                bdLocation.getSpeed();    //获取当前速度，单位：公里每小时
                bdLocation.getSatelliteNumber();    //获取当前卫星数
                bdLocation.getAltitude();    //获取海拔高度信息，单位米
                bdLocation.getDirection();    //获取方向信息，单位度

            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {

                //当前为网络定位结果，可获取以下信息
                bdLocation.getOperators();    //获取运营商信息

            } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {

                //当前为网络定位结果

            } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {

                //当前网络定位失败
                //可将定位唯一ID、IMEI、定位失败时间反馈至loc-bugs@baidu.com

            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {

                //当前网络不通

            } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {

                //当前缺少定位依据，可能是用户没有授权，建议弹出提示框让用户开启权限
                //可进一步参考onLocDiagnosticMessage中的错误返回码

            }
        }
    }

    //标记定位
    private void locationTab(BDLocation bdLocation) {
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(mCurrentX).latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);
        //赋值定位图标
        MyLocationConfiguration config = new MyLocationConfiguration(mLocationMode, true, mLocationIcon);
        mBaiduMap.setMyLocationConfiguration(config);
    }

    //切换到当前位置
    private void switchTo() {
        MapStatusUpdate SMU = MapStatusUpdateFactory.newLatLng(point);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.animateMapStatus(SMU);
    }
}

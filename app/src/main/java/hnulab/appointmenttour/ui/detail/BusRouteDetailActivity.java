package hnulab.appointmenttour.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;

import butterknife.BindView;
import hnulab.appointmenttour.MyApplication;
import hnulab.appointmenttour.R;
import hnulab.appointmenttour.base.BaseActivity;
import hnulab.appointmenttour.ui.adapter.BusSegmentListAdapter;
import hnulab.appointmenttour.util.AMapUtil;
import hnulab.appointmenttour.util.BusRouteOverlay;

/**
 * Created by dingmouren on 2017/3/2.
 */

public class BusRouteDetailActivity extends BaseActivity implements AMap.OnMapLoadedListener,AMap.OnMapClickListener
                ,AMap.InfoWindowAdapter,AMap.OnInfoWindowClickListener,AMap.OnMarkerClickListener{
    private static final String TAG = BusRouteDetailActivity.class.getName();
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.listview) ListView mListView;
    @BindView(R.id.mapview)
    MapView mMapView;
    @BindView(R.id.tv_logo) TextView mTvLogo;
    @BindView(R.id.tv_route_info) TextView mTvRouteInfo;
    private BusPath mBuspath;
    private BusRouteResult mBusRouteResult;
    private AMap mMap;
    private BusRouteOverlay mBusrouteOverlay;
    private BusSegmentListAdapter mBusSegmentListAdapter;
    private String duration;
    private String distinct;
    private UiSettings mUiSettings;//操作控件类
    private boolean isClicked;//地图只能被点击一次

    public static void newInstance(Activity activity, BusPath busPath, BusRouteResult busRouteResult){
        Intent intent = new Intent(activity,BusRouteDetailActivity.class);
        intent.putExtra("bus_path",busPath);
        intent.putExtra("bus_result",busRouteResult);
        activity.startActivity(intent);
    }
    @Override
    public int setLayoutId() {
        return R.layout.activity_bus_route_detail;
    }

    @Override
    public void init(Bundle savedInstanceStae) {
        if (null != getIntent()){
            mBuspath = getIntent().getParcelableExtra("bus_path");
            mBusRouteResult = getIntent().getParcelableExtra("bus_result");
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);

        mMapView.onCreate(savedInstanceState);
        if (null == mMap) mMap = mMapView.getMap();
        if (null == mUiSettings && null != mMap){
            mUiSettings = mMap.getUiSettings();//获取操作控件类
            mUiSettings.setZoomControlsEnabled(true);//是否显示缩放按钮
            mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
            mUiSettings.setLogoLeftMargin(getWindowManager().getDefaultDisplay().getWidth());//隐藏高德地图的Logo
        }
        duration = AMapUtil.getFriendlyTime((int)mBuspath.getDuration());
        distinct = AMapUtil.getFriendlyLength((int)mBuspath.getDistance());
        if (null != duration && null != distinct) {
            mTvRouteInfo.setText("坐公交耗时" + duration + ",路程" + distinct);
        }else {
            mTvRouteInfo.setText("公交路线详情");
        }


        mBusSegmentListAdapter = new BusSegmentListAdapter(MyApplication.applicationContext,mBuspath.getSteps());
        mListView.setAdapter(mBusSegmentListAdapter);
    }

    @Override
    public void initListener() {
        mMap.setOnMapLoadedListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setInfoWindowAdapter(this);
        mToolbar.setNavigationOnClickListener(v -> finish());
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.mapview && !isClicked){
                    mListView.setVisibility(View.GONE);
                    mMapView.setVisibility(View.VISIBLE);
                    mTvLogo.setVisibility(View.VISIBLE);
                    mMap.clear();
                    mBusrouteOverlay = new BusRouteOverlay(BusRouteDetailActivity.this,mMap,mBuspath,mBusRouteResult.getStartPos(),mBusRouteResult.getTargetPos());
                    mBusrouteOverlay.removeFromMap();
                    isClicked = true;
                }
                return true;
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bus_detail,menu);
        return true;
    }

    @Override//OnMapLoadedListener
    public void onMapLoaded() {
        if (mBusrouteOverlay != null) {
            mBusrouteOverlay.addToMap();
            mBusrouteOverlay.zoomToSpan();
        }
    }

    @Override//OnMapClickListener
    public void onMapClick(LatLng latLng) {

    }

    @Override//InfoWindowAdapter
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override//InfoWindowAdapter
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override//OnInfoWindowClickListener
    public void onInfoWindowClick(Marker marker) {

    }

    @Override//OnMarkerClickListener
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}

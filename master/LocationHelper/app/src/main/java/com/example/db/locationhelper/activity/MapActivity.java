package com.example.db.locationhelper.activity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;

import com.amap.api.maps.MapView;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.example.db.locationhelper.R;
import com.example.db.locationhelper.utils.AppConstant;

public class MapActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {

    public Toolbar toolbar;

    public MapView mMapView;
    public AMap aMap;

    public double Lat = 34.341568;
    public double Lon = 108.940174;

    private LocationSource.OnLocationChangedListener mListener;
    private LocationManagerProxy mAMapLocationManager;

    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        deactivate();
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation.getAMapException().getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);
                // 显示系统小蓝点
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(
                        amapLocation.getLatitude(),amapLocation.getLongitude())));
                CameraUpdateFactory.zoomTo(16);

//                MarkerOptions markerOption = new MarkerOptions();
//                markerOption.position(new LatLng(amapLocation.getLatitude(),
//                        amapLocation.getLongitude()));
//                markerOption.title("上海市").snippet("上海：34.341568, 108.940174");
//                markerOption.draggable(true);
//                Marker marker = aMap.addMarker(markerOption);

            }else {
                Log.v("error00",amapLocation.getAMapException().getErrorMessage().toString());
            }
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mAMapLocationManager != null) {
            mAMapLocationManager.removeUpdates(this);
            mAMapLocationManager.destroy();
        }
        mAMapLocationManager = null;
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mAMapLocationManager == null) {
            mAMapLocationManager = LocationManagerProxy.getInstance(this);
            mAMapLocationManager.requestLocationData(
                    LocationProviderProxy.AMapNetwork, 60*1000, 10, this);
        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        initToolBar();

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        if (aMap==null)
        {
            aMap = mMapView.getMap();
        }
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.getUiSettings().setCompassEnabled(true);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);

    }

    private void initToolBar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("实时地图");
        toolbar.setTitleTextColor(getResources().getColor(R.color.actionbar_title_color));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.actionbar_title_color));

        if (Build.VERSION.SDK_INT >= 21)
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    public Toolbar getToolbar(){
        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}

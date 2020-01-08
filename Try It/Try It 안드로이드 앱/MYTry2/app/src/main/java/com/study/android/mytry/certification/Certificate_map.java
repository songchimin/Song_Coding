package com.study.android.mytry.certification;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;
import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;

import org.xmlpull.v1.XmlPullParserException;

import java.util.Date;

import static com.study.android.mytry.login.Intro.local_url;

public class Certificate_map extends AppCompatActivity
        implements MapView.CurrentLocationEventListener,
        MapReverseGeoCoder.ReverseGeoCodingResultListener{
    private static final String tag="selee";

    Button back_pressed;
    Button trekking_start;
    TextView running_time;
    TextView running_distance;

    MapPolyline polyline;
    int hour, minute, second;
    String s_hour, s_minute, s_second;

    double pre_latitude;
    double pre_longitude;
    Location pre_location = new Location("pointA");

    float distance = 0;
    ViewGroup mapViewContainer;
    MapView mMapView;
    int min = 0;

    String location;
    LinearLayout container;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};

    IMyCounterService binder;

    Date start_time;
    String url;
    String userid;
    String challenge_num;

    //날씨
    TextView txtTemp;
    TextView textWater;
    TextView textState;

    static parsing parsing;

    /**
     * Activity가 Service를 호출합니다.
     * 이 때, 꼭 binder가 필요합니다.
     */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            /**
             * Service가 가지고있는 binder를 전달받는다.
             * 즉, Service에서 구체화한 getCount() 메소드를 받았습니다.
             */
            binder = IMyCounterService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Intent intent;
    private boolean running = true;
    int btn_check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certificate_map);

        Log.d(tag, "certificate_mappppppppppppp 지도화면");

        try {
            parsing = new parsing();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        WeatherTask api1 = new WeatherTask();
        api1.execute();

        txtTemp = findViewById(R.id.temp);
        textWater= findViewById(R.id.water);
        textState= findViewById(R.id.state);

        trekking_start = (Button)findViewById(R.id.trekking_start); //트레킹 시작, 종료
           back_pressed = (Button) findViewById(R.id.map_back_btn);
        running_time = (TextView) findViewById(R.id.running_time);
        running_distance = (TextView) findViewById(R.id.running_distance);
        container = (LinearLayout) findViewById(R.id.map_temp_linear);

        GlobalApplication myApp = (GlobalApplication) getApplication();
        userid = myApp.getGlobalString();

        back_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Certificate_map.this, Certificate_map_popup.class);
                intent3.putExtra("distance", Math.round(distance*10)/10.0+"");
                Log.d(tag, "distanceeeeeeeeeeeeeeee " + Math.round(distance*10)/10.0);
                startActivityForResult(intent3, 1234); // 다음화면으로 넘어가기
            }
        });

        mMapView = new MapView(this);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mMapView);

        Intent intent2 = getIntent();
        challenge_num = intent2.getExtras().getString("challenge_num");
        Log.d(tag, "챌린지 넘버000000000 "+challenge_num);

        Log.d(tag, "왜바로꺼지냐");

        trekking_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_check == 1) {
                    if(distanceCheck()==true) {
                        Date end_time = new Date();

                        Toast.makeText(Certificate_map.this, "1키로 걷기 성공!!", Toast.LENGTH_SHORT).show();

                        unbindService(connection);
                        running = false;

                        Intent intent = new Intent(getApplicationContext(), Certificate_map_comment.class);

                        Intent intent2 = getIntent();
                        intent.putExtra("challenge_num", intent2.getExtras().getString("challenge_num"));
                        intent.putExtra("challenge_title", intent2.getExtras().getString("challenge_title"));
                        intent.putExtra("running_time", running_time.getText().toString());
                        intent.putExtra("running_distance", running_distance.getText().toString());
                        intent.putExtra("start_time", start_time);
                        intent.putExtra("end_time", end_time);
                        intent.putExtra("location", location);

                        Log.d(tag, "locationnnnnn : " + location);
                        Log.d(tag, "start_time : " + start_time);
                        Log.d(tag, "end time : " + end_time);

                        startActivity(intent);
                        finish();

                        Log.d(tag, "Certificate_map.finish() 됨!!");
                    } else {
                        Intent intent3 = new Intent(Certificate_map.this, Certificate_map_popup.class);
                        intent3.putExtra("distance", Math.round(distance*10)/10.0+"");
                        Log.d(tag, "distanceeeeeeeeeeeeeeee " + Math.round(distance*10)/10.0);
                        startActivityForResult(intent3, 1234); // 다음화면으로 넘어가기
                    }
                } else {
                    trekking_start.setText("트레킹 종료");

                    start_time = new Date();

                    if (!checkLocationServicesStatus()) {
                        showDialogForLocationServiceSetting();
                    }else {
                        checkRunTimePermission();
                    }

                    mMapView.setCurrentLocationEventListener(Certificate_map.this);

                    intent = new Intent(Certificate_map.this, MyCounterService.class);
                    bindService(intent, connection, BIND_AUTO_CREATE);
                    running = true;
                    new Thread(new GetCountThread()).start();
                    btn_check ++;
                    Log.d(tag, "btn_check : " + btn_check);
                }
            }
        });
    }

    /**
     *
     */
    private class GetCountThread implements Runnable {
        // binder에서 count가져와서 set시키려면 handler 필요
        private Handler handler = new Handler();

        @Override
        public void run() {
            while (running) {
                if(binder == null) {
                    continue;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            hour = binder.getCount()/3600;
                            minute = binder.getCount()/60;
                            second = binder.getCount()%60;
                            if(hour<10) {
                                s_hour="0"+hour;
                            } else {
                                s_hour=""+hour;
                            }
                            if(minute<10) {
                                s_minute="0"+minute;
                            } else {
                                s_minute=""+minute;
                            }
                            if(second<10) {
                                s_second="0"+second;
                            } else {
                                s_second=""+second;
                            }
                            running_time.setText(s_hour+":"+s_minute+":"+s_second);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                });
                // 1초 텀을 준다.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean distanceCheck() {
        double th = 500;
        if(distance >= th) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mMapView.setShowCurrentLocationMarker(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i(tag, "로그찍히는지확인중"+String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));

        try {
            if(min == 0) {
                pre_latitude = (double)mapPointGeo.latitude;
                pre_longitude = (double)mapPointGeo.longitude;

                // 좌표저장
                url = local_url + "/selee/map_insert";

                String msg = "?member_id="+userid+
                        "&challenge_num="+challenge_num+
                        "&feed_info="+pre_latitude+","+pre_longitude;

                url = url +msg;
                Log.d("certificate", url);

                Certificate_map.NetworkTask networkTask = new Certificate_map.NetworkTask(url, null);
                networkTask.execute();

                pre_location.setLatitude(pre_latitude);
                pre_location.setLongitude(pre_longitude);

                Log.d(tag, "pre_lat, lon : " + pre_latitude + "   "+ pre_longitude);
                trekking_start(pre_latitude, pre_longitude);

            } else {

                double new_latitude = (double)mapPointGeo.latitude;
                double new_longitude = (double)mapPointGeo.longitude;

                // 좌표저장
                url = local_url + "/selee/map_insert";

                String msg = "?member_id="+userid+
                        "&challenge_num="+challenge_num+
                        "&feed_info="+new_latitude+","+new_longitude;

                url = url +msg;
                Log.d("certificate", url);

                Certificate_map.NetworkTask networkTask = new Certificate_map.NetworkTask(url, null);
                networkTask.execute();



                Location new_location = new Location("pointB");
                new_location.setLatitude(new_latitude);
                new_location.setLongitude(new_longitude);

                float distanceMeter = pre_location.distanceTo(new_location);


                if(distanceMeter > 3.5) {
                    Log.d(tag, "아래두개 로그는 30미터 넘어서 안들어가야돼");
                    Log.d(tag, Math.round(distance*10)/10.0 + " aaa");
                    Log.d(tag, Math.round(distanceMeter*10)/10.0+ " bbb");
                } else {
                    // 폴리라인 그리는 것도 30미터 안넘을때만!
                    Log.d(tag, "new_lat, lon : " + pre_latitude + "   "+ pre_longitude);
                    trekking_ing(new_latitude, new_longitude);

                    distance = distance+distanceMeter;
                }

                running_distance.setText(Math.round(distance*10)/10.0+"m");

                pre_latitude = new_latitude;
                pre_longitude = new_longitude;
                pre_location = new_location;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    // 통신
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpConnection requestHttpURLConnection = new RequestHttpConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView , float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        onFinishReverseGeoCoding("Fail");
    }

    private void onFinishReverseGeoCoding(String result) {
//        Toast.makeText(LocationDemoActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }

    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if ( check_result ) {
                Log.d("@@@", "start");
                //위치 값을 가져올 수 있음
                mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                    Toast.makeText(Certificate_map.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(Certificate_map.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission(){
        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(Certificate_map.this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)

            // 3.  위치 값을 가져올 수 있음
            mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);

        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(Certificate_map.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(Certificate_map.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(Certificate_map.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(Certificate_map.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Certificate_map.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
        }

        if(requestCode == 1234 && resultCode == RESULT_OK){
            String myData = data.getStringExtra("check");
            if(myData.equals("나중에")) {

                unbindService(connection);
                running = false;



                // 좌표저장
                url = local_url + "/selee/map_delete";

                String msg = "?member_id="+userid+
                        "&challenge_num="+challenge_num;

                url = url +msg;
                Log.d(tag, url);

                Certificate_map.NetworkTask networkTask = new Certificate_map.NetworkTask(url, null);
                networkTask.execute();

                finish();

            } else {
            }
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void trekking_start(double latitude, double longitude) {
        //자신의 접속 위치를 받아 오자.
        double lat = latitude;
        double lon = longitude;

        location = latitude+","+longitude;

        Log.d(tag, "마커 : " + lat + "   "+ lon);

        //마커 찍기
        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(lat, lon);
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("출발");
        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(128, 255, 51, 0)); // Polyline 컬러 지정.

// Polyline 좌표 지정.
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));

        min++;
        mMapView.addPOIItem(marker);
    }

    public void trekking_ing(double latitude, double longitude) {
        //자신의 접속 위치를 받아 오자.
        double lat = latitude;
        double lon = longitude;

        location = location+"!"+latitude+","+longitude;

        Log.d(tag, "폴리라인 : " + lat + "   "+ lon);

        // Polyline 좌표 지정.
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(lat, lon));

        // Polyline 지도에 올리기.
        mMapView.addPolyline(polyline);

        // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
        MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
        int padding = 100; // px
        mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
    }

    public class WeatherTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... integers) {
            //파싱

            String result="";
            result = parsing.chodanparsing();
            result = result + parsing.dongnae2parsing();

            return result;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s!=null){
                Log.d("lecture","여기 마이페이지 날씨이이이"+s);

                String weather[] = s.split(",");

                txtTemp.setText(weather[1] + " ℃");
                textWater.setText(weather[0]+" %");
                textState.setText(weather[2]);
            }

        }
    }
}
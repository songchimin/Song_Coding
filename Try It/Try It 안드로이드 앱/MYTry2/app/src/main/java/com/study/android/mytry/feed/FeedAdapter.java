package com.study.android.mytry.feed;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.mytry.R;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.main.Fragment_Feed;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

import static com.study.android.mytry.login.Intro.local_url;
import static com.study.android.mytry.main.Fragment_Feed.f_list;
import static com.study.android.mytry.main.Fragment_Feed.type;
import static com.study.android.mytry.main.Fragment_Feed.userid;

public class FeedAdapter extends BaseAdapter implements MapView.CurrentLocationEventListener,
        MapReverseGeoCoder.ReverseGeoCodingResultListener {
    private static final String tag="selee";

    ViewGroup mapViewContainer;
    MapView mMapView;
    Context feed_context;
    ArrayList<FeedItem> items = new ArrayList<>();
    MapPolyline polyline;
    String location ="";

    public FeedAdapter(Context context) {
        this.feed_context = context;
    }

    public void addItem(FeedItem item) {
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final FeedItem item = items.get(position);
        FeedMapView view = null;

        if(convertView==null){
            view=new FeedMapView(feed_context);
        }else {
            view=(FeedMapView) convertView;
        }

        // 좋아요
        LinearLayout like_btn = view.findViewById(R.id.feed_map_item_like);

        final ImageView like_image = view.findViewById(R.id.feed_map_item_like_image);

        if (item.getLikeExist() == 1) {
            like_image.setBackgroundResource(R.drawable.feed_like_check);
        } else if (item.getLikeExist() == 0) {
            like_image.setBackgroundResource(R.drawable.feed_like_default);
        }

        // 좋아요 버튼 처리 (숫자, 버튼)
        like_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                String url = local_url + "/selee/feed_like";
                String msg = "?feed_num=" + item.getFeed_num()+
                        "&member_id=" + userid+
                        "&type=" + type;
                Log.d(tag, msg);
                url = url + msg;
                Log.d(tag, url);

                FeedAdapter.NetworkTask1 networkTask = new FeedAdapter.NetworkTask1(url, null);
                networkTask.execute();
            }
        });

        ImageButton button2 = view.findViewById(R.id.feed_map_item_message_btn);
        button2.setFocusable(false);
        button2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d(tag, "화면넘어가라제발");
                Intent intent = new Intent(feed_context, FeedComment.class);
                intent.putExtra("feed_num", item.getFeed_num());
                intent.putExtra("member_profile_image", item.getMember_profile());
                intent.putExtra("member_nickname", item.getMember_nicname());
                intent.putExtra("feed_update_time", item.getFeed_update_time());
                intent.putExtra("feed_comment", item.getFeed_comment());

                Log.d(tag, "피드번호 " + item.getFeed_num());
                feed_context.startActivity(intent);
            }
        });

        TextView comment_text = view.findViewById(R.id.feed_map_item_commentCount_textView);
        comment_text.setFocusable(false);
        comment_text.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d(tag, "화면넘어가라제발");
                Intent intent = new Intent(feed_context, FeedComment.class);
                intent.putExtra("feed_num", item.getFeed_num());
                intent.putExtra("member_profile_image", item.getMember_profile());
                intent.putExtra("member_nickname", item.getMember_nicname());
                intent.putExtra("feed_update_time", item.getFeed_update_time());
                intent.putExtra("feed_comment", item.getFeed_comment());
                feed_context.startActivity(intent);
            }
        });

        TextView comment_like_text = view.findViewById(R.id.feed_map_item_likeCount_textView);
        comment_like_text.setFocusable(false);
        comment_like_text.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d(tag, "누가좋아요했냐");
                Intent intent = new Intent(feed_context, FeedLike.class);
                intent.putExtra("feed_num", item.getFeed_num());
                intent.putExtra("like_count", item.getLikecount());
                feed_context.startActivity(intent);
            }
        });

        ImageView profile_img = view.findViewById(R.id.feed_map_item_profile_imageView);
        profile_img.setFocusable(false);
        profile_img.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d(tag, "사진누르면");
                Intent intent = new Intent(feed_context, Userpage.class);
                intent.putExtra("member_id", item.getMember_id());
                feed_context.startActivity(intent);
            }
        });

        // feed 기본정보 출력
        if(item.getFeed_info().equals("map")) {
            mMapView = new MapView(view.context);
            mapViewContainer = (ViewGroup) view.findViewById(R.id.feed_map_view);
            mapViewContainer.addView(mMapView);

            String url2 = local_url + "/selee/location_list";
            String msg = "?challenge_num="+item.getChallenge_num()+
                    "&member_id="+item.getMember_id()+
                    "&certificate_date="+item.getFeed_update_time();
            url2 = url2+msg;
            Log.d(tag, "url2222 : "+url2);

            // location정보를 출력
            FeedAdapter.NetworkTask2 networkTask2 = new FeedAdapter.NetworkTask2(url2, null);
            networkTask2.execute();
        }

        view.setFeed_item_main_imageView(item.getFeed_info());
        view.setFeed_map_item_profile_imageView(item.getMember_profile());
        view.setFeed_map_item_email_textView(item.getMember_nicname());
        view.setFeed_map_item_date_textView(item.getFeed_update_time());
        view.setFeed_map_item_title_textView(item.getChallenge_title());
        view.setFeed_map_item_comment(item.getFeed_comment());
        view.setFeed_map_item_likeCount_textView(item.getLikecount());
//        view.setFeed_item_comment_userNicname(item.getComment_userid());
        view.setFeed_map_item_commentCount_textView(item.getComment_count());
        view.setFeed_item_myprofile_imageView(Fragment_Feed.mypofile_url);

        return view;
    }



    // 통신 = insert, delete?
    public class NetworkTask1 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask1(String url, ContentValues values) {
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
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            FeedAdapter feedAdapter = new FeedAdapter(feed_context);

            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(json);
                JSONArray ListArray = jsonObject.getJSONArray("List");

                FeedItem[] feedItems = new FeedItem[ListArray.length()];

                for (int i = 0; i < ListArray.length(); i++) {

                    JSONObject ListObject2 = ListArray.getJSONObject(i);

                    feedItems[i] = new FeedItem(
                            ListObject2.getInt("feed_num"),
                            ListObject2.getString("member_id"),
                            ListObject2.getInt("challenge_num"),
                            ListObject2.getString("challenge_title"),
                            ListObject2.getString("feed_update_time"),
                            ListObject2.getString("feed_info"),
                            ListObject2.getString("feed_comment"),
                            ListObject2.getInt("feed_like_count"),
                            ListObject2.getInt("feed_like_exist"),
                            ListObject2.getInt("feed_comment_count"),
                            ListObject2.getString("member_profile"),
                            ListObject2.getString("member_nicname")
                            );

                    feedAdapter.addItem(feedItems[i]);
                }
                f_list.setAdapter(feedAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // map location 통신
    public class NetworkTask2 extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetworkTask2(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result; // 요청 결과를 저장할 변수.
            RequestHttpConnection requestHttpURLConnection = new RequestHttpConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            Log.d(tag, "resultttttt " + result);
            return result;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            Log.d(tag, "jsonnnnnnnnnn : "+json);

            try {
                if(json !=null) {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray ListArray = jsonObject.getJSONArray("location_list");
                    Log.d(tag, ListArray.toString());

                    Log.d(tag, String.valueOf(ListArray.length()));
                    location="";
                    for (int i = 0; i < ListArray.length(); i++) {
                        JSONObject ListObject3 = ListArray.getJSONObject(i);
                        location = location+ListObject3.getDouble("latitude")+","+ListObject3.getDouble("longitude")+"!";
                        Log.d(tag, "location통신   "+ListObject3.getDouble("latitude")+"  "+ListObject3.getDouble("longitude"));
                    }
                    Log.d(tag, "locationnnnnnnnnnn" + location);
                    addMarkAndPolyline(location);
                } else{
                    Log.d(tag, "Nulllllllll");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void addMarkAndPolyline(String locations) {
        double lat;
        double lon;

        if(locations != null) {
            Log.d(tag, "location어떻게찍히니 어뎁터 "+locations);
            String location = locations.substring(0, locations.length()-1);
            Log.d(tag, location);
            String[] loc = location.split("!");

            for(int i=0; i< loc.length ; i++) {
                Log.d(tag, loc.length+"");
                lat = Double.parseDouble(loc[i].substring(0, loc[i].indexOf(",")));
                lon = Double.parseDouble(loc[i].substring(loc[i].indexOf(",")+1));

                Log.d(tag, "위도경도들어오냐 어뎁터 " + lat + "   " + lon);

                if(i == 0) {
                    MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(lat, lon);
                    MapPOIItem marker = new MapPOIItem();
                    marker.setItemName("출발");
                    marker.setTag(0);
                    marker.setMapPoint(MARKER_POINT);
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                    polyline = new MapPolyline();
                    polyline.setTag(1000);
                    polyline.setLineColor(Color.rgb(255, 94, 94)); // Polyline 컬러 지정.

                    polyline.addPoint(MapPoint.mapPointWithGeoCoord(lat, lon));

                    mMapView.addPOIItem(marker);

                } else if(i == loc.length-1) {
                    MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(lat, lon);
                    MapPOIItem marker = new MapPOIItem();
                    marker.setItemName("도착");
                    marker.setTag(0);
                    marker.setMapPoint(MARKER_POINT);
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

                    mMapView.addPOIItem(marker);
                }

                // Polyline 좌표 지정.
                polyline.addPoint(MapPoint.mapPointWithGeoCoord(lat, lon));

                // Polyline 지도에 올리기.
                mMapView.addPolyline(polyline);

                // 지도뷰의 중심좌표와 줌레벨을 Polyline이 모두 나오도록 조정.
                MapPointBounds mapPointBounds = new MapPointBounds(polyline.getMapPoints());
                int padding = 100; // px
                mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding));
            }
        } else {
            Log.d(tag, "FeedAdapter_item.getLocation()_nullllllllllllllllllllll어뎁터");
        }
    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder , String s) {

    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {

    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView , MapPoint mapPoint , float v) {

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
}
package com.study.android.mytry.main;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.study.android.mytry.R;
import com.study.android.mytry.feed.FeedAdapter;
import com.study.android.mytry.feed.FeedItem;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.study.android.mytry.login.Intro.local_url;

public class Fragment_Feed extends Fragment {
    private static final String tag = "selee";

    public static ListView f_list;
    public static String userid;
    public static String type ="follow";
    FeedAdapter adapter;

    Button top_btn1;
    Button top_btn2;

    String url;
    Activity activity;

    static public String mypofile_url;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity)
            activity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feed_main, container, false);

        GlobalApplication myApp = (GlobalApplication) getActivity().getApplication();
        userid = myApp.getGlobalString();

        f_list = view.findViewById(R.id.feed_toto);
        top_btn1 = view.findViewById(R.id.feed_top_following_btn);
        top_btn2 = view.findViewById(R.id.feed_top_all_btn);

        url = local_url + "/selee/feed_list";
        String msg = "?member_id="+userid+
                "&type=follow";
        url = url+msg;
        Log.d(tag, "url : "+url);

        Fragment_Feed.FeedListNetworkTask feedListNetworkTask = new Fragment_Feed.FeedListNetworkTask(url, null);
        feedListNetworkTask.execute();

        String url2 = local_url + "/yejin/mypage_main_list";
        url2 = url2 + "?member_id=" + userid;
        Log.d(tag, "맴버정보url "+url2);

        Fragment_Feed.MemberNetworkTask networkTask = new Fragment_Feed.MemberNetworkTask(url2, null);
        networkTask.execute();

        top_btn1.setSelected(true);

        //팔로잉 list출력
        top_btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                top_btn1.setSelected(true);
                top_btn2.setSelected(false);

                url = local_url + "/selee/feed_list";
                String msg = "?member_id="+userid+
                        "&type=follow";
                url = url+msg;
                Log.d(tag, "url : "+url);
                type = "follow";

                Fragment_Feed.FeedListNetworkTask feedListNetworkTask = new Fragment_Feed.FeedListNetworkTask(url, null);
                feedListNetworkTask.execute();
            }
        });

        // 전체 feed_list출력
        top_btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                top_btn1.setSelected(false);
                top_btn2.setSelected(true);

                url = local_url + "/selee/feed_list";
                String msg = "?member_id="+userid+
                        "&type=all";
                url = url+msg;
                Log.d(tag, "url : "+url);
                type = "all";

                Fragment_Feed.FeedListNetworkTask feedListNetworkTask = new Fragment_Feed.FeedListNetworkTask(url, null);
                feedListNetworkTask.execute();
            }
        });

        return view;
    }

    // feed_list 통신
    public class FeedListNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public FeedListNetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
            Log.d(tag, "통신111111");
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
            adapter = new FeedAdapter(activity);
            try {
                if(json !=null) {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray ListArray = jsonObject.getJSONArray("List");
                    Log.d(tag, ListArray.toString());

                    FeedItem[] feedItems= new FeedItem[ListArray.length()];

                    Log.d(tag, String.valueOf(ListArray.length()));

                    for (int i = 0; i < ListArray.length(); i++) {
                        JSONObject ListObject2 = ListArray.getJSONObject(i);

                        //여기서 회원정보 찍어서 넘겨야됨!!!!!

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
                                ListObject2.getString("member_nicname"));

                        Log.d(tag, "피드아이템 확인확인 "+ListObject2.getString("member_nicname")+"  "+ListObject2.getString("challenge_title")+"  "+ListObject2.getString("member_profile"));
                        adapter.addItem(feedItems[i]);
                    }
                    f_list.setAdapter(adapter);
                } else{
                    Log.d(tag, "Nulllllllll");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // 회원정보 불러오기
    public class MemberNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public MemberNetworkTask(String url, ContentValues values) {
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

            try {
                System.out.println(json);
                JSONObject jsonObject = new JSONObject(json);

                JSONArray MemberList = jsonObject.getJSONArray("List");

                if (MemberList != null) {
                    for (int i = 0; i < MemberList.length(); i++) {
                        JSONObject ListObject = MemberList.getJSONObject(i);
                        mypofile_url = ListObject.getString("member_profile_image");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

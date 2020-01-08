package com.study.android.mytry.feed;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.study.android.mytry.R;
import com.study.android.mytry.challenge_public.CommentItem;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.study.android.mytry.login.Intro.local_url;


public class FeedLike extends AppCompatActivity {
    private static final String tag="selee";

    Button back_pressed;
    String userid;
    FeedLike.CommentNetworkTask networkTask;
    String url;
    public static ListView feed_like_list;
    FeedLikeAdapter feedlikeAdapter;
    static HashMap<String, String> feed_like_map = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_like);
        Log.d(tag, "FeedLike!!!!!!!!!!!");

        back_pressed = (Button) findViewById(R.id.feed_like_back_btn);
        back_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GlobalApplication myApp = (GlobalApplication) getApplication();
        userid = myApp.getGlobalString();

        Intent intent = getIntent();
        feed_like_map.put("feed_num", String.valueOf(intent.getExtras().getInt("feed_num")));
        feed_like_map.put("likeCount", String.valueOf(intent.getExtras().getInt("like_count")));
        feed_like_map.put("my_id", userid);

        feed_like_list = (ListView) findViewById(R.id.feed_like_list);
        feedlikeAdapter = new FeedLikeAdapter(getApplicationContext());

        url = local_url + "/selee/feed_like_list"+
                            "?feed_num="+intent.getExtras().getInt("feed_num")+
                            "&user_id="+userid;

        Log.d(tag, url);

        networkTask = new CommentNetworkTask(url, null);
        networkTask.execute();
    }

    // 통신
    public class CommentNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public CommentNetworkTask(String url, ContentValues values) {

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
                JSONObject jsonObject = new JSONObject(json);
                JSONArray ListArray = jsonObject.getJSONArray("List");

                FeedLikeItem[] feedLikeItems = new FeedLikeItem[ListArray.length()];

                // 생성
                for (int i = 0; i < ListArray.length(); i++) {

                    JSONObject ListObject = ListArray.getJSONObject(i);

                    feedLikeItems[i] = new FeedLikeItem(String.valueOf(ListObject.getString("feedLikeUserid")), ListObject.getInt("followExist"));
                    feedlikeAdapter.addItem(feedLikeItems[i]);

                    Log.d(tag, "feedLikeUserid     "+ListObject.getString("feedLikeUserid"));

                    String url = local_url + "/yejin/mypage_main_list";
                    url = url + "?member_id=" + ListObject.getString("feedLikeUserid");
                    Log.d(tag, url);

                    FeedLike.MemberNetworkTask networkTask = new FeedLike.MemberNetworkTask(url, null);
                    networkTask.execute();
                }
                feed_like_list.setAdapter(feedlikeAdapter);
                Log.d(tag, "commentAdapter 갯수 " + String.valueOf(feedlikeAdapter.getCount()));


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
                        feed_like_map.put("member_nickname", ListObject.getString("member_nickname"));
                        feed_like_map.put("member_profile_image", ListObject.getString("member_profile_image"));

                        Log.d(tag,"정보넘어오냐"+ ListObject.getString("member_nickname")+"    "+ListObject.getString("member_profile_image"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

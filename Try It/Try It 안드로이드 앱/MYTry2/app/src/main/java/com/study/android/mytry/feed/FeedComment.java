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
import static com.study.android.mytry.main.Fragment_Feed.mypofile_url;


public class FeedComment extends AppCompatActivity {
    private static final String tag="selee";

    Button back_pressed;
    String userid;
    ImageView feed_comment_user_profile_imageView;
    TextView feed_comment_email_textView;
    TextView feed_comment_date_textView;
    TextView feed_comment_content_textView;
    Button feed_comment_btn;
    int feed_num;
    EditText feed_comment_comment_editText;
    FeedComment.CommentNetworkTask networkTask;
    String url;
    public static ListView feed_comment_list;
    static HashMap<String, String> feed_map = new HashMap<>();
    FeedCommentAdapter feedcommentAdapter;
    ImageView feed_comment_myprofile_imageView;
    String user_pofile_url;
    String user_nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_comment);
        Log.d(tag, "FeedComment!!!!!!!!!!!");

        back_pressed = (Button) findViewById(R.id.feed_comment_back_btn);
        back_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        GlobalApplication myApp = (GlobalApplication) getApplication();
        userid = myApp.getGlobalString();

        feed_comment_user_profile_imageView = (ImageView)findViewById(R.id.feed_comment_user_profile_imageView);
        feed_comment_email_textView = (TextView)findViewById(R.id.feed_comment_email_textView);
        feed_comment_date_textView = (TextView)findViewById(R.id.feed_comment_date_textView);
        feed_comment_content_textView = (TextView)findViewById(R.id.feed_comment_content_textView);
        feed_comment_comment_editText = (EditText)findViewById(R.id.feed_comment_comment_editText);

        Intent intent = getIntent();

        String profile_url = local_url + "/" + intent.getExtras().getString("member_profile_image") + ".jpg";
        Picasso.with(this)
                .load(profile_url)
                .transform(new CircleTransform())
                .into(feed_comment_user_profile_imageView);

        feed_comment_myprofile_imageView = (ImageView)findViewById(R.id.feed_comment_myprofile_imageView);
        String my_profile_url = local_url + "/" + mypofile_url + ".jpg";
        Picasso.with(this)
                .load(my_profile_url)
                .transform(new CircleTransform())
                .into(feed_comment_myprofile_imageView);

        feed_comment_email_textView.setText(intent.getExtras().getString("member_nickname"));
        feed_comment_date_textView.setText(intent.getExtras().getString("feed_update_time"));
        feed_comment_content_textView.setText(intent.getExtras().getString("feed_comment"));
        feed_num = intent.getExtras().getInt("feed_num");

        feed_map.put("num", feed_num+"");
        feed_comment_list = (ListView) findViewById(R.id.feed_comment_list);
        feedcommentAdapter = new FeedCommentAdapter(getApplicationContext());

        url = local_url + "/selee/comment_list";

        networkTask = new CommentNetworkTask(url, null);
        networkTask.execute();

        feed_comment_btn = (Button) findViewById(R.id.feed_comment_btn);
        feed_comment_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                url = local_url + "/selee/comment_insert";
                String msg = "?feed_num=" + feed_num +
                        "&member_id=" + userid +
                        "&content=" + feed_comment_comment_editText.getText().toString();

                Log.d(tag, msg);
                url = url + msg;

                Log.d("lecture", url);

                networkTask = new CommentNetworkTask(url, null);
                networkTask.execute();
            }
        });
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

                int count = 0;
                for (int i = 0; i < ListArray.length(); i++) {
                    JSONObject ListObject = ListArray.getJSONObject(i);
                    if (feed_map.get("num").equals(String.valueOf(ListObject.getInt("feed_num")))) {
                        count++;
                    }
                }

                // 생성
                if (count < feedcommentAdapter.getCount() || feedcommentAdapter.getCount() == 0) {
                    FeedCommentItem[] commentItems = new FeedCommentItem[ListArray.length()];

                    for (int i = 0; i < ListArray.length(); i++) {

                        JSONObject ListObject = ListArray.getJSONObject(i);

                        Log.d(tag, String.valueOf(ListObject.getInt("comment_num")));
                        Log.d(tag, ListObject.getString("feed_num"));
                        Log.d(tag, ListObject.getString("member_id"));
                        Log.d(tag, ListObject.getString("comment_content"));
                        Log.d(tag, ListObject.getString("commment_date"));
                        Log.d(tag, String.valueOf(ListObject.getInt("commment_like_count")));
                        Log.d(tag, String.valueOf("exist" + ListObject.getInt("commment_like_exist")));

                        Log.d(tag, "이거 유저네임가져온거야     "+ListObject.getString("member_id"));

                        String url2 = local_url + "/yejin/mypage_main_list";
                        url2 = url2 + "?member_id=" + ListObject.getString("member_id");
                        Log.d(tag, "맴버정보url "+url2);

                        FeedComment.MemberNetworkTask networkTask = new FeedComment.MemberNetworkTask(url2, null);
                        networkTask.execute();

                        if (feed_map.get("num").equals(String.valueOf(ListObject.getInt("feed_num")))) {
                            commentItems[i] = new FeedCommentItem(ListObject.getInt("comment_num"), String.valueOf(ListObject.getString("member_id")), ListObject.getString("comment_content"), ListObject.getString("commment_date"), ListObject.getInt("commment_like_count"), ListObject.getInt("commment_like_exist"), user_pofile_url, user_nickname);

                            feedcommentAdapter.addItem(commentItems[i]);
                        }
                    }
                    feed_comment_list.setAdapter(feedcommentAdapter);
                    Log.d(tag, "commentAdapter 갯수 " + String.valueOf(feedcommentAdapter.getCount()));
                } else {
                    JSONObject ListObject = ListArray.getJSONObject(ListArray.length() - 1);

                    String url2 = local_url + "/yejin/mypage_main_list";
                    url2 = url2 + "?member_id=" + ListObject.getString("member_id");
                    Log.d(tag, "맴버정보url "+url2);

                    FeedComment.MemberNetworkTask networkTask = new FeedComment.MemberNetworkTask(url2, null);
                    networkTask.execute();

                    Log.d(tag, String.valueOf(ListObject.getInt("comment_num")));
                    Log.d(tag, ListObject.getString("feed_num"));
                    Log.d(tag, ListObject.getString("member_id"));
                    Log.d(tag, ListObject.getString("comment_content"));
                    Log.d(tag, ListObject.getString("commment_date"));
                    Log.d(tag, String.valueOf(ListObject.getInt("commment_like_count")));

                    if (feed_map.get("num").equals(ListObject.getString("feed_num"))) {
                        FeedCommentItem commentItem = new FeedCommentItem(ListObject.getInt("comment_num"), String.valueOf(ListObject.getString("member_id")), ListObject.getString("comment_content"), ListObject.getString("commment_date"), ListObject.getInt("commment_like_count"), ListObject.getInt("commment_like_exist"), user_pofile_url, user_nickname);

                        feedcommentAdapter.addItem(commentItem);

                        feedcommentAdapter.notifyDataSetChanged();
                        feed_comment_list.setAdapter(feedcommentAdapter);

                        Log.d("lecture", "리스트 갯수 " + String.valueOf(feedcommentAdapter.getCount()));
                    }
                    // 생성
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
                        user_pofile_url = ListObject.getString("member_profile_image");
                        user_nickname = ListObject.getString("member_nickname");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

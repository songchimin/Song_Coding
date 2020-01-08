package com.study.android.mytry.mypage.setup;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.study.android.mytry.R;
import com.study.android.mytry.challenge_private.CreationFifth;
import com.study.android.mytry.login.RequestHttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.study.android.mytry.login.Intro.local_url;

public class ComplaintDetail extends AppCompatActivity {

    TextView comment_id;
    TextView comment_date;
    TextView comment_content;
    TextView Complaint_content;
    TextView reply_date;
    TextView reply_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_complaint_detail);

        comment_id = findViewById(R.id.complaint_content_userid);
        comment_date = findViewById(R.id.complaint_comment_date);
        comment_content = findViewById(R.id.comment_content);
        Complaint_content = findViewById(R.id.complaint_content);
        reply_date = findViewById(R.id.complaint_reply_date);
        reply_content = findViewById(R.id.complaint_reply_content);

        Intent intent = getIntent();
        int comment_num = Integer.parseInt(intent.getExtras().getString("comment_num"));

        String urlString = local_url + "/yejin/mypage_comment_complaint";
        urlString = urlString + "?commentNum=" + comment_num;
        Log.d("lecture", urlString);

        CommentNetworkTask networkTask = new CommentNetworkTask(urlString, null);
        networkTask.execute();

        Complaint_content.setText(intent.getExtras().getString("content"));
        reply_date.setText(intent.getExtras().getString("reply_date"));
        reply_content.setText(intent.getExtras().getString("reply"));
    }

    // 내 메모 추가
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
                if (json != null) {
                    System.out.println(json);

                    JSONObject jsonObject = new JSONObject(json);

                    JSONArray CommentList = jsonObject.getJSONArray("List");


                    for (int i = 0; i < CommentList.length(); i++) {

                        JSONObject ListObject = CommentList.getJSONObject(i);

                        Log.d("lecture", String.valueOf(ListObject.getInt("comment_num")));
                        Log.d("lecture", String.valueOf(ListObject.getInt("challenge_num")));
                        Log.d("lecture", ListObject.getString("member_id"));
                        Log.d("lecture", ListObject.getString("comment_content"));

                        comment_id.setText(ListObject.getString("member_id"));
                        comment_date.setText(ListObject.getString("comment_date"));
                        comment_content.setText(ListObject.getString("comment_content"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

package com.study.android.mytry.mypage.setup;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.study.android.mytry.R;
import com.study.android.mytry.challenge_public.FileLoadConnection;
import com.study.android.mytry.login.RequestHttpConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.study.android.mytry.login.Intro.local_url;

public class QnAResult extends AppCompatActivity {

    TextView question_date;
    TextView question_content;
    TextView manager_id;
    TextView reply_date;
    TextView reply_content;
    ImageView question_Img;
    ImageView user_Img;
    ImageView manager_Img;

    HashMap<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_setup_qna_content);

        Intent intent = getIntent();
        map = (HashMap<String, String>) intent.getSerializableExtra("hashmap");

        question_date = findViewById(R.id.qna_question_date);
        question_content = findViewById(R.id.qna_question_content);
        manager_id = findViewById(R.id.qna_content_managerid);
        reply_date = findViewById(R.id.qna_reply_date);
        reply_content = findViewById(R.id.qna_reply_content);
        question_Img = findViewById(R.id.qna_image);
        user_Img = findViewById(R.id.qna_user_profile);
        manager_Img = findViewById(R.id.qna_manager_profile);

        question_date.setText(map.get("question_date"));
        question_content.setText(map.get("question_Content"));

        if (map.get("manager_id").equals("null")) {
            manager_id.setText(map.get(""));
            manager_Img.setVisibility(View.GONE);
        } else {
            manager_id.setText(map.get("manager_id"));
        }

        if (map.get("answer_date").equals("null")) {
            reply_date.setText(map.get(""));
        } else {
            reply_date.setText(map.get("answer_date"));
        }

        if (map.get("question_answer").equals("null")) {
            reply_content.setText(map.get(""));
        } else {
            reply_content.setText(map.get("question_answer"));
        }

        if (map.get("question_Picture").equals("null")) {
            question_Img.setVisibility(View.GONE);
        } else {
            String imageurl = local_url + "/";
            imageurl = imageurl + map.get("question_Picture") + ".jpg";

            FileUploadNetworkTask fileUploadNetworkTask = new FileUploadNetworkTask(imageurl, null);
            fileUploadNetworkTask.execute();
        }

        String url = local_url + "/yejin/profile_Image";
        String msg = "?id=" +map.get("member_id").toString();
        url = url+msg;

        Log.d("lecture", url);
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

    }

    public class FileUploadNetworkTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ContentValues values;

        public FileUploadNetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap result; // 요청 결과를 저장할 변수.
            FileLoadConnection requestHttpURLConnection = new FileLoadConnection();
            result = requestHttpURLConnection.request(url); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(Bitmap json) {
            super.onPostExecute(json);
            question_Img.setImageBitmap(json);

        }
    }

    public class ProfileLoadNetworkTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ContentValues values;

        public ProfileLoadNetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap result; // 요청 결과를 저장할 변수.
            FileLoadConnection requestHttpURLConnection = new FileLoadConnection();
            result = requestHttpURLConnection.request(url); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(Bitmap json) {
            super.onPostExecute(json);
            user_Img.setImageBitmap(json);
            user_Img.setBackground(new ShapeDrawable(new OvalShape()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                user_Img.setClipToOutline(true);
            }
        }
    }

    // 통신
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            Log.d("done_money1231", "통과통과4");
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
            try {
                if(s != null) {
                    JSONObject json = new JSONObject(s);

                    String imageurl2 = local_url + "/";
                    imageurl2 = imageurl2 +  json.getString("profileImg") + ".jpg";

                    ProfileLoadNetworkTask profileLoadNetworkTask = new ProfileLoadNetworkTask(imageurl2, null);
                    profileLoadNetworkTask.execute();

                }
            }   catch (JSONException e) {
                Log.d("eeeeeeettext", "");
                e.printStackTrace();
            }
        }
    }


}

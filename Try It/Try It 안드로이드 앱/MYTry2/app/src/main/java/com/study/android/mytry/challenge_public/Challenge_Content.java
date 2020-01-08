package com.study.android.mytry.challenge_public;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.study.android.mytry.challenge_public.CreationMain.challenge_listView;
import static com.study.android.mytry.login.Intro.local_url;

public class Challenge_Content extends AppCompatActivity {

    Button back_pressed;
    TextView title;
    TextView category;
    TextView detail;

    Button comment_insert;
    EditText comment_text;
    LinearLayout votebtn;
    ImageView likeImg;
    TextView likeCount;

    public static ListView comment_listView;
    CommentAdapter commentAdapter;

    CommentNetworkTask networkTask;

    String url;
    String userid;
    HashMap<String, String> map;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_public_content);


        GlobalApplication myApp = (GlobalApplication) getApplicationContext();
        userid = myApp.getGlobalString();

        Intent intent = getIntent();
        map = (HashMap<String, String>) intent.getSerializableExtra("hashmap");

        back_pressed = (Button) findViewById(R.id.public_content_back);
        back_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        builder= new AlertDialog.Builder(Challenge_Content.this);

        title = (TextView) findViewById(R.id.public_content_name);
        category = (TextView) findViewById(R.id.public_content_category);
        detail = (TextView) findViewById(R.id.public_content_detail);
        likeCount = (TextView) findViewById(R.id.public_content_count);
        likeImg = (ImageView) findViewById(R.id.public_content_image);
        votebtn = (LinearLayout) findViewById(R.id.public_content_good);

        title.setText(map.get("title"));
        category.setText(map.get("category"));
        detail.setText(map.get("detail"));

        likeCount.setText(map.get("like_count") + " 개");

        if (map.get("like_exist").equals("1")) {

            votebtn.setBackgroundColor(Color.rgb(255, 94, 94));
            likeImg.setBackgroundResource(R.drawable.challenge_like_false);
            likeCount.setTextColor(Color.WHITE);
        } else if (map.get("like_exist").equals("0")) {

            votebtn.setBackgroundColor(Color.rgb(255, 255, 255));
            likeImg.setBackgroundResource(R.drawable.challenge_like_true);
            likeCount.setTextColor(Color.rgb(255, 94, 94));
        }

        votebtn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String url = local_url + "/yejin/public_challenge_like";

                String msg = "?challenge_num=" + map.get("num") +
                        "&member_id=" + userid;
                url = url + msg;

                VoteNetworkTask networkTask = new VoteNetworkTask(url, null);
                networkTask.execute();
            }
        });

        url = local_url + "/yejin/comment_list?member_id="+userid;


        Log.d("lecture", url);

        comment_text = (EditText) findViewById(R.id.public_comment_text);
        comment_listView = findViewById(R.id.content_comment_list);
        commentAdapter = new CommentAdapter(Challenge_Content.this);

        networkTask = new CommentNetworkTask(url, null);
        networkTask.execute();

        comment_insert = (Button) findViewById(R.id.public_comment_btn);
        comment_insert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                url = local_url + "/yejin/comment_insert";
                String msg = "?challenge_num=" + map.get("num") +
                        "&member_id=" + userid +
                        "&content=" + comment_text.getText().toString();

                Log.d("lecture", msg);
                url = url + msg;

                Log.d("lecture", url);

                networkTask = new CommentNetworkTask(url, null);
                networkTask.execute();
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                Toast.makeText(getApplicationContext(), "삭제", Toast.LENGTH_LONG).show();
                break;
            case R.id.complaint:
                Toast.makeText(getApplicationContext(), "신고", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
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
            // textView.setText(json);
            try {
                JSONObject jsonObject = new JSONObject(json);

                JSONArray ListArray = jsonObject.getJSONArray("List");

                Log.d("lecture", "리스트 갯수  " + String.valueOf(ListArray.length()));

                Log.d("lecture", "commentAdapter 갯수  " + String.valueOf(commentAdapter.getCount()));
                Log.d("lecture", map.get("num"));

                int count = 0;

                for (int i = 0; i < ListArray.length(); i++) {

                    JSONObject ListObject = ListArray.getJSONObject(i);

                    if (map.get("num").equals(String.valueOf(ListObject.getInt("challenge_num")))) {
                        count++;
                    }
                }

                // 생성
                if (count < commentAdapter.getCount() || commentAdapter.getCount() == 0) {
                    CommentItem[] commentItems = new CommentItem[ListArray.length()];

                    for (int i = 0; i < ListArray.length(); i++) {

                        JSONObject ListObject = ListArray.getJSONObject(i);

                        Log.d("lecture", String.valueOf(ListObject.getInt("comment_num")));
                        Log.d("lecture", ListObject.getString("challenge_num"));
                        Log.d("lecture", ListObject.getString("member_id"));
                        Log.d("lecture", ListObject.getString("comment_content"));
                        Log.d("lecture", ListObject.getString("commment_date"));
                        Log.d("lecture", String.valueOf(ListObject.getInt("commment_like_count")));
                        Log.d("lecture", String.valueOf("exist" + ListObject.getInt("commment_like_exist")));

                        if (map.get("num").equals(String.valueOf(ListObject.getInt("challenge_num")))) {
                            commentItems[i] = new CommentItem(ListObject.getInt("comment_num"), ListObject.getInt("challenge_num"), String.valueOf(ListObject.getString("member_id")), ListObject.getString("comment_content"), ListObject.getString("commment_date"), ListObject.getInt("commment_like_count"), ListObject.getInt("commment_like_exist"));

                            commentAdapter.addItem(commentItems[i]);
                        }
                    }
                    comment_listView.setAdapter(commentAdapter);
                    Log.d("lecture", "commentAdapter 갯수 " + String.valueOf(commentAdapter.getCount()));

                } else {
                    JSONObject ListObject = ListArray.getJSONObject(ListArray.length() - 1);

                    Log.d("lecture", String.valueOf(ListObject.getInt("comment_num")));
                    Log.d("lecture", ListObject.getString("challenge_num"));
                    Log.d("lecture", ListObject.getString("member_id"));
                    Log.d("lecture", ListObject.getString("comment_content"));
                    Log.d("lecture", ListObject.getString("commment_date"));
                    Log.d("lecture", String.valueOf(ListObject.getInt("commment_like_count")));

                    if (map.get("num").equals(ListObject.getString("challenge_num"))) {
                        CommentItem commentItem = new CommentItem(ListObject.getInt("comment_num"), ListObject.getInt("challenge_num"), String.valueOf(ListObject.getString("member_id")), ListObject.getString("comment_content"), ListObject.getString("commment_date"), ListObject.getInt("commment_like_count"), ListObject.getInt("commment_like_exist"));

                        commentAdapter.addItem(commentItem);

                        commentAdapter.notifyDataSetChanged();
                        comment_listView.setAdapter(commentAdapter);

                        Log.d("lecture", "리스트 갯수 " + String.valueOf(commentAdapter.getCount()));
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class VoteNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public VoteNetworkTask(String url, ContentValues values) {

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
            JSONObject jsonObject = null;
            try {
                PublicAdapter publicAdapter = new PublicAdapter(getApplicationContext());

                jsonObject = new JSONObject(json);

                JSONArray ListArray = jsonObject.getJSONArray("List");

                PublicItem[] publicItems = new PublicItem[ListArray.length()];

                for (int i = 0; i < ListArray.length(); i++) {

                    JSONObject ListObject = ListArray.getJSONObject(i);

                    publicItems[i] = new PublicItem(ListObject.getInt("challenge_num"),
                            ListObject.getString("challenge_title"),
                            ListObject.getString("challenge_type"),
                            ListObject.getInt("challenge_like_count"),
                            ListObject.getInt("challenge_like_exist"));

                    publicAdapter.addItem(publicItems[i]);

                    Log.d("lecture", ListObject.getString("challenge_num"));
                    Log.d("lecture", ListObject.getString("challenge_title"));
                    Log.d("lecture", ListObject.getString("challenge_category"));
                    Log.d("lecture", ListObject.getString("challenge_type"));
                    Log.d("lecture", ListObject.getString("challenge_detail"));

                    if (Integer.parseInt(map.get("num")) == ListObject.getInt("challenge_num")) {

                        votebtn.setBackgroundColor(Color.rgb(255, 94, 94));
                        likeImg.setBackgroundResource(R.drawable.challenge_like_false);
                        likeCount.setTextColor(Color.WHITE);

                        if (ListObject.getInt("challenge_like_exist") == 1) {
                            builder.setTitle("좋아요");

                            builder.setMessage("해당 챌린지 개설 완료시 알림이 갑니다.\n" +
                                    "앱 내 푸쉬설정, 휴대폰 푸쉬설정이 전부 켜져있어야 알림 받을 수 있습니다.")
                                    .setCancelable(false)
                                    .setPositiveButton("확인",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(
                                                        DialogInterface dialog, int id) {
                                                }
                                            });


                        } else if (ListObject.getInt("challenge_like_exist") == 0) {

                            votebtn.setBackgroundColor(Color.rgb(255, 255, 255));
                            likeImg.setBackgroundResource(R.drawable.challenge_like_true);
                            //  like_btn.setBackgroundResource(R.drawable.xml_public_stroke);
                            likeCount.setTextColor(Color.rgb(255, 94, 94));

                            builder.setTitle("좋아요 취소");

                            builder.setMessage("챌린지 주제에 대한 좋아요가 취소되었습니다.\n" +
                                    "챌린지 개설에 대한 알림을 원하실 경우 다시 좋아요를 눌러주세요.")
                                    .setCancelable(false)
                                    .setPositiveButton("확인",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(
                                                        DialogInterface dialog, int id) {
                                                }
                                            });
                        }

                        AlertDialog dialog = builder.create();
                        dialog.show();

                        likeCount.setText(String.valueOf(ListObject.getInt("challenge_like_count")) + "개");
                    }
                    challenge_listView.setAdapter(publicAdapter);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

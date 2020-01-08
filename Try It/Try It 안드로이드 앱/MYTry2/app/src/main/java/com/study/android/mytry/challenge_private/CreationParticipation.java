package com.study.android.mytry.challenge_private;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.study.android.mytry.R;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.search.Search_detail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.study.android.mytry.login.Intro.local_url;

public class CreationParticipation extends AppCompatActivity {

    EditText searchEdit;
    Button searchBtn;

    TextView searchResult;
    Button participateBtn;

    HashMap<String, String> hashMap= new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_private_participation);

        searchEdit = findViewById(R.id.private_search_editText);
        searchBtn = findViewById(R.id.private_search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/yejin/private_searchcode";
                String msg = "?code=" + searchEdit.getText();
                Log.d("lecture", msg);
                url = url + msg;
                Log.d("lecture", url);

                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();
            }
        });
        searchResult = findViewById(R.id.private_search_result);
        participateBtn = findViewById(R.id.private_challenge_part);
        participateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Search_detail.class);

                intent.putExtra("challenge_num",hashMap.get("num"));
                intent.putExtra("challenge_title",hashMap.get("title"));
                intent.putExtra("challenge_category",hashMap.get("category"));
                intent.putExtra("challenge_type",hashMap.get("type"));
                intent.putExtra("challenge_frequency",hashMap.get("frequency"));
                intent.putExtra("challenge_start",hashMap.get("start"));
                intent.putExtra("challenge_end",hashMap.get("end"));
                intent.putExtra("challenge_fee",hashMap.get("fee"));
                intent.putExtra("challenge_time",hashMap.get("time"));
                intent.putExtra("challenge_detail",hashMap.get("detail"));
                intent.putExtra("challenge_first_image",hashMap.get("first_image"));
                intent.putExtra("challenge_state",hashMap.get("state"));
                intent.putExtra("challenge_public",hashMap.get("public"));
                intent.putExtra("challenge_exp",hashMap.get("exp"));
                intent.putExtra("challenge_host",hashMap.get("host"));


                startActivity(intent);
            }
        });

    }

    public void onBackPressed(View v) {
        finish();
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
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            try {
                System.out.println(json);
                JSONObject jsonObject = new JSONObject(json);

                JSONArray ListArray = jsonObject.getJSONArray("List");

                for (int i = 0; i < ListArray.length(); i++) {

                    JSONObject ListObject = ListArray.getJSONObject(i);

                    String result = "트라잇 이름 : " + ListObject.getString("challenge_title") + "\n" +
                            "개설자 : " + ListObject.getString("challenge_host");
                    searchResult.setText(result);

                    hashMap.put("num", ListObject.getString("challenge_num"));
                    hashMap.put("title", ListObject.getString("challenge_title"));
                    hashMap.put("category", ListObject.getString("challenge_category"));
                    hashMap.put("type", ListObject.getString("challenge_type"));
                    hashMap.put("frequency", ListObject.getString("challenge_frequency"));
                    hashMap.put("start", ListObject.getString("challenge_start"));
                    hashMap.put("end", ListObject.getString("challenge_end"));
                    hashMap.put("fee", ListObject.getString("challenge_fee"));
                    hashMap.put("time", ListObject.getString("challenge_time"));
                    hashMap.put("detail", ListObject.getString("challenge_detail"));
                    hashMap.put("first_image", ListObject.getString("challenge_first_image"));
                    hashMap.put("public", ListObject.getString("challenge_public"));
                    hashMap.put("state", ListObject.getString("challenge_state"));
                    hashMap.put("exp", ListObject.getString("challenge_exp"));
                    hashMap.put("host", ListObject.getString("challenge_host"));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

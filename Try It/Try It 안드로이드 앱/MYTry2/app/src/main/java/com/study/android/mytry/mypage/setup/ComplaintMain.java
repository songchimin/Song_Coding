package com.study.android.mytry.mypage.setup;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.study.android.mytry.R;
import com.study.android.mytry.challenge_public.ChallengeDto;
import com.study.android.mytry.challenge_public.PublicItem;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.study.android.mytry.login.Intro.local_url;

public class ComplaintMain extends AppCompatActivity {

    ListView complaintList;
    ComplaintAdapter complaintAdapter;

    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_complaint_main);

        complaintList = findViewById(R.id.complaint_list);

        GlobalApplication myApp = (GlobalApplication) getApplicationContext();
        userid = myApp.getGlobalString();

        complaintAdapter = new ComplaintAdapter(getApplicationContext());

        String url = local_url + "/yejin/mypage_complaint_list?" +
                "id=" + userid;

        Log.d("lecture", url);

        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();
    }

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
                if(json != null) {
                    System.out.println(json);
                    JSONObject jsonObject = new JSONObject(json);

                    JSONArray ListArray = jsonObject.getJSONArray("List");

                    ComplaintItem[] complaintItems = new ComplaintItem[ListArray.length()];

                    for (int i = 0; i < ListArray.length(); i++) {

                        JSONObject ListObject = ListArray.getJSONObject(i);



                        complaintItems[i] = new ComplaintItem(ListObject.getInt("complaint_num"),
                                ListObject.getInt("challenge_num"),
                                ListObject.getInt("comment_num"),
                                ListObject.getString("complaint_content"),
                                ListObject.getString("complaint_state"),
                                ListObject.getString("complaint_reply"),
                                "",
                                ListObject.getString("complaint_date"));

                        complaintAdapter.addItem(complaintItems[i]);
                        System.out.println(complaintItems[i]);
                    }

                    complaintList.setAdapter(complaintAdapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

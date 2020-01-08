package com.study.android.mytry.chat;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.study.android.mytry.R;
import com.study.android.mytry.login.Login;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.main.Fragment_Search;
import com.study.android.mytry.mypage.setup.SetupMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.study.android.mytry.login.Intro.local_url;

public class chat_room extends AppCompatActivity {
    private static final String TAG = "song";
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String user_email;

    Button chat_room_back_btn;
    ListView chat_room_list;

    chat_room_singerAdapter adapter;

    public static String nickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room_activity);

        chat_room_back_btn = findViewById(R.id.chat_room_back_btn);
        chat_room_list = findViewById(R.id.chat_room_list);

        adapter = new chat_room_singerAdapter(chat_room.this);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        user_email = user.getEmail();


        String id = user_email;
        String url = local_url+"/chat_room_list";
        String msg = "?id="+id;
        Log.d("lecture", msg);
        url = url +msg;
        Log.d("lecture", url);

        NetworkTask networkTask = new NetworkTask(url, null,1);
        networkTask.execute();


        url = local_url+"/member_nickname";
        msg = "?id="+id;
        Log.d("lecture", msg);
        url = url +msg;
        Log.d("lecture", url);

        networkTask = new NetworkTask(url, null,2);
        networkTask.execute();


        chat_room_back_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                finish();
                overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
            }
        });



    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;
        int num;

        public NetworkTask(String url, ContentValues values, int num) {

            this.url = url;
            this.values = values;
            this.num = num;
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

            if(num == 1){
                try {
                    if (json != null) {
                        JSONObject jsonObject = new JSONObject(json);
                        Log.d(TAG, "들어옴");
                        Log.d(TAG, json);
                        JSONArray ListArray = jsonObject.getJSONArray("AndroidChatList");

                        chat_room_singerItem[] singerItems = new chat_room_singerItem[ListArray.length()];

                        for (int i = 0; i < ListArray.length(); i++) {

                            JSONObject ListObject = ListArray.getJSONObject(i);

                            singerItems[i] =
                                    new chat_room_singerItem(
                                            String.valueOf(ListObject.getInt("chat_room_num")),
                                            ListObject.getString("chat_room_image"),
                                            ListObject.getString("chat_room_title"),
                                            ListObject.getString("chat_room_participant_count"));

                            adapter.addItem(singerItems[i]);
                            Log.d("TAG", i+"");

                        }
                        chat_room_list.setAdapter(adapter);
//                    Log.d("ssssssettext2", "들어옴222");

                    } else {
//                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    View view = inflater.inflate(R.layout.search_main_item, null);

//                    TextView challenge_name =  findViewById(R.id.challenge_name1);
//                    TextView challenge_start =  findViewById(R.id.challenge_start1);
//                    TextView challenge_end = findViewById(R.id.challenge_end1);
//
//                    challenge_name.setText("");
//                    challenge_start.setText("");
//                    challenge_end.setText("");

                        Log.d("ssssssettext2", "null임");

                    }

                } catch (JSONException e) {
                    Log.d("eeeeeeettext", "");
                    e.printStackTrace();
                }
            }else{

                try {
                    if (json != null) {
                        JSONObject jsonObject = new JSONObject(json);
                        Log.d(TAG, "들어옴");
                        Log.d(TAG, json);
                        JSONArray ListArray = jsonObject.getJSONArray("nickname");

                        JSONObject ListObject = ListArray.getJSONObject(0);

                        Log.d("songsong", ListObject.getString("nickname"));

                        nickname = ListObject.getString("nickname");

                    } else {

                        Log.d("ssssssettext2", "null임");

                    }

                } catch (JSONException e) {
                    Log.d("eeeeeeettext", "");
                    e.printStackTrace();
                }

            }



           // chat_room_singerItem[] items = new chat_room_singerItem[1];
           // items[0] = new chat_room_singerItem("","일찍자기","최근 채팅 내용","최근채팅시간","안읽은갯수","참가자수");
            //adapter.addItem(items[0]);
            //chat_room_list.setAdapter(adapter);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            //   tv_outPut.setText(s);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.not_move_activity,R.anim.rightout_activity);
    }
}

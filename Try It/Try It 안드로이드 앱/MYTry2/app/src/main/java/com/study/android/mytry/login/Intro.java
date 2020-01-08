package com.study.android.mytry.login;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kakao.auth.Session;
import com.study.android.mytry.R;
import com.study.android.mytry.main.MainActivity;
import com.study.android.mytry.search.search_main;

import org.json.JSONException;
import org.json.JSONObject;

public class Intro extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String user_email;
    String regId;

    public static String local_url = "http://112.222.180.235:80";

    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {

            Intent intent = null;

            intent = getIntent();
            if(intent != null && intent.getExtras() != null){

                for(String key : getIntent().getExtras().keySet()){
                    String value = getIntent().getExtras().getString(key);
                    Log.d(TAG, "Noti - " + key + ":" + value);
                }

                Log.d(TAG, "알림 메시지가있어요");

                String contents = intent.getStringExtra("message");
                if(contents != null){
                    processIntent(contents);
                }
            }

            //로그인이 되어 있지 않았을 경우
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                intent = new Intent(Intro.this,search_main.class);
                startActivity(intent); // 다음화면으로 넘어가기
                finish();

                //로그인이 되었을 경우
            } else {

                firebaseAuth = FirebaseAuth.getInstance();
                user = firebaseAuth.getCurrentUser();
                user_email = user.getEmail();

                if(user_email !=null) {
                    GlobalApplication myApp = (GlobalApplication) getApplication();
                    myApp.setGlobalString(user_email);
                    Log.e("GlobalVariablesActivity", myApp.getGlobalString());

                }
                else{
                    if (Session.getCurrentSession().checkAndImplicitOpen()) {
                        Log.e("GlobalVariablesActivity", "카카오 자동로그인");
                    } else {
                        Log.e("GlobalVariablesActivity", "카카오 자동로그인 실패");
                    }
                }
                getRegistrationId();
                Toast.makeText(Intro.this, "자동로그인 되셨습니다.", Toast.LENGTH_LONG).show();
                // 여기서 알아야함
                intent = new Intent(Intro.this, MainActivity.class);
//                intent = new Intent(Intro.this,Logout.class);
                startActivity(intent); // 다음화면으로 넘어가기
                finish(); // Activity 화면 제거
            }
        }
    };

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                //데이터 받기
//                String result = data.getStringExtra("result");
//                if(result.equals("gologin")){
//
//                    Intent intent = new Intent(Intro.this,Login.class);
//                    startActivity(intent); // 다음화면으로 넘어가기
//
//                }else{
//
//                    Intent intent = new Intent(Intro.this,MainActivity.class);
//                    startActivity(intent); // 다음화면으로 넘어가기
//                }
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_intro); // xml과 java소스를 연결
    } // end of onCreate

    @Override
    protected void onResume() {
        super.onResume();
// 다시 화면에 들어어왔을 때 예약 걸어주기
        handler.postDelayed(r, 3000); // 4초 뒤에 Runnable 객체 수행
    }

    @Override
    protected void onPause() {
        super.onPause();
// 화면을 벗어나면, handler 에 예약해놓은 작업을 취소하자
        handler.removeCallbacks(r); // 예약 취소
    }

    public void getRegistrationId() {
        println("getRegistrationId() 호출됨.");

        regId = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "RegID:"+regId);
        println("regId : " + regId);

      //  FirebaseMessaging.getInstance().subscribeToTopic("1");
       FirebaseMessaging.getInstance().subscribeToTopic("all");
        //FirebaseMessaging.getInstance().unsubscribeFromTopic("1");

        String id = user_email;
        String url = local_url+"/token_save";
        String msg = "?id="+id+"&token="+regId;
        Log.d("lecture", msg);
        url = url +msg;
        Log.d("lecture", url);

        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();
    }

    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        println("onNewIntent() called.");

        if(intent != null){
            String contents = intent.getStringExtra("message");
            processIntent(contents);
        }
    }

    private void processIntent(String contents){
        println("DATA : " + contents);
    }

    public void println(String data){

//        log.append(data + "\n");
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

            return "1";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


        }
    }
}
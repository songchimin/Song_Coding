package com.study.android.mytry.certification;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.study.android.mytry.login.Intro.local_url;

public class Certificate_map_comment extends AppCompatActivity {
    private static final String tag="certificate";

    TextView challenge_title;
    EditText comment;
    Button submit_btn;
    String userid;
    String challenge_num;
    String url;
    TextView running_time;
    TextView running_distance;
    TextView end_time;
    TextView start_time;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certificate_map_comment);

        GlobalApplication myApp = (GlobalApplication) getApplicationContext();
        userid = myApp.getGlobalString();

        challenge_title = findViewById(R.id.certificate_title);
        running_time = (TextView) findViewById(R.id.map_temp_running_time);
        running_distance = (TextView) findViewById(R.id.map_temp_running_distance);
//        start_time = (TextView) findViewById(R.id.map_temp_start_time);
//        end_time = (TextView) findViewById(R.id.map_temp_end_time);

        Intent intent = getIntent();
        challenge_title.setText(intent.getExtras().getString("challenge_title"));
        challenge_num = intent.getExtras().getString("challenge_num");
        running_time.setText(intent.getExtras().getString("running_time"));
        running_distance.setText(intent.getExtras().getString("running_distance"));
//        start_time.setText(intent.getExtras().getString("start_time"));
//        end_time.setText(intent.getExtras().getString("end_time"));

        location = intent.getExtras().getString("location");

        comment = (EditText)findViewById(R.id.certification_map_comment_editText);
        submit_btn = (Button)findViewById(R.id.certification_map_comment_submit_btn);

        Log.d(tag, "useriddddddddddddddddddddd"+userid);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadClicked();
            }
        });
    }

    // 사진을 정방향대로 회전하기
    private Bitmap rotate(Bitmap src, float degree) {
        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    public void uploadClicked(){

        url = local_url + "/selee/feed_insert";

        String msg = "?member_id="+userid+
                "&challenge_num="+challenge_num+
                "&challenge_title="+challenge_title.getText().toString()+
                "&feed_comment="+comment.getText().toString()+
                "&feed_info=map";

        url = url +msg;
        Log.d("certificate", url);

        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        Toast.makeText(Certificate_map_comment.this,"성공적으로 인증을 마쳤습니다.",Toast.LENGTH_LONG).show();
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
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}

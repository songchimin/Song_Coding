package com.study.android.mytry.mypage.setup;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;

import static com.study.android.mytry.login.Intro.local_url;

public class ComplaintDialog extends Dialog {

    private Context context;
    EditText content;
    Button sendBtn;

    String userid;
    int comment_num;

    public ComplaintDialog(Context context, int comment_num) {
        super(context);
        this.context = context;
        this.comment_num=comment_num;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint_dialog);

        GlobalApplication myApp = (GlobalApplication) context.getApplicationContext();
        userid = myApp.getGlobalString();

        content = (EditText) findViewById(R.id.complaint_content);
        sendBtn = findViewById(R.id.complaint_dialog_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 신고내용 insert
                String url = local_url + "/yejin/mypage_complaint_comment";
                url = url + "?comment_num=" +comment_num+
                            "&id="+userid+
                            "&content="+content.getText();
                Log.d("lecture", url);

                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();

                Toast.makeText(context, "신고가 완료되었습니다.", Toast.LENGTH_SHORT);

                dismiss();
            }
        });
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
            // textView.setText(json);
            if(json != null){

            }
        }
    }

}

package com.study.android.mytry.mypage;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.main.MainActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.study.android.mytry.login.Intro.local_url;

public class MemoInsert extends AppCompatActivity {

    EditText textTitle;
    EditText textContent;
    Button sendBtn;

    String userid;

    DatePicker mDate;
    String memoDate;
    final Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_memo_write);

        GlobalApplication myApp = (GlobalApplication) getApplicationContext();
        userid = myApp.getGlobalString();

        textTitle = findViewById(R.id.memo_title);
        textContent = findViewById(R.id.memo_content);

        mDate = (DatePicker) findViewById(R.id.date_picker_dialog);
        mDate.init(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(),

                new DatePicker.OnDateChangedListener() {



                    //값이 바뀔때마다 텍스트뷰의 값을 바꿔준다.

                    @Override

                    public void onDateChanged(DatePicker view, int year, int monthOfYear,

                                              int dayOfMonth) {
//                        mTxtDate.setText(String.format("%d/%d/%d", year,monthOfYear + 1
//                                , dayOfMonth));
                    }

                });

        sendBtn = findViewById(R.id.memo_send);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = String.format("%d/%d/%d", mDate.getYear(),

                        mDate.getMonth() + 1, mDate.getDayOfMonth());

                Toast.makeText(MemoInsert.this, result, Toast.LENGTH_SHORT).show();

                String url = local_url + "/yejin/mypage_memo_insert";
                url = url + "?member_id=" + userid +
                        "&memo_title="+ textTitle.getText().toString()+
                        "&memo_content="+textContent.getText().toString()+
                        "&memo_date="+result;
                Log.d("lecture", url);

                MemoNetworkTask memoNetworkTask = new MemoNetworkTask(url, null);
                memoNetworkTask.execute();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    // 내 메모 추가
    public class MemoNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public MemoNetworkTask(String url, ContentValues values) {

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

        }
    }
}

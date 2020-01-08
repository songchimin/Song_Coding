package com.study.android.mytry.mypage.setup;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;

import org.json.JSONException;
import org.json.JSONObject;

import static com.study.android.mytry.login.Intro.local_url;

public class MypageBank extends AppCompatActivity {

    Spinner bank;
    EditText account;
    EditText name;
    Button send;

    String userid;
    String bankName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_setup_bank);

        GlobalApplication myApp = (GlobalApplication) getApplicationContext();
        userid = myApp.getGlobalString();

        bank = findViewById(R.id.bank_spinner);
        account = findViewById(R.id.bank_account);
        name = findViewById(R.id.bank_name);

        bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bankName = (String) bank.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        send = findViewById(R.id.bank_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = local_url + "/yejin/mypage_account?" +
                        "id=" + userid +
                        "&bank=" + bankName +
                        "&account=" + account.getText() +
                        "&holder=" + name.getText();

                Log.d("lecture", url);

                BankNetworkTask bankNetworkTask = new BankNetworkTask(url, null);
                bankNetworkTask.execute();

                Toast.makeText(getApplicationContext(), "변경이 완료 되었습니다!", Toast.LENGTH_SHORT);

                finish();
            }
        });


        String url = local_url + "/yejin/mypage_account_list?" +
                "id=" + userid ;

        Log.d("lecture", url);

        BankNetworkTask bankNetworkTask = new BankNetworkTask(url, null);
        bankNetworkTask.execute();

    }


    // 내 메모 추가
    public class BankNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public BankNetworkTask(String url, ContentValues values) {

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
            System.out.println(s);
            if (s != null) {
                try {
                    JSONObject json = new JSONObject(s);

                    String[] array = json.getString("accountList").split(",");
                    if(array.length > 1) {

                            bank.setPrompt(array[0]);
                            account.setText(array[1]);
                            name.setText(array[2]);
                        }
                }   catch (JSONException e) {
                    Log.d("eeeeeeettext", "");
                    e.printStackTrace();
                }

            }

        }
    }
}

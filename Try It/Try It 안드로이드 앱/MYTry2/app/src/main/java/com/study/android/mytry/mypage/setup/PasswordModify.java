package com.study.android.mytry.mypage.setup;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.study.android.mytry.R;
import com.study.android.mytry.login.RequestHttpConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.study.android.mytry.login.Intro.local_url;

public class PasswordModify extends AppCompatActivity {

    TextInputLayout nowLayout;
    TextInputLayout newLayout;
    TextInputLayout checkLayout;

    EditText nowEdit;
    EditText newEdit;
    EditText checkEdit;

    Button modify_send;


    HashMap<String, String> setup_map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_password_modify);

        Intent intent = getIntent();
        setup_map = (HashMap<String, String>) intent.getSerializableExtra("hashmap");

        nowLayout = findViewById(R.id.nowInputLayout);
        // nowPw.setErrorTextColor();
        nowLayout.setCounterMaxLength(30);

        newLayout = findViewById(R.id.newInputLayout);
        newLayout.setCounterMaxLength(30);

        checkLayout = findViewById(R.id.oldInputLayout);
        checkLayout.setCounterMaxLength(30);

        nowEdit = findViewById(R.id.nowEditText);
        newEdit = findViewById(R.id.newEditText);
        checkEdit = findViewById(R.id.checkEditText);

        newEdit.addTextChangedListener(pwTextWatcher);
        checkEdit.addTextChangedListener(checkTextWatcher);

        modify_send = findViewById(R.id.nickname_modify_btn);
        modify_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 통신

                String url = local_url + "/yejin/mypage_modify_password?" +
                        "id=" + setup_map.get("id") +
                        "&pw=" + setup_map.get("pw");


                ProfileNetworkTask profileNetworkTask = new ProfileNetworkTask(url, null);
                profileNetworkTask.execute();

                Toast.makeText(getApplicationContext(), "비밀번호가 변경되었습니다!", Toast.LENGTH_SHORT);
                finish();

            }
        });

    }

    TextWatcher pwTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() < 6) {
                newLayout.setError("비밀번호는 6글자 이상이어야 합니다.");
            } else {
                newLayout.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    TextWatcher checkTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() < 6) {
                checkLayout.setError("비밀번호는 6글자 이상이어야 합니다.");
            } else {
                checkLayout.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    // 내 메모 추가
    public class ProfileNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public ProfileNetworkTask(String url, ContentValues values) {

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
            if (json != null) {
            }
        }
    }

}

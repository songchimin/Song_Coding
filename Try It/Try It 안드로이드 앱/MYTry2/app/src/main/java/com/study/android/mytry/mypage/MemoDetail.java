package com.study.android.mytry.mypage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.study.android.mytry.R;

public class MemoDetail extends AppCompatActivity {

    TextView title;
    TextView content;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_memo_detail);

        title = findViewById(R.id.memo_detail_title);
        content = findViewById(R.id.memo_detail_content);
        date = findViewById(R.id.memo_detail_date);

        Intent intent = getIntent();
        title.setText(intent.getExtras().getString("title"));
        content.setText(intent.getExtras().getString("content"));
        date.setText(intent.getExtras().getString("date"));
    }

    public void onBack(View view){
        finish();
    }
}

package com.study.android.mytry.mypage.setup;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.mytry.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class QnAItemView extends LinearLayout {

    TextView title;
    TextView textDate;
    TextView state;

    public QnAItemView(Context context) {
        super(context);

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mypage_question_item, this, true);

        title = (TextView) findViewById(R.id.question_item_title);
        textDate = (TextView) findViewById(R.id.question_item_date);
        state = (TextView) findViewById(R.id.question_item_state);

    }

    public void setTitle(String str) {
        title.setText(str);
    }

    public void setDate(String str) {
        textDate.setText(str);
    }

    public void setState(String str) {
        state.setText(str);
    }

}
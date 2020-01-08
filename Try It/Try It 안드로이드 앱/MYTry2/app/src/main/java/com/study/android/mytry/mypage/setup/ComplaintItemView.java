package com.study.android.mytry.mypage.setup;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.mytry.R;

public class ComplaintItemView extends LinearLayout {

    TextView comment_title;
    TextView state;
    TextView date;

    public ComplaintItemView(Context context){
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mypage_complaint_item,this, true);

        comment_title = (TextView) findViewById(R.id.complaint_item_title);
        state = (TextView) findViewById(R.id.complaint_item_state);
        date = (TextView) findViewById(R.id.complaint_item_date);
    }

    public void setComment_title(String str){
        comment_title.setText(str);
    }
    public void setState(String str){
        state.setText(str);
    }
    public void setDate(String str){
        date.setText(str);
    }
}


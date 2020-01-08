package com.study.android.mytry.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.mytry.R;

public class MyChallengeView extends LinearLayout {

    TextView titleText;
    TextView categoryText;
    TextView dateText;
    TextView stateText;

    public MyChallengeView(Context context){
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mypage_challenge_item,this, true);

         titleText = findViewById(R.id.mypage_title);
         categoryText = findViewById(R.id.mypage_category);
         dateText = findViewById(R.id.mypage_date);
         stateText = findViewById(R.id.mypage_state);
    }


    public void setTitle(String str){
        titleText.setText(str);
    }
    public void setCategory(String str){
        categoryText.setText(str);
    }
    public void setDate(String str){
        dateText.setText(str);
    }
    public void setState(String str){
        stateText.setText(str);
    }

}

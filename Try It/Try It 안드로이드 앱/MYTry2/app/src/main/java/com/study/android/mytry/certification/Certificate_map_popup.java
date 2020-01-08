package com.study.android.mytry.certification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.study.android.mytry.R;

public class Certificate_map_popup extends Activity {
    TextView distance_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.certificate_map_popup);

        //데이터 가져오기
        Intent intent = getIntent();
        String distance = intent.getStringExtra("distance");
        Log.d("certificate", "(String)distance : "+distance);

        double remain;
        remain = 1000-Float.parseFloat(distance);

        distance_message = findViewById(R.id.distance_message);
        distance_message.setText(Math.round(remain*10)/10.0+"m 남았어요~");

    }

    Intent intent = new Intent();

    //이어서 츄라이~
    public void gologin(View v){
        intent.putExtra("check", "츄라이");
        setResult(RESULT_OK, intent);
        finish();
    }

    //그냥 나중에..
    public void golookaround(View v){
        intent.putExtra("check", "나중에");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }
}

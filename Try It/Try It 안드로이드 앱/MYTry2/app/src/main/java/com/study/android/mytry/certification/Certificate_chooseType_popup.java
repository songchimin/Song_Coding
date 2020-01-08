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

public class Certificate_chooseType_popup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.certificate_choose_type_popup);
    }

    Intent intent = new Intent();

    //카메라
    public void gologin2(View v){
        intent.putExtra("check", "카메라");
        setResult(RESULT_OK, intent);
        finish();
    }

    //갤러리
    public void golookaround2(View v){
        intent.putExtra("check", "갤러리");
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

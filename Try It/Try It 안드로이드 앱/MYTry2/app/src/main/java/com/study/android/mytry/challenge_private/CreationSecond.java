package com.study.android.mytry.challenge_private;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.study.android.mytry.R;
import java.util.HashMap;

import static com.study.android.mytry.main.Fragment_Search.Master_nickName;

public class CreationSecond extends AppCompatActivity {

    HashMap<String, String> map;

    CheckBox check_everyday;
    CheckBox check_weekday;
    CheckBox check_everyotherday;
    CheckBox check_weekend;

    public static CreationSecond creationSecond;
    private int CREATION_FINISH = 2;
    private static final int PRIVATE_CREATION_FINISH = 2;

    TextView Nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_private_second);


        Nickname = findViewById(R.id.textViewh17);
        Nickname.setText(Master_nickName+"님 만의");

        check_everyday = (CheckBox)findViewById(R.id.check_everyday);
        check_everyday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    check_weekday.setChecked(false);
                    check_everyotherday.setChecked(false);
                    check_weekend.setChecked(false);
                 }
            }
        });

        check_weekday = (CheckBox)findViewById(R.id.check_weekday);
        check_weekday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    check_everyday.setChecked(false);
                    check_everyotherday.setChecked(false);
                    check_weekend.setChecked(false);
                }
            }
        });

        check_everyotherday = (CheckBox)findViewById(R.id.check_everyotherday);
        check_everyotherday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    check_weekday.setChecked(false);
                    check_everyday.setChecked(false);
                    check_weekend.setChecked(false);
                }
            }
        });

        check_weekend = (CheckBox)findViewById(R.id.check_weekend);
        check_weekend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if(isChecked){
                    check_everyday.setChecked(false);
                    check_everyotherday.setChecked(false);
                    check_weekday.setChecked(false);
                }
            }
        });

        Intent intent=getIntent();
        map = (HashMap<String, String>) intent.getSerializableExtra("hashmap");

        creationSecond = CreationSecond.this;

        if (map.get("frequency") != null) {

            if(map.get("frequency").equals("매일")){
                check_everyday.setChecked(true);
            } else if(map.get("frequency").equals("주중")){
                check_weekday.setChecked(true);
            } else if( map.get("frequency").equals("월,수,금")){
                check_everyotherday.setChecked(true);
            } else if(map.get("frequency").equals("주말")){
                check_weekend.setChecked(true);
            } else {
            }
        }

    }


    public void onSecondNext(View v){
        Intent intent = new Intent(getApplicationContext(), CreationThrid.class);

        if(check_everyday.isChecked()){
            map.put("frequency","매일");
        } else if(check_weekday.isChecked()){
            map.put("frequency","주중");
        } else if( check_everyotherday.isChecked()){
            map.put("frequency","월,수,금");
        } else if(check_weekend.isChecked()){
            map.put("frequency","주말");
        } else {
        }

        intent.putExtra("hashmap",map);
        startActivityForResult(intent, CREATION_FINISH);
    }

    public void onBackPressed(View v){
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("yejin", String.valueOf(requestCode));
        if (requestCode == PRIVATE_CREATION_FINISH) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(CreationSecond.this, CreationFirst.class);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}

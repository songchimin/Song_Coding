package com.study.android.wifi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class region extends AppCompatActivity  {
    ArrayAdapter<CharSequence> dosi_Adapter;
    ArrayAdapter<CharSequence> sigungu_Adapter;
    ArrayAdapter<CharSequence> umd_Adapter; // 어댑터 선언
    String choice_do = "";
    String choice_se = "";
    String choice_dong = "";
    String user_name = "";

    EditText name;

    String input_do;
    String input_se;
    String input_dong;

    String ddo;
    String se;
    String dong;

//    static FirebaseFirestore db;

    FirebaseFirestore db;
    //firebase auth object
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        db=FirebaseFirestore.getInstance();

        name = findViewById(R.id.name);

        final Spinner dosi_Spinner = findViewById(R.id.dosi_edit);
        final Spinner sigungu_Spinner = findViewById(R.id.sigungu_edit);
        final Spinner umd_Spinner = findViewById(R.id.umd_edit);

        dosi_Adapter = ArrayAdapter.createFromResource(this, R.array.spinner_do, android.R.layout.simple_spinner_item);
        dosi_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dosi_Spinner.setAdapter(dosi_Adapter);


        dosi_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                ddo = String.valueOf(position);

                if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);
                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);
                } else if (parent.getItemAtPosition(position).equals("강원도")) {
                    choice_do = "강원도";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_kangwon, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);

                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);
                    choice_dong=" ";

                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            }else {
                                String item = parent.getItemAtPosition(position).toString();
                                System.out.println("item"+item);
                                choice_se = item;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (parent.getItemAtPosition(position).equals("서울특별시")) {
                    choice_do = "서울특별시";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_seoul, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);
                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);
                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            } else {
                                String item = parent.getItemAtPosition(position).toString();
                                System.out.println("position"+position);
                                choice_se = item;
                            }

                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            se = String.valueOf(position);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("종로구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_jongro, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("중구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_sjunggu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("용산구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_yongsan, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("성동구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_seongdong, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("광진구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_gwangjin, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("동대문구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_dongdaemun, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("중랑구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_jungrang, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("성북구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_seongbuk, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("강북구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_gangbuk, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("도봉구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_dobong, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("노원구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_noone, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("은평구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_enpyeong, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("서대문구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_seodaemun, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("마포구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_mapo, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("양천구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_yangcheon, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("강서구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_gangseo, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("구로구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_guro, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("금천구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_gumcheon, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("영등포구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_youngdengpo, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("동작구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_dongjak, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("관악구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_gwanak, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("서초구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_seocho, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("강남구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_gangnam, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("송파구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_songpa, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("강동구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_gangdong, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                } else if (parent.getItemAtPosition(position).equals("부산광역시")) {
                    choice_do = "부산광역시";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_busan, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);
                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);

                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            } else {
                                String item = parent.getItemAtPosition(position).toString();
                                choice_se = item;
                            }
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("중구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_bjunggu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("서구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_bseogu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("동구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_bdonggu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("영도구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_byoungdogu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("부산진구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_busanjingu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("동래구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_bdongrae, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("남구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_bnamgu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("북구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_bbukgu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("해운대구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_bhaeundae, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("사하구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_bsaha, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("금정구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_bgumjung, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("연제구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_byeonje, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("수영구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_bsuyoung, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("사상구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_bsasang, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("기장군")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_bgijang, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else if (parent.getItemAtPosition(position).equals("대구광역시")) {
                    choice_do = "대구광역시";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_daegu, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);
                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);

                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            } else {
                                String item = parent.getItemAtPosition(position).toString();
                                choice_se = item;
                            }
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("중구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_djunggu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("동구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_ddonggu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("서구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_dseogu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("남구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_dnamgu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("북구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_dbukgu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("수성구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_dsuseong, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("달서구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_ddalseogu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("달성군")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_ddalseonggun, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (parent.getItemAtPosition(position).equals("인천광역시")) {
                    choice_do = "인천광역시";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_incheon, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);
                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);

                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            } else {
                                String item = parent.getItemAtPosition(position).toString();
                                choice_se = item;
                            }
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("중구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_ijunggu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("동구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_idonggu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("남구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_inamgu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("연수구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_iyeonsugu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("남동구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_inamdonggu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("부평구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_ibupyeong, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("계양구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_igyeyang, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("서구")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_iseogu, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("강화군")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_iganghwa, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            } else if (parent.getItemAtPosition(position).equals("옹진군")) {
                                umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_umd_iongjin, android.R.layout.simple_spinner_item);
                                umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                umd_Spinner.setAdapter(umd_Adapter);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (parent.getItemAtPosition(position).equals("광주광역시")) {

                    choice_do = "광주광역시";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_gwangju, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);

                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);
                    choice_dong=" ";

                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            }else {
                                String item = parent.getItemAtPosition(position).toString();
                                System.out.println("item"+item);
                                choice_se = item;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (parent.getItemAtPosition(position).equals("대전광역시")) {
                    choice_do = "대전광역시";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_daejeon, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);

                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);
                    choice_dong=" ";

                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            }else {
                                String item = parent.getItemAtPosition(position).toString();
                                System.out.println("item"+item);
                                choice_se = item;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (parent.getItemAtPosition(position).equals("울산광역시")) {
                    choice_do = "울산광역시";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_ulsan, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);

                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);
                    choice_dong=" ";

                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            }else {
                                String item = parent.getItemAtPosition(position).toString();
                                System.out.println("item"+item);
                                choice_se = item;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (parent.getItemAtPosition(position).equals("세종특별자치시")) {
                    choice_do = "세종특별자치시";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);
                    choice_se=" ";
                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);
                    choice_dong=" ";
                } else if (parent.getItemAtPosition(position).equals("경기도")) {
                    choice_do = "경기도";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_gyeongi, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);

                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);
                    choice_dong=" ";

                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            }else {
                                String item = parent.getItemAtPosition(position).toString();
                                System.out.println("item"+item);
                                choice_se = item;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (parent.getItemAtPosition(position).equals("충청북도")) {
                    choice_do = "충청북도";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_chungbuk, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);

                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);
                    choice_dong=" ";

                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            }else {
                                String item = parent.getItemAtPosition(position).toString();
                                System.out.println("item"+item);
                                choice_se = item;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (parent.getItemAtPosition(position).equals("충청남도")) {
                    choice_do = "충청남도";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_chungnam, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);

                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);
                    choice_dong=" ";

                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            }else {
                                String item = parent.getItemAtPosition(position).toString();
                                System.out.println("item"+item);
                                choice_se = item;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else if (parent.getItemAtPosition(position).equals("전라북도")) {
                    choice_do = "전라북도";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_jeonbuk, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);

                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);
                    choice_dong=" ";

                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            }else {
                                String item = parent.getItemAtPosition(position).toString();
                                System.out.println("item"+item);
                                choice_se = item;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (parent.getItemAtPosition(position).equals("전라남도")) {
                    choice_do = "전라남도";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_jeonnam, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);

                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);
                    choice_dong=" ";

                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            }else {
                                String item = parent.getItemAtPosition(position).toString();
                                System.out.println("item"+item);
                                choice_se = item;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (parent.getItemAtPosition(position).equals("경상북도")) {
                    choice_do = "경상북도";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_gyeongbuk, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);

                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);
                    choice_dong=" ";

                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            }else {
                                String item = parent.getItemAtPosition(position).toString();
                                System.out.println("item"+item);
                                choice_se = item;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (parent.getItemAtPosition(position).equals("경상남도")) {
                    choice_do = "경상남도";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_gyeongnam, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);

                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);
                    choice_dong=" ";

                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            }else {
                                String item = parent.getItemAtPosition(position).toString();
                                System.out.println("item"+item);
                                choice_se = item;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else if (parent.getItemAtPosition(position).equals("제주특별자치도")) {
                    choice_do = "제주특별자치도";
                    sigungu_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_do_jeju, android.R.layout.simple_spinner_item);
                    sigungu_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sigungu_Spinner.setAdapter(sigungu_Adapter);

                    umd_Adapter = ArrayAdapter.createFromResource(region.this, R.array.spinner_null, android.R.layout.simple_spinner_item);
                    umd_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    umd_Spinner.setAdapter(umd_Adapter);
                    choice_dong=" ";

                    sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                            ((TextView) parent.getChildAt(0)).setTextSize(20);
                            parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                            if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                                choice_se = null;
                            }else {
                                String item = parent.getItemAtPosition(position).toString();
                                System.out.println("item"+item);
                                choice_se = item;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } else {
                    String item = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sigungu_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        umd_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                parent.getChildAt(0).setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                dong = String.valueOf(position);

                if (parent.getItemAtPosition(position).equals("선택해주세요")) {
                    choice_dong = null;
                }else {
                    String item = parent.getItemAtPosition(position).toString();
                    System.out.println("item"+item);
                    choice_dong = item;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button r_check = findViewById(R.id.region_check);

        r_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = name.getText().toString();

                if (choice_do == "" || choice_se == "" ||  choice_dong == "" || choice_do == null || choice_se == null || choice_dong == null) {
                    Toast.makeText(region.this, "모두 선택해 주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, Object> quote = new HashMap<>();
                    quote.put("choice_do", choice_do);
                    quote.put("choice_se", choice_se);
                    quote.put("choice_dong", choice_dong);
                    quote.put("user_name", user_name);

                    quote.put("ddo", ddo);
                    quote.put("se", se);
                    quote.put("dong", dong);

                    db.collection("MyWeather")
                            .document(user.getEmail().toString())
                            .set(quote)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("yejin", "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("yejin", "Error writing document", e);

                                }
                            });

                                        Intent intent=new Intent(region.this, MainActivity.class);
                                        finish();
                                        startActivity(intent);

                }
            }
        });


    }
}
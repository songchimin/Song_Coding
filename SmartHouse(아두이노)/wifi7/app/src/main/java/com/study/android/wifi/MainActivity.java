package com.study.android.wifi;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.xmlpull.v1.XmlPullParserException;

public class MainActivity extends AppCompatActivity implements SensorValueListener{
    private static final String TAG = "lecture";

    ViewPager viewPager;
    TabLayout tabLayout;

    AdView mAdView;


    //firebase auth object
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    //네비게이션 바
    private DrawerLayout drawerLayout;
    private View drawerView;

    int rain, gas, soil;
    Intent intent;

    TextView id;
    TextView name;
    TextView adress;

    FirebaseUser user;
    String user_email;
    FirebaseFirestore db;

    String Nname = "";
    String input_do = "";
    String input_se = "";
    String input_dong = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        intent = new Intent(MainActivity.this, MyService.class);

        //네비게이션 바
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);
        drawerLayout.setDrawerListener(listener);

        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });


        String bannerid = getResources().getString(R.string.ad_unit_id_1);
        MobileAds.initialize(getApplicationContext(), bannerid);

        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        adress = findViewById(R.id.adress);


        db=FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        user_email= user.getEmail();

        db=FirebaseFirestore.getInstance();



        db.collection("MyWeather")
                .document(user_email.toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            System.out.println(user_email);
                            DocumentSnapshot documentSnapshot = task.getResult();
                            input_do = (String) documentSnapshot.get("choice_do");
                            input_se = (String) documentSnapshot.get("choice_se");
                            input_dong = (String) documentSnapshot.get("choice_dong");
                            Nname = (String) documentSnapshot.get("user_name");

                            id.setText(user_email);
                            name.setText(Nname);
                            adress.setText(input_do +" "+input_se+" "+input_dong);
                        }
                    }
                });


        mAdView = findViewById(R.id.adView);

        viewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabMenu);

        PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // 다음의 리스너는 구현하지 않아도 된다.
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.d(TAG, "b:"+errorCode);
            }
            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }
            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
            }
        });


        AdRequest adRequest = new AdRequest
                .Builder()
                .addTestDevice("HASH_DEVICE_ID") //테스트
                .build();
        mAdView.loadAd(adRequest);


        //네비게이션 바
        Button btn_open = (Button)findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

//        Button btn_close = (Button)findViewById(R.id.btn_close);
//        btn_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.closeDrawers();
//            }
//        });


        Button logout = (Button)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });

        Button delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(MainActivity.this);
                alert_confirm.setMessage("정말 계정을 삭제 할까요?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        db.collection("MyWeather").document(user_email)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(MainActivity.this, "계정이 삭제 되었습니다.", Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });

                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(MainActivity.this, "계정이 삭제 되었습니다.", Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(getApplicationContext(), Login.class));
                                    }
                                });

                    }
                });
                alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "취소", Toast.LENGTH_LONG).show();
                    }
                });
                alert_confirm.show();

            }
        });

        Button info = (Button)findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, user_information.class));
            }
        });

    }


    //네비게이션 바
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View view, float v) {

        }

        @Override
        public void onDrawerOpened(@NonNull View view) {

        }

        @Override
        public void onDrawerClosed(@NonNull View view) {

        }

        @Override
        public void onDrawerStateChanged(int i) {

        }
    };



    //뒤로가기 두번으로 어플 종료시키기
    private static long back_pressed;
    @Override
    public void onBackPressed() {
//
//        if (dlDrawer.isDrawerOpen(lvNavList)) {
//            dlDrawer.closeDrawer(lvNavList);
//        } else {
//            super.onBackPressed();
//        }

        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
//            Log.d(TAG, "onBackPressed:");
            finish();
        } else {
            Toast.makeText(getBaseContext(), "한번 더 뒤로가기를 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }


    int isrun = 0;

    @Override
    public void SensorValueset(int rain, int gas){
        this.rain = rain;
        this.gas = gas;

        if( (this.rain > 100 || this.gas > 400 ) && isrun == 0) {
            //Intent intent = new Intent(MainActivity.this, MyService.class);
            startService(intent);
            isrun = 1;
        }
        else if( !(this.rain > 100 && this.gas > 400) && isrun == 1 ){
            //  Intent intent = new Intent(MainActivity.this,MyService.class);
            stopService(intent);
            isrun = 0;
        }
    }

}

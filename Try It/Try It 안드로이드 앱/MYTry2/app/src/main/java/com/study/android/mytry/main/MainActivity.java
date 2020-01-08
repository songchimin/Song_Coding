package com.study.android.mytry.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.study.android.mytry.R;
import com.study.android.mytry.mypage.setup.QnAMain;
import com.study.android.mytry.search.Search_detail;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;

    TabItem search_tab;
    TabItem custom_tab;
    TabItem certification_tab;
    TabItem feed_tab;
    TabItem mypage_tab;


    private static final int PUBLIC_CREATION_FINISH = 65537;
    private static final int PRIVATE_CREATION_FINISH = 2;
    private static final int MYPAGE_QNA_FINISH_ = 6;
    private static final int MYPAGE_MEMO_FINISH = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        viewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabMenu);

        search_tab = findViewById(R.id.search_tab);
        custom_tab = findViewById(R.id.custom_tab);
        certification_tab = findViewById(R.id.certification_tab);
        feed_tab = findViewById(R.id.feed_tab);
        mypage_tab = findViewById(R.id.mypage_tab);



        PagerAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.getTabAt(0).setIcon(R.drawable.main_chat_try);
        // 화면 전환 프래그먼트 선언 및 초기 화면 설정
        FragmentTransaction fragmentTransaction;

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                 if(tab.getPosition()==0){
                     tabLayout.getTabAt(0).setIcon(R.drawable.main_chat_try);
                     tabLayout.getTabAt(1).setIcon(R.drawable.main_pen_default);
                     tabLayout.getTabAt(2).setIcon(R.drawable.main_verified_default);
                     tabLayout.getTabAt(3).setIcon(R.drawable.main_mobile_default);
                     tabLayout.getTabAt(4).setIcon(R.drawable.main_profile_default);

                } else if (tab.getPosition()==1){
                     tabLayout.getTabAt(0).setIcon(R.drawable.main_chat_default);
                     tabLayout.getTabAt(1).setIcon(R.drawable.main_pen_try);
                     tabLayout.getTabAt(2).setIcon(R.drawable.main_verified_default);
                     tabLayout.getTabAt(3).setIcon(R.drawable.main_mobile_default);
                     tabLayout.getTabAt(4).setIcon(R.drawable.main_profile_default);
                } else if (tab.getPosition()==2){
                     tabLayout.getTabAt(0).setIcon(R.drawable.main_chat_default);
                     tabLayout.getTabAt(1).setIcon(R.drawable.main_pen_default);
                     tabLayout.getTabAt(2).setIcon(R.drawable.main_verified_try);
                     tabLayout.getTabAt(3).setIcon(R.drawable.main_mobile_default);
                     tabLayout.getTabAt(4).setIcon(R.drawable.main_profile_default);
                } else if (tab.getPosition()==3){
                     tabLayout.getTabAt(0).setIcon(R.drawable.main_chat_default);
                     tabLayout.getTabAt(1).setIcon(R.drawable.main_pen_default);
                     tabLayout.getTabAt(2).setIcon(R.drawable.main_verified_default);
                     tabLayout.getTabAt(3).setIcon(R.drawable.main_mobile_try);
                     tabLayout.getTabAt(4).setIcon(R.drawable.main_profile_default);
                } else if (tab.getPosition()==4){
                     tabLayout.getTabAt(0).setIcon(R.drawable.main_chat_default);
                     tabLayout.getTabAt(1).setIcon(R.drawable.main_pen_default);
                     tabLayout.getTabAt(2).setIcon(R.drawable.main_verified_default);
                     tabLayout.getTabAt(3).setIcon(R.drawable.main_mobile_default);
                     tabLayout.getTabAt(4).setIcon(R.drawable.main_profile_try);
                }


            }

            public void onTabUnselected(TabLayout.Tab tab) {

            }

            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void setTab(TabLayout.Tab tab){
        tabLayout.setScrollPosition(tab.getPosition(),0,true);
        if(tab.getPosition()==0){
            search_tab.setSelected(true);
            custom_tab.setSelected(false);
            certification_tab.setSelected(false);
            feed_tab.setSelected(false);
            mypage_tab.setSelected(false);
        } else if (tab.getPosition()==1){
            search_tab.setSelected(false);
            custom_tab.setSelected(true);
            certification_tab.setSelected(false);
            feed_tab.setSelected(false);
            mypage_tab.setSelected(false);
        } else if (tab.getPosition()==2){
            search_tab.setSelected(false);
            custom_tab.setSelected(false);
            certification_tab.setSelected(true);
            feed_tab.setSelected(false);
            mypage_tab.setSelected(false);
        } else if (tab.getPosition()==3){
            search_tab.setSelected(false);
            custom_tab.setSelected(false);
            certification_tab.setSelected(false);
            feed_tab.setSelected(true);
            mypage_tab.setSelected(false);
        } else if (tab.getPosition()==4){
            search_tab.setSelected(false);
            custom_tab.setSelected(false);
            certification_tab.setSelected(false);
            feed_tab.setSelected(false);
            mypage_tab.setSelected(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("request : " + requestCode);
        System.out.println("result : " + resultCode);

        if (requestCode == PUBLIC_CREATION_FINISH) {
            Log.d("yejin", "Main 오니 안오니 public");
            if (resultCode == RESULT_OK) {
                Log.d("yejin", "Main 오니 안오니");
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right);
                fragmentTransaction.add(R.id.fragment_creation, new com.study.android.mytry.challenge_public.CreationMain()).addToBackStack(null).commitAllowingStateLoss();
            }

        }
        if (requestCode == PRIVATE_CREATION_FINISH) {
            Log.d("yejin", "Main 오니 안오니1");
            if (resultCode == RESULT_OK) {
                Log.d("lecture", "Main 들어오니");
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right);
                fragmentTransaction.add(R.id.fragment_creation, new com.study.android.mytry.challenge_private.CreationMain()).addToBackStack(null).commitAllowingStateLoss();
            }
        }
        if (requestCode == MYPAGE_QNA_FINISH_) {
            if (resultCode == RESULT_OK) {
                Log.d("lecture", "Main 들어오니");
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right);
                fragmentTransaction.add(R.id.qna_main, new QnAMain()).addToBackStack(null).commitAllowingStateLoss();
            }
        }
        if (requestCode == MYPAGE_MEMO_FINISH) {
            Log.d("yejin",String.valueOf(requestCode));
            Log.d("yejin", "Main 오니 안오니1");
            if (resultCode == RESULT_OK) {
                Log.d("lecture", "Main 들어오니");
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right);
                fragmentTransaction.add(R.id.mypage, new Fragment_Mypage()).addToBackStack(null).commitAllowingStateLoss();
            }
        }


        // qrcode
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            //qrcode 가 없으면
            if (result.getContents() == null) {
                Toast.makeText(MainActivity.this, "취소!", Toast.LENGTH_SHORT).show();
            } else {
                //qrcode 결과가 있으면
                Toast.makeText(MainActivity.this, "스캔완료!", Toast.LENGTH_SHORT).show();
                try {
                    //data를 json으로 변환
                    JSONObject obj = new JSONObject(result.getContents());
                    Intent intent = new Intent(getApplicationContext(), Search_detail.class);

                    intent.putExtra("challenge_num",obj.getString("num"));
                    intent.putExtra("challenge_title",obj.getString("title"));
                    intent.putExtra("challenge_category",obj.getString("category"));
                    intent.putExtra("challenge_type",obj.getString("type"));
                    intent.putExtra("challenge_frequency",obj.getString("frequency"));
                    intent.putExtra("challenge_start",obj.getString("start"));
                    intent.putExtra("challenge_end",obj.getString("end"));
                    intent.putExtra("challenge_fee",obj.getString("fee"));
                    intent.putExtra("challenge_time",obj.getString("time"));
                    intent.putExtra("challenge_detail",obj.getString("detail"));
                    intent.putExtra("challenge_first_image",obj.getString("first_image"));
                    intent.putExtra("challenge_state",obj.getString("state"));
                    intent.putExtra("challenge_public",obj.getString("public"));
                    intent.putExtra("challenge_exp",obj.getString("exp"));
                    intent.putExtra("challenge_host",obj.getString("host"));
                    //intent.putExtra("bookmaker_exist",item.getBookmaker_exist());


                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(MainActivity.this, result.getContents(), Toast.LENGTH_LONG).show();
                    Log.d("yejin", result.getContents());
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private long time = 0;

    public interface onKeyBackPressedListener {
        void onBackKey();
    }

    private onKeyBackPressedListener mOnKeyBackPressedListener;

    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    }

    @Override
    public void onBackPressed() {
        if (mOnKeyBackPressedListener != null) {
                mOnKeyBackPressedListener.onBackKey();
            } else {
                //쌓인 BackStack 여부에 따라 Toast를 띄울지, 뒤로갈지
                if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    //* 종료 EndToast Bean 사용
                    if (System.currentTimeMillis() - time >= 2000) {
                        time = System.currentTimeMillis();
                        Toast.makeText(getApplicationContext(), "종료하려면 한번 더 누르세요.", Toast.LENGTH_SHORT).show();
                    } else if (System.currentTimeMillis() - time < 2000) {
                        finish();
                    }

                } else {
                    super.onBackPressed();

            }
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

    }



}

package com.study.android.wifi;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;


public class Fragment1 extends Fragment {

    static weather weather = new weather();
    static parsing parsing;

    TextView txtTemp;
    TextView txtWeather;
    TextView txtDust;
    TextView txtDate;
    TextView tempLow;
    TextView tempHigh;
    TextView water;
    TextView txtRegion;

    ImageView imageWeather;
    LinearLayout background;

    SingerAdapter adapter;
    RecyclerView mRecyclerView;
    int nCount;

    ViewGroup rootView;

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String user_email;

    String input_do;
    String input_se;
    String input_dong;

    XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
    XmlPullParser parser = parserCreator.newPullParser();

//    FirebaseFirestore db;

    final Handler handler = new Handler();

    public Fragment1() throws XmlPullParserException {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        txtDate = (TextView)rootView.findViewById(R.id.txtDate);
        txtTemp = (TextView)rootView.findViewById(R.id.txtTemp);
        txtWeather = (TextView)rootView.findViewById(R.id.txtWeather);
        txtDust = (TextView)rootView.findViewById(R.id.txtDust);

        tempLow= (TextView)rootView.findViewById(R.id.tempLow);
        tempHigh= (TextView)rootView.findViewById(R.id.tempHigh);
        water= (TextView)rootView.findViewById(R.id.sunrise);

        imageWeather=(ImageView)rootView.findViewById(R.id.imageWeather);
        background=(LinearLayout)rootView.findViewById(R.id.weatherback);

        txtRegion=(TextView)rootView.findViewById(R.id.textRegion);


        try {
            parsing = new parsing();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        user_email= user.getEmail();
//        System.out.println(user_email);

//        db=FirebaseFirestore.getInstance();

        onRegion();


//        db.collection("MyWeather")
//                .document(user_email.toString())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            System.out.println(user_email);
//                            DocumentSnapshot documentSnapshot = task.getResult();
//                            input_do = (String) documentSnapshot.get("choice_do");
//                            input_se = (String) documentSnapshot.get("choice_se");
//                            input_dong = (String) documentSnapshot.get("choice_dong");
//
//                            weather.setChoice_do(input_do);
//                            weather.setChoice_se(input_se);
//                            weather.setChoice_dong(input_dong);


//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    onRegion();
//
//                                }
//                            }, 3000 );

//                            Log.d("yejin", "Cached document success: " + input_do);
//                            Log.d("yejin", "Cached document success: " + input_se);
//                            Log.d("yejin", "Cached document success: " + input_dong);

//                            onRegion();

//                        } else {
//                            Log.d("yejin", "Cached document failed: " + task.getException());
//                            System.out.println("안됨");
//                        }
//                    }
//                });



        return rootView;
    }


        public void onRegion() {

            InputStream inputStream = getResources().openRawResource(R.raw.region);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            try{
                parser.setInput(reader);

                int eventType = parser.getEventType();

                boolean check_do = false;
                boolean check_se = false;
                boolean check_dong = false;

                //여기서 해야할일
                // 1. if = 저장되어있는 시군구가 태그랑 다 같은게 있으면
                // 2. 객체 nx, ny에 저장하기

                boolean isItemTag = true;

                System.out.println("들어있니?"+input_do);

                while (eventType != XmlPullParser.END_DOCUMENT && isItemTag){

                    String startTag="";
                    switch(eventType){
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            String check=parser.getName();
                            if(check.equals("dosi")){
                                String Do="";
                                try {
                                    Do=(String) parser.nextText();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                if(weather.choice_do!=null) {
                                    if (weather.choice_do.equals(Do)) {
                                        check_do = true;
                                        Log.d("xml", "Start TAG :" + Do);
                                    }
                                }else {
                                    check_do=true;
                                }
                            }

                            if(check.equals("sigun") ){
                                String si="";
                                try {
                                    si=(String) parser.nextText();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if(weather.choice_se!=null) {
                                    if (weather.choice_se.equals(si)) {
                                        check_se = true;
                                        Log.d("xml", "Start TAG :" + si);
                                    }
                                }else if(weather.choice_se == ""){
                                }
                                else {
                                    check_se = true;
                                }
                            }

                            if(check.equals("dong")){
                                String dong="";
                                try {
                                    dong=(String) parser.nextText();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if(weather.choice_dong != null){
                                     if( weather.choice_dong.equals(dong)) {
                                        check_dong=true;
                                        Log.d("xml", "Start TAG :" + dong);
                                      }
                                }else if(weather.choice_dong == ""){
                                    check_dong=true;
                                }else {
                                    check_dong=true;
                                }
                            }

                            if(check.equals("nx")){
                                if(check_do && check_dong && check_se){
                                    try {
                                        weather.setNX(parser.nextText());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            if(check.equals("ny")){
                                if(check_do && check_dong && check_se){
                                    try {
                                        weather.setNY(parser.nextText());
                                        isItemTag=false;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            break;
                        case XmlPullParser.END_TAG:
                        //    Log.d("xml","End TAG : "+ parser.getName());
                            break;
                    }
                    try {
                        eventType = parser.next();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } catch (XmlPullParserException e) {
                e.printStackTrace();

            } finally{
                try{
                    if(reader !=null) reader.close();
                    if(inputStreamReader !=null) inputStreamReader.close();
                    if(inputStream !=null) inputStream.close();
                }catch(Exception e2){
                    e2.printStackTrace();
                }
            }

            Log.d("yejin", "region : " + weather.choice_nx + weather.choice_ny);

//            System.out.println("이번엔 되니"+weather.choice_nx+weather.choice_ny);
            //비동기 작업 실행
            AsyncTask1 api1 = new AsyncTask1();
            api1.execute();


            APItask api = new APItask();
            api.execute();

//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            }, 1500 );

        }



    public class APItask extends AsyncTask<Integer, Integer, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {

            try {
            } catch (Exception e) {
                e.printStackTrace();
            }finally {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            txtDate.setText(AsyncTask1.doYearMonthDay().substring(0,4)+"년 "+AsyncTask1.doYearMonthDay().substring(4,6)+"월 "+AsyncTask1.doYearMonthDay().substring(6,8)+"일 ");

            txtRegion.setText(weather.choice_do+" "+weather.choice_se+" "+weather.choice_dong);

            // 미세먼지
            String ps10 = weather.pm10;
            int p10 = Integer.parseInt(ps10.trim());

            if (p10 < 30) {
                txtDust.setText("미세먼지 \n좋음");
            } else if (30 <= p10 && p10 <= 50) {
                txtDust.setText("미세먼지 \n보통");
            } else if (p10 > 50 && p10 <= 75) {
                txtDust.setText("미세먼지 \n나쁨");
            } else if (p10 > 75) {
                txtDust.setText("미세먼지 \n최악");
            }

            //-------------------------------------------------------------------------------------------------
            //오늘 날씨
            txtTemp.setText(weather.T1H + " ℃");

           //  최저 온도
            if(Integer.parseInt(weather.T3H_1)<=Integer.parseInt(weather.T3H_5)){
                tempLow.setText(weather.T3H_1 + " ℃");

            }
            // 최고 온도
            if(Integer.parseInt(weather.T3H_1)>=Integer.parseInt(weather.T3H_5)){
                tempLow.setText(weather.T3H_5 + " ℃");
            }
            tempHigh.setText(weather.T3H_3 + " ℃");

            water.setText("습도\n"+weather.REH+" %");

            int tmpID;
            if(Integer.parseInt(AsyncTask1.doTime())< 600){
                tmpID = getResources().getIdentifier(getsky2(weather.SKY0_1, Integer.parseInt(weather.PTY_1)), "drawable", "com.study.android.wifi");
                imageWeather.setImageResource(tmpID);

            }

            if(Integer.parseInt(AsyncTask1.doTime())> 600 && Integer.parseInt(AsyncTask1.doTime())< 900) {
                tmpID = getResources().getIdentifier(getsky2(weather.SKY0_1, Integer.parseInt(weather.PTY_1)), "drawable", "com.study.android.wifi");
                imageWeather.setImageResource(tmpID);
                txtWeather.setText(weather.SKY0_1);
            }

            if(Integer.parseInt(AsyncTask1.doTime())> 900 && Integer.parseInt(AsyncTask1.doTime())< 1200) {
                tmpID = getResources().getIdentifier(getsky2(weather.SKY0_2, Integer.parseInt(weather.PTY_2)), "drawable", "com.study.android.wifi");
                imageWeather.setImageResource(tmpID);
                txtWeather.setText(weather.SKY0_2);
            }

            if(Integer.parseInt(AsyncTask1.doTime())> 1200 && Integer.parseInt(AsyncTask1.doTime())< 1800) {
                tmpID = getResources().getIdentifier(getsky2(weather.SKY0_3, Integer.parseInt(weather.PTY_3)), "drawable", "com.study.android.wifi");
                imageWeather.setImageResource(tmpID);
                txtWeather.setText(weather.SKY0_3);
            }

            if(Integer.parseInt(AsyncTask1.doTime())> 1800 && Integer.parseInt(AsyncTask1.doTime())< 2100) {
                tmpID = getResources().getIdentifier(getsky2(weather.SKY0_4, Integer.parseInt(weather.PTY_4)), "drawable", "com.study.android.wifi");
                imageWeather.setImageResource(tmpID);
                txtWeather.setText(weather.SKY0_4);

            }

            if(Integer.parseInt(AsyncTask1.doTime()) >2100) {
                tmpID = getResources().getIdentifier(getsky2(weather.SKY0_5, Integer.parseInt(weather.PTY_5)), "drawable", "com.study.android.wifi");
                imageWeather.setImageResource(tmpID);
                txtWeather.setText(weather.SKY0_5);
            }

            // 주간 날씨!!!!
            //-------------------------------------------------------------------------------------------------------

            Context context=getActivity();

            adapter=new SingerAdapter(context);

            tmpID = getResources().getIdentifier(getsky(weather.SKY3), "drawable", "com.study.android.wifi");
            SingerItem item1= new SingerItem(AsyncTask1.today_1().substring(4,6)+"/"+AsyncTask1.today_1().substring(6,8),getyoil(0),tmpID,weather.TMX1.substring(0, weather.TMX1.length() - 2) + " ℃",weather.TMN1.substring(0, weather.TMN1.length() - 2) + " ℃");
            tmpID = getResources().getIdentifier(getsky(weather.SKY4), "drawable", "com.study.android.wifi");
            SingerItem item2= new SingerItem(AsyncTask1.today_2().substring(4,6)+"/"+AsyncTask1.today_2().substring(6,8),getyoil(1),tmpID,weather.TMX2.substring(0, weather.TMX2.length() - 2) + " ℃",weather.TMN2.substring(0, weather.TMN2.length() - 2) + " ℃");
            tmpID = getResources().getIdentifier(getsky(weather.SKY5), "drawable", "com.study.android.wifi");
            SingerItem item3= new SingerItem(AsyncTask1.today_3().substring(4,6)+"/"+AsyncTask1.today_3().substring(6,8),getyoil(2),tmpID,weather.TMX3+ " ℃",weather.TMN3 + " ℃");
            tmpID = getResources().getIdentifier(getsky(weather.SKY6), "drawable", "com.study.android.wifi");
            SingerItem item4= new SingerItem(AsyncTask1.today_4().substring(4,6)+"/"+AsyncTask1.today_4().substring(6,8),getyoil(3),tmpID,weather.TMX4 + " ℃",weather.TMN4 + " ℃");
            tmpID = getResources().getIdentifier(getsky(weather.SKY7), "drawable", "com.study.android.wifi");
            SingerItem item5= new SingerItem(AsyncTask1.today_5().substring(4,6)+"/"+AsyncTask1.today_5().substring(6,8),getyoil(4),tmpID,weather.TMX5 + " ℃",weather.TMN5 + " ℃");
            tmpID = getResources().getIdentifier(getsky(weather.SKY8), "drawable", "com.study.android.wifi");
            SingerItem item6= new SingerItem(AsyncTask1.today_6().substring(4,6)+"/"+AsyncTask1.today_6().substring(6,8),getyoil(5),tmpID,weather.TMX6 + " ℃",weather.TMN6 + " ℃");
            tmpID = getResources().getIdentifier(getsky(weather.SKY9), "drawable", "com.study.android.wifi");
            SingerItem item7= new SingerItem(AsyncTask1.today_7().substring(4,6)+"/"+AsyncTask1.today_7().substring(6,8),getyoil(6),tmpID,weather.TMX7 + " ℃",weather.TMN7 + " ℃");

            adapter.addItem(item7);
            adapter.addItem(item6);
            adapter.addItem(item5);
            adapter.addItem(item4);
            adapter.addItem(item3);
            adapter.addItem(item2);
            adapter.addItem(item1);

            mRecyclerView=rootView.findViewById(R.id.recycleView1);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            //RecyclerView 수평 스크롤로 변경하는 코드
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true));
            mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);

            nCount=1;
        }

        public String getsky(String a) {
            String result = "sunny1";
            if (a != null && a.contains("맑음")) {
                result = "sunny1";
            }
            if (a != null && (a.contains("눈") || a.contains("눈/비"))) {
                result = "snow";
            }
            if (a != null && (a.contains("비") || a.contains("비/눈"))) {
                result = "rain1";
            }
            if (a != null && a.contains("조금")) {
                result = "cloud2";
            }
            if (a != null && (a.contains("많음") || a.contains("흐림"))) {
                result = "cloud1";
            }
            return result;
        }

        public String getsky2(String a, int i) {
            String result = "sunny";

            if (a != null && i == 1 || i == 2) {
                result = "rain";
            }
            if (a != null && 1 == 3) {
                result = "snow";
            }
            if (a != null && a.contains("맑음")) {
                result = "sunny";
            }
            if (a != null && a.contains("많음") || a.contains("흐림")) {
                result = "cloud";
            }
            if (a != null && a.contains("조금")) {
                result = "cloud2";
            }

            return result;
        }

        public String getyoil(int i) {
            Calendar cal = Calendar.getInstance();
            int dayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) + i) % 7 + 1;

            String korDOW = "";
            switch (dayOfWeek) {
                case 1:
                    korDOW = "일";
                    break;
                case 2:
                    korDOW = "월";
                    break;
                case 3:
                    korDOW = "화";
                    break;
                case 4:
                    korDOW = "수";
                    break;
                case 5:
                    korDOW = "목";
                    break;
                case 6:
                    korDOW = "금";
                    break;
                case 7:
                    korDOW = "토";
                    break;
            }
            return korDOW;
        }
    }

}

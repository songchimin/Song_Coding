package com.study.android.wifi;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import static com.study.android.wifi.Fragment1.parsing;

public class AsyncTask1 extends AsyncTask<Integer, Integer, Void> {
    static String result = "";

    @Override
    protected Void doInBackground(Integer... integers) {
        //파싱
        parsing.dongnae2parsing();
        parsing.dongnae5parsing();
        parsing.chodanparsing();
        parsing.midterm();

        try {
            parsing.miseparsing();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    //오늘 날짜 가져오기 (api 변수에 사용합니다.)
        public static String doYearMonthDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Date date = new Date();
        String currentDate = formatter.format(date);
        System.out.println("날짜 1: "+currentDate);
        return currentDate;
    }

    //현재 시간 가져오기 (api 변수에 사용합니다.)
    public static String doTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH00", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        calendar.add(Calendar.HOUR_OF_DAY, 0);

        String currentDate = formatter.format(calendar.getTime());
        System.out.println(date);
        return currentDate;
    }

    public static String doyes(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);  // 오늘 날짜에서 하루를 뺌.
        String date = formatter.format(calendar.getTime());
        System.out.println("날짜 2: "+date);
        return date;
    }

    //  여기서 부터 주간 날짜
    public static String today_1(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +1);  // 내일.
        String date = formatter.format(calendar.getTime());
        return date;
    }

    public static String today_2(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +2);  //
        String date = formatter.format(calendar.getTime());
        return date;
    }

    public static String today_3(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +3);
        String date = formatter.format(calendar.getTime());
        return date;
    }

    public static String today_4(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +4);
        String date = formatter.format(calendar.getTime());
        return date;
    }

    public static String today_5(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +5);
        String date = formatter.format(calendar.getTime());
        return date;
    }

    public static String today_6(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +6);
        String date = formatter.format(calendar.getTime());
        return date;
    }

    public static String today_7(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +7);
        String date = formatter.format(calendar.getTime());
        return date;
    }


}
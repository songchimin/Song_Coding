package com.study.android.mytry.certification;

import android.app.Application;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class parsing {

    private String xml;
    private Document doc = null;
    String weather_url;

    XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
    XmlPullParser parser = parserCreator.newPullParser();

    public parsing() throws XmlPullParserException {

    }

    public String doYearMonthDay() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Date date = new Date();
        String currentDate = formatter.format(date);
        System.out.println("날짜 1: " + currentDate);
        return currentDate;
    }

    //현재 시간 가져오기 (api 변수에 사용합니다.)
    public String doTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH00", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        calendar.add(Calendar.HOUR_OF_DAY, 0);

        String currentDate = formatter.format(calendar.getTime());
        System.out.println(date);
        return currentDate;
    }


    public static String doyes() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);  // 오늘 날짜에서 하루를 뺌.
        String date = formatter.format(calendar.getTime());
        System.out.println("날짜 2: " + date);
        return date;
    }

    //초단기 실황 - 완료
    public String chodanparsing() {
        String nowtime = doTime();
        String today = doYearMonthDay();

        int a = Integer.parseInt(nowtime) - 100;
        nowtime = "0" + Integer.toString(a);

        weather_url = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib?" +
                "serviceKey=K%2BMCtYf%2B7K08gNEfV3IEBwpDyJDxv%2BJwiJyqKN3N1EbCOjRXnGr7gA1HLJS3pxNOZb8%2FDXC3G7DjBdA3ivDQCw%3D%3D&" +
                "base_date=" +
                today +
                "&" +
                "base_time=" +
                nowtime +
                "&" +
                "nx=60&" +
                "ny=127&" +
                "numOfRows=10&" +
                "_type=xml";
        Log.d("lecture", "초단기" + weather_url);
        URL url;
        Document doc = null;

        try {
            url = new URL(weather_url);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();


        } catch (Exception e) {
        }

        String s = "";
        NodeList nodeList = doc.getElementsByTagName("item");


        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);
            Element fstElmnt = (Element) node;
            NodeList idx = fstElmnt.getElementsByTagName("category");

            //기온
            if (idx.item(0).getChildNodes().item(0).getNodeValue().equals("T1H")) {
                NodeList gugun = fstElmnt.getElementsByTagName("obsrValue");
                s = s + gugun.item(0).getChildNodes().item(0).getNodeValue() + ",";
                System.out.println("기온기온" + s);
            }

            //습도
            if (idx.item(0).getChildNodes().item(0).getNodeValue().equals("REH")) {
                NodeList gugun = fstElmnt.getElementsByTagName("obsrValue");
                s = s + gugun.item(0).getChildNodes().item(0).getNodeValue() + ",";
                System.out.println("습도습도" + s);
            }
        }

        return s;
    }

    // 0200 동네 예보 - 오늘 내일 예보
    public String dongnae2parsing() {
        String nowtime = doTime();
        String today;
        int a = Integer.parseInt(nowtime);
        System.out.println(a+"여기 날씨 "+nowtime);

        if (a < 200) {
            // 오늘 두시
            today = doyes();
            nowtime = "0200";
            System.out.println("1 ");
        } else if (a > 200 && a < 500) {
            // 오늘 다섯시
            today = doyes();

            nowtime = "0500";
            System.out.println("2 ");

        } else {
            // 내일 두시
            today = doYearMonthDay();
            nowtime = "0200";
            System.out.println("3 ");

        }

        weather_url = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData?" +
                "serviceKey=K%2BMCtYf%2B7K08gNEfV3IEBwpDyJDxv%2BJwiJyqKN3N1EbCOjRXnGr7gA1HLJS3pxNOZb8%2FDXC3G7DjBdA3ivDQCw%3D%3D&" +
                "base_date=" +
                today +
                "&base_time=" +
                nowtime +
                "&nx=60&" +
                "ny=127&" +
                "numOfRows=250&" +
                "pageNo=1&" +
                "_type=xml";

        System.out.println(today);
        System.out.println(nowtime);
        System.out.println("url : " + weather_url);

        URL url;

        try {
            url = new URL(weather_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setUseCaches(false);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = br.readLine();
                if (line == null)
                    break;
                sb.append(line);
            }
            xml = sb.toString();
            br.close();
            conn.disconnect();

        } catch (Exception e) {
            System.out.println("다운로드에러" + e.getMessage());

        }

        String ss = "";

        Element element;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            doc = db.parse(is);
            element = doc.getDocumentElement();

            String s = "";

            String sss = "";
            NodeList nodeList = element.getElementsByTagName("item");


            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;
                NodeList idx = fstElmnt.getElementsByTagName("category");

                if (idx.item(0).getChildNodes().item(0).getNodeValue().equals("SKY")) {
                    NodeList gugun2 = fstElmnt.getElementsByTagName("fcstDate");
                    sss = gugun2.item(0).getChildNodes().item(0).getNodeValue() + "";

                    // 오늘
                    if (sss.equals(today)) {
                        NodeList gugun = fstElmnt.getElementsByTagName("fcstValue");
                        int cloud_num = Integer.parseInt(gugun.item(0).getChildNodes().item(0).getNodeValue());
                        NodeList gugun1 = fstElmnt.getElementsByTagName("fcstTime");
                        s = "" + gugun1.item(0).getChildNodes().item(0).getNodeValue() + "";

                        if (cloud_num == 0 || cloud_num == 1 || cloud_num == 2) {
                            ss = "맑음";
                        } else if (cloud_num == 3 || cloud_num == 4 || cloud_num == 5) {
                            ss = "구름 조금";
                        } else if (cloud_num == 6 || cloud_num == 7 || cloud_num == 8) {
                            ss = "구름 많음";
                        } else if (cloud_num == 9 || cloud_num == 10) {
                            ss = "흐림";
                        }
//                        if (s.equals("0600")) {
//                            weather.SKY0_1 = ss;
//                        }
//                        if (s.equals("0900")) {
//                            weather.SKY0_2 = ss;
//                        }
//                        if (s.equals("1200")) {
//                            weather.SKY0_3 = ss;
//                        }
//                        if (s.equals("1800")) {
//                            weather.SKY0_4 = ss;
//                        }
//                        if (s.equals("2100")) {
//                            weather.SKY0_5 = ss;
//                        }

                    }
                }
            }


        } catch (Exception e) {
        }
        return ss;
    }

}

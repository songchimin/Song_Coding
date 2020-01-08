package com.study.android.mytry.main;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.formats.NativeAd;
import com.study.android.mytry.R;
import com.study.android.mytry.challenge_public.FileLoadConnection;
import com.study.android.mytry.chat.chat_room;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.Login;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.search.PopupActivity;
import com.study.android.mytry.search.search_SingerItem;

import com.study.android.mytry.search.search_bookmark;
import com.study.android.mytry.search.search_filer;
import com.study.android.mytry.search.search_main;
import com.study.android.mytry.search.searchmain_SingerItem;
import com.study.android.mytry.search.searchmain_singerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import static com.study.android.mytry.login.Intro.local_url;

public class Fragment_Search extends Fragment {
    private static final String TAG = "lecture";
    private FloatingActionButton fab;
    public static GridView gridView;
    searchmain_singerAdapter adapter;
    TextView challenge_name;
    TextView challenge_start;
    TextView challenge_end;
    NativeAd.Image image;
    ImageView imageView;
    Button all;
    Button hobby;
    Button life;
    Button capability;
    Button health;
    Button realtionship;
    Button asset;
    ImageButton search_main_like_btn;
    public static String bookuserid;
    public static String category;
    TextView textViewday;
    TextView textViewday2;
    public static String Master_nickName = "";
    Button search_main_direct_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        GlobalApplication myApp = (GlobalApplication) getActivity().getApplication();
        bookuserid = myApp.getGlobalString();

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.search_main, container, false);

        gridView = rootView.findViewById(R.id.search_list2);

        String url = local_url + "/uzinee/serach_public_list";
        String msg = "?member_id=" + bookuserid + "&filter=all";
        url = url + msg;
        Log.d("done_money1231", url);
        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        String url1 = local_url + "/yejin/nickName?id=" + bookuserid;

        Log.d("lecture",url1);
        NicknameTask nicknameTask = new NicknameTask(url1, null);
        nicknameTask.execute();


        search_main_direct_btn = rootView.findViewById(R.id.search_main_direct_btn);
        search_main_direct_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), chat_room.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.rightin_activity,R.anim.not_move_activity);
            }
        });


        fab = rootView.findViewById(R.id.gofilter);
        fab.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDestroy();
                Intent intent = new Intent(getActivity(), search_filer.class);
                startActivity(intent); // 다음화면으로 넘어가기
            }
        });


        search_main_like_btn = rootView.findViewById(R.id.search_main_like_btn);
        search_main_like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), search_bookmark.class);
                startActivity(intent); // 다음화면으로 넘어가기

            }
        });

        all = rootView.findViewById(R.id.search_main_category_all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/uzinee/serach_public_list";
                String msg = "?member_id=" + bookuserid;
                url = url + msg;
                Log.d("done_money1231", url);
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();
                Log.d("카테고리", "모두");
            }
        });

        life = rootView.findViewById(R.id.search_main_category_life);
        life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/uzinee/category_life";
                String msg = "?member_id=" + bookuserid;
                url = url + msg;
                Log.d("done_money1231", url);
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();
                Log.d("카테고리", "생활");

            }
        });

        hobby = rootView.findViewById(R.id.search_main_category_hobby);
        hobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/uzinee/category_hobby";
                String msg = "?member_id=" + bookuserid;
                url = url + msg;
                Log.d("done_money1231", url);
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();
                Log.d("카테고리", "취미");

            }
        });


        capability = rootView.findViewById(R.id.search_main_category_capability);
        capability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/uzinee/category_capability";
                String msg = "?member_id=" + bookuserid;
                url = url + msg;
                Log.d("done_money1231", url);
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();
                Log.d("카테고리", "능력");
            }
        });


        health = rootView.findViewById(R.id.search_main_category_health);
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/uzinee/category_health";
                String msg = "?member_id=" + bookuserid;
                url = url + msg;
                Log.d("done_money1231", url);
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();
                Log.d("카테고리", "건강");
            }
        });


        realtionship = rootView.findViewById(R.id.search_main_category_relationship);
        realtionship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/uzinee/category_relationship";
                String msg = "?member_id=" + bookuserid;
                url = url + msg;
                Log.d("done_money1231", url);
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();
                Log.d("카테고리", "관계");
            }
        });


        asset = rootView.findViewById(R.id.search_main_category_asset);
        asset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/uzinee/category_asset";
                String msg = "?member_id=" + bookuserid;
                url = url + msg;
                Log.d("done_money1231", url);
                NetworkTask networkTask = new NetworkTask(url, null);
                networkTask.execute();
                Log.d("카테고리", "자산");
            }
        });

        int Random = 0;
        for (int i = 0; i < 1; i++) {
            int start = 1;

            int end = 5;

            double range = end - start + 1;

            Random = (int) (Math.random() * range + start);

//            double dValue = Math.random();
//           iValue = (int)(dValue * 10);
            System.out.println(Random);


        }
        textViewday2 = rootView.findViewById(R.id.textViewday2);
        textViewday = rootView.findViewById(R.id.textViewday);
        Log.d("랜덤함수", "" + Random);

        if (Random == 1) {
            textViewday2.setText("꺼리김없이 한 시간을 낭비하는 사람은 \n 아직 삶의 가치를 발견하지 못한 사람이다.");
            textViewday.setText("_찰스 R.다윈");
        } else if (Random == 2) {
            textViewday2.setText("늦게 시작하는 것을 두려워말고, \n 하다 중단하는것을 두려워 하라.");
            textViewday.setText("_중국속담");

        } else if (Random == 3) {
            textViewday2.setText("계획 없는 목표는 한날 꿈에 불과하다.");
            textViewday.setText("_생텍쥐페리");

        } else if (Random == 4) {
            textViewday2.setText("오늘은 힘들고, 내일은 더 힘들 것이다. \n 하지만 모레는 아름답다.");
            textViewday.setText("_잭 마윈");
        } else {
            textViewday2.setText("처음에는 우리가 습관을 만들지만 \n 그 다음에는 습관이 우리는 만든다.");
            textViewday.setText("_존 드라이든");
        }


        return rootView;
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            Log.d("done_money1231", "통과통과4");
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result; // 요청 결과를 저장할 변수.
            RequestHttpConnection requestHttpURLConnection = new RequestHttpConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            return result;
        }

        @Override
        protected void onPostExecute(String json) {

            super.onPostExecute(json);

            try {
                if (json != null) {
                    JSONObject jsonObject = new JSONObject(json);
                    Log.d("ssssssettext2", "들어옴");
                    Log.d("ssssssettext2", json);
                    JSONArray ListArray = jsonObject.getJSONArray("search_list");

                    searchmain_SingerItem[] singermainItems = new searchmain_SingerItem[ListArray.length()];
                    adapter = new searchmain_singerAdapter(getActivity());
                    for (int i = 0; i < ListArray.length(); i++) {

                        JSONObject ListObject = ListArray.getJSONObject(i);

                        String url = local_url + "/" + ListObject.getString("challenge_first_image") + ".jpg";

                        Log.d("testtxtx", url);
                        FileUploadNetworkTask fileUploadNetworkTask = new FileUploadNetworkTask(url, null);
                        fileUploadNetworkTask.execute();


                        singermainItems[i] =
                                new searchmain_SingerItem(
                                        String.valueOf(ListObject.getInt("challenge_num")),
                                        ListObject.getString("challenge_title"),
                                        ListObject.getString("challenge_category"),
                                        ListObject.getString("challenge_type"),
                                        ListObject.getString("challenge_frequency"),
                                        ListObject.getString("challenge_start"),
                                        ListObject.getString("challenge_end"),
                                        String.valueOf(ListObject.getInt("challenge_fee")),
                                        ListObject.getString("challenge_time"),
                                        ListObject.getString("challenge_detail"),
                                        ListObject.getString("challenge_first_image"),
                                        ListObject.getString("challenge_state"),
                                        ListObject.getString("challenge_public"),
                                        String.valueOf(ListObject.getInt("challenge_exp")),
                                        //      ListObject.getString("challenge_date"),
                                        ListObject.getString("challenge_host"),
                                        ListObject.getInt("bookmake_exist"));
                        adapter.addItem(singermainItems[i]);
                        Log.d("ssssssettext2", "들어옴111");


                    }
                    gridView.setAdapter(adapter);
                    Log.d("ssssssettext2", "들어옴222");
                } else {

                    challenge_name.setText("");
                    challenge_start.setText("");
                    challenge_end.setText("");

                    Log.d("ssssssettext2", "null임");

                }

            } catch (JSONException e) {
                Log.d("eeeeeeettext", "");
                e.printStackTrace();
            }
        }
    }


    public class FileUploadNetworkTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ContentValues values;

        public FileUploadNetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap result; // 요청 결과를 저장할 변수.
            FileLoadConnection requestHttpURLConnection = new FileLoadConnection();
            result = requestHttpURLConnection.request(url); // 해당 URL로 부터 결과물을 얻어온다.

            Log.d("testtxtx", "resilt   " + result.toString());

            return result;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap1) {

            super.onPostExecute(bitmap1);
            Log.d("testtxtx", "bitmap" + bitmap1.toString());
        }
    }


    public class NicknameTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NicknameTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpConnection requestHttpURLConnection = new RequestHttpConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                if (s != null) {
                    JSONObject json = new JSONObject(s);

                    Master_nickName = json.getString("Nickname");
                    Log.d("yejin",Master_nickName );
                }
            } catch (JSONException e) {
                Log.d("eeeeeeettext", "");
                e.printStackTrace();
            }
        }
    }


}
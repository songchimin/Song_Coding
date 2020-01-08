package com.study.android.mytry.challenge_public;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.main.MainActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import static com.study.android.mytry.login.Intro.local_url;

public class CreationMain extends Fragment implements MainActivity.onKeyBackPressedListener {

    FloatingActionButton creation_btn;

    Button back_pressed;
    public static ListView challenge_listView;

    PublicAdapter adapter;

    public static String userid;
    Button vote;

    private int CREATION_FINISH = 1;

    static  ArrayList<ChallengeDto> challenge = new ArrayList<>();

    static HashMap<String, String> challenge_map = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.challenge_public_main, container, false);

        GlobalApplication myApp = (GlobalApplication) getActivity().getApplication();
        userid = myApp.getGlobalString();

                // 공개 챌린지 생성
        creation_btn = rootView.findViewById(R.id.public_creation_floating);
        creation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreationPage.class);
                getActivity().startActivityForResult(intent, CREATION_FINISH);
            }
        });

        back_pressed = (Button) rootView.findViewById(R.id.challenge_back_page);
        back_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.setOnKeyBackPressedListener(null);
                //액티비티의 콜백을 직접호출
                activity.onBackPressed();
            }
        });

        challenge_listView = rootView.findViewById(R.id.public_challenge_list);
        adapter = new PublicAdapter(getActivity());

        String url = local_url + "/yejin/public_list?member_id=" + userid;

        Log.d("lecture", url);


        NetworkTask networkTask = new NetworkTask(url, null);
        networkTask.execute();

        // 버튼 누르고


        return rootView;
    }

    // 통신
    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

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
                JSONObject jsonObject = new JSONObject(json);

                JSONArray ListArray = jsonObject.getJSONArray("List");

                PublicItem[] publicItems = new PublicItem[ListArray.length()];

                for (int i = 0; i < ListArray.length(); i++) {

                    JSONObject ListObject = ListArray.getJSONObject(i);

                    publicItems[i] = new PublicItem(ListObject.getInt("challenge_num"), ListObject.getString("challenge_category"), ListObject.getString("challenge_title"), ListObject.getInt("challenge_like_count"), ListObject.getInt("challenge_like_exist"));

                    challenge.add(new ChallengeDto(String.valueOf(ListObject.getInt("challenge_num")),
                            ListObject.getString("challenge_title"),
                            ListObject.getString("challenge_category"),
                            ListObject.getString("challenge_type"),
                            ListObject.getString("challenge_detail"),
                            ListObject.getInt("challenge_like_count"),
                            ListObject.getInt("challenge_like_exist")));

                    adapter.addItem(publicItems[i]);
                }

                challenge_listView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //BackStack 으로 뒤로가기 버튼 누르면 전 화면으로 이동하기 위함
    @Override
    public void onBackKey() {
        MainActivity activity = (MainActivity) getActivity();
        activity.setOnKeyBackPressedListener(null);
        activity.onBackPressed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) context).setOnKeyBackPressedListener(this);
    }
}

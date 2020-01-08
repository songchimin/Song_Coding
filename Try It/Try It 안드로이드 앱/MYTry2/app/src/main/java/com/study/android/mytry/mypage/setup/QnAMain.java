package com.study.android.mytry.mypage.setup;

import android.app.Activity;
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

import static com.study.android.mytry.login.Intro.local_url;

public class QnAMain extends Fragment implements MainActivity.onKeyBackPressedListener {

    ListView questionList;
    FloatingActionButton questionWrite;

    QnAdapter qnAdapter;
    String userid;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.mypage_setup_qna, container, false);

        GlobalApplication myApp = (GlobalApplication) getActivity().getApplication();
        userid = myApp.getGlobalString();

        questionList = rootView.findViewById(R.id.QandAlist);
        questionWrite = rootView.findViewById(R.id.question_submit);
        questionWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QnAWrite.class);
                getActivity().startActivityForResult(intent, 6);
            }
        });

        qnAdapter = new QnAdapter(getActivity());

        String url = local_url + "/yejin/mypage_question_list";
        String msg = "?id=" + userid;

        Log.d("lecture", msg);
        url = url + msg;
        Log.d("lecture", url);

        QuestionNetworkTask questionNetworkTask = new QuestionNetworkTask(url, null);
        questionNetworkTask.execute();

        return rootView;
    }

    // 내 메모 추가
    public class QuestionNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public QuestionNetworkTask(String url, ContentValues values) {

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
                    System.out.println(json);

                    JSONObject jsonObject = new JSONObject(json);

                    JSONArray MemberList = jsonObject.getJSONArray("List");

                    QnAItem[] qnAItems = new QnAItem[MemberList.length()];

                    for (int i = 0; i < MemberList.length(); i++) {

                        JSONObject ListObject = MemberList.getJSONObject(i);

                        String state = null;

                        if (ListObject.getString("question_state").equals("0")) {
                            state = "답변 대기중";
                        } else if (ListObject.getString("question_state").equals("1")) {
                            state = "답변 완료";
                        }

                        qnAItems[i] = new QnAItem(ListObject.getInt("question_num"),
                                ListObject.getString("member_id"),
                                ListObject.getString("manager_id"),
                                ListObject.getString("question_title"),
                                ListObject.getString("question_Content"),
                                ListObject.getString("question_Picture"),
                                ListObject.getString("question_answer"),
                                ListObject.getString("question_divide"),
                                state,
                                ListObject.getString("question_date"),
                                ListObject.getString("anwser_date"));

                        qnAdapter.addItem(qnAItems[i]);
                        System.out.println(qnAItems[i]);

                        if (getActivity().isFinishing()) {
                            return;
                        }
                    }
                    questionList.setAdapter(qnAdapter);
                }
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

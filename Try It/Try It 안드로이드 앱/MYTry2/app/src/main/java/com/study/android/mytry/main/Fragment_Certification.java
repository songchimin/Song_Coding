package com.study.android.mytry.main;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.study.android.mytry.R;
import com.study.android.mytry.certification.CertificateAdapter;
import com.study.android.mytry.certification.CertificateItem;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static com.study.android.mytry.login.Intro.local_url;

public class Fragment_Certification extends Fragment {
    private static final String tag = "certificate";

    ListView c_list;
    CertificateAdapter adapter;

    Button top_btn1;
    Button top_btn2;
    String url2;
    String userid;

    JSONObject ListObject2;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.certification_main, container, false);

        GlobalApplication myApp = (GlobalApplication) getActivity().getApplication();
        userid = myApp.getGlobalString();

        c_list = view.findViewById(R.id.certification_listView);
        top_btn1 = view.findViewById(R.id.certification_top_btn1);
        top_btn2 = view.findViewById(R.id.certification_top_btn2);

        url2 = local_url + "/selee/certificate_list";
        String msg = "?member_id="+userid;
        url2 = url2+msg;
        Log.d("certificate", "url2 : "+url2);

        //인증가능 list출력(top_btn1)
        NetworkTask7 networkTask7 = new NetworkTask7(url2, null);
        networkTask7.execute();

        top_btn1.setSelected(true);

        top_btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                top_btn1.setSelected(true);
                top_btn2.setSelected(false);

                //인증가능 list출력
                NetworkTask7 networkTask7 = new NetworkTask7(url2, null);
                networkTask7.execute();

            }
        });

        top_btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                top_btn1.setSelected(false);
                top_btn2.setSelected(true);

                //전체 list출력
                NetworkTask2 networkTask2 = new NetworkTask2(url2, null);
                networkTask2.execute();
            }
        });

        return view;
    }

    // 인증가능 list 통신
    public class NetworkTask7 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask7(String url, ContentValues values) {
            this.url = url;
            this.values = values;
            Log.d(tag, "통신111111");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result; // 요청 결과를 저장할 변수.
            RequestHttpConnection requestHttpURLConnection = new RequestHttpConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            Log.d(tag, "resultttttt " + result);
            return result;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);

            try {
                if(json != null) {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray ListArray = jsonObject.getJSONArray("List");
                    Log.d(tag, ListArray.toString());

                    CertificateItem[] certificateItems = new CertificateItem[ListArray.length()];
                    Log.d(tag, String.valueOf(ListArray.length()));

                    adapter = new CertificateAdapter(getActivity());
                    for (int i = 0; i < ListArray.length(); i++) {
                        JSONObject ListObject2 = ListArray.getJSONObject(i);

//                        String url3 = local_url+"/" + ListObject2.getString("challenge_first_image")+".jpg";
//
//                        Log.d(tag,"url333333"+url3);
//                        Fragment_Certification.FileUploadNetworkTask fileUploadNetworkTask = new Fragment_Certification.FileUploadNetworkTask(url3, null);
//                        fileUploadNetworkTask.execute();

                        if(ListObject2.getString("certificate_check").equals("0")) {
                            certificateItems[i] = new CertificateItem(
                                    ListObject2.getString("challenge_num"),
                                    ListObject2.getString("certificate_check"),
                                    ListObject2.getString("challenge_type"),
                                    ListObject2.getString("challenge_title"),
                                    ListObject2.getString("challenge_start"),
                                    ListObject2.getString("challenge_end"),
                                    ListObject2.getString("challenge_frequency"),
                                    ListObject2.getString("challenge_time"),
                                    ListObject2.getString("challenge_first_image"),
                                    ListObject2.getInt("all_count"),
                                    ListObject2.getInt("check_count"));
                            Log.d(tag, ListObject2.getString("challenge_start") + "    " + ListObject2.getString("challenge_end") + "   " + ListObject2.getString("challenge_frequency"));
                            adapter.addItem(certificateItems[i]);
                        }
                    }
                    c_list.setAdapter(adapter);
                } else{
                    Log.d("certificate", "Nulllllllll");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // 전체인증
    public class NetworkTask2 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask2(String url, ContentValues values) {
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
                Log.d("certificate", ListArray.toString());
                CertificateItem [] certificateItems = new CertificateItem[ListArray.length()];

                Log.d("certificate", String.valueOf(ListArray.length()));

                adapter = new CertificateAdapter(getActivity());
                for (int i = 0; i < ListArray.length(); i++) {
                    JSONObject ListObject2 = ListArray.getJSONObject(i);

//                    String url3 = local_url+"/" + ListObject2.getString("challenge_first_image")+".jpg";
//
//                    Log.d(tag, "url33333"+url3);
//                    Fragment_Certification.FileUploadNetworkTask fileUploadNetworkTask = new Fragment_Certification.FileUploadNetworkTask(url3, null);
//                    fileUploadNetworkTask.execute();

                    certificateItems[i] = new CertificateItem(
                            ListObject2.getString("challenge_num"),
                            ListObject2.getString("certificate_check"),
                            ListObject2.getString("challenge_type"),
                            ListObject2.getString("challenge_title"),
                            ListObject2.getString("challenge_start"),
                            ListObject2.getString("challenge_end"),
                            ListObject2.getString("challenge_frequency"),
                            ListObject2.getString("challenge_time"),
                            ListObject2.getString("challenge_first_image"),
                            ListObject2.getInt("all_count"),
                            ListObject2.getInt("check_count")
                    );
                    adapter.addItem(certificateItems[i]);
                }
                c_list.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

//    public class FileUploadNetworkTask extends AsyncTask<Void, Void, Bitmap> {
//
//        private String url;
//        private ContentValues values;
//
//        public FileUploadNetworkTask(String url, ContentValues values) {
//            this.url = url;
//            this.values = values;
//        }
//
//        @Override
//        protected Bitmap doInBackground(Void... params) {
//            Bitmap result; // 요청 결과를 저장할 변수.
//            FileLoadConnection requestHttpURLConnection = new FileLoadConnection();
//            result = requestHttpURLConnection.request(url); // 해당 URL로 부터 결과물을 얻어온다.
//
//            Log.d(tag,"resilt   "+result.toString());
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap1) {
//
//            super.onPostExecute(bitmap1);
//            Log.d(tag,"bitmap"+bitmap1.toString());
//        }
//    }
}
package com.study.android.mytry.mypage.setup;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.study.android.mytry.R;
import com.study.android.mytry.challenge_public.FileLoadConnection;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.Login;
import com.study.android.mytry.login.Logout;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.study.android.mytry.login.Intro.local_url;
import static kr.co.bootpay.Bootpay.finish;

public class ProfileModify extends Fragment implements MainActivity.onKeyBackPressedListener {

    LinearLayout into_pw;
    LinearLayout into_account;

    ImageView profileImg;

    TextView nickname;
    TextView introduction;
    TextView name;
    TextView id;
    TextView password;
    TextView account;
    Button profileBtn;
    Button withdraw;

    String userid;

    HashMap<String, String> setup_map = new HashMap<>();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.mypage_setup_profile, container, false);

        GlobalApplication myApp = (GlobalApplication) getActivity().getApplication();
        userid = myApp.getGlobalString();

        into_pw = rootView.findViewById(R.id.setup_into_pw);
        into_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PasswordModify.class);
                intent.putExtra("hashmap", setup_map);
                startActivity(intent);
            }
        });

        into_account = rootView.findViewById(R.id.setup_into_account);
        into_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MypageBank.class);
                intent.putExtra("hashmap", setup_map);
                startActivity(intent);
            }
        });

        profileImg=rootView.findViewById(R.id.profile_Image);
        nickname = rootView.findViewById(R.id.profile_nickname);
        introduction = rootView.findViewById(R.id.profile_introduction);
        name = rootView.findViewById(R.id.profile_name);
        id = rootView.findViewById(R.id.profile_id);
        password = rootView.findViewById(R.id.profile_pw);
        account = rootView.findViewById(R.id.profile_account);
        profileBtn = rootView.findViewById(R.id.profile_modify_send);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = local_url + "/yejin/mypage_profile_modify?" +
                        "id=" + userid+
                        "&nickname="+nickname.getText()+
                        "&introduce="+introduction.getText();

                ProfileNetworkTask profileNetworkTask = new ProfileNetworkTask(url, null);
                profileNetworkTask.execute();

                Toast.makeText(getActivity(), "프로필 정보 변경이 완료되었습니다. ", Toast.LENGTH_SHORT);
            }
        });

        withdraw = rootView.findViewById(R.id.profile_withdraw);
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity());
                alert_confirm.setMessage("정말 계정을 삭제 할까요?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getActivity(), "계정이 삭제 되었습니다.", Toast.LENGTH_LONG).show();

                                        startActivity(new Intent(getActivity(), Login.class));
                                    }
                                });

                    }
                });
                alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "취소", Toast.LENGTH_LONG).show();
                    }
                });
                alert_confirm.show();
            }
        });
        String url = local_url + "/yejin/mypage_main_list?member_id=" + userid;

        ProfileNetworkTask profileNetworkTask = new ProfileNetworkTask(url, null);
        profileNetworkTask.execute();

        return rootView;
    }

    // 내 메모 추가
    public class ProfileNetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public ProfileNetworkTask(String url, ContentValues values) {

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

                    JSONArray MemberList = jsonObject.getJSONArray("List");

                    for (int i = 0; i < MemberList.length(); i++) {

                        JSONObject ListObject = MemberList.getJSONObject(i);

                        String.valueOf(ListObject.getInt("member_no"));
                        ListObject.getString("member_id");
                        ListObject.getString("member_pw");
                        ListObject.getString("member_name");
                        ListObject.getString("member_nickname");
                        ListObject.getString("member_account");
                        ListObject.getString("member_introduction");
                        ListObject.getString("member_blacklist");
                        ListObject.getString("member_black_date");
                        ListObject.getString("member_black_reason");
                        ListObject.getString("member_withdraw");
                        ListObject.getString("member_joindate");
                        ListObject.getString("member_last_access");
                        ListObject.getString("member_exp");
                        ListObject.getString("member_grade");
                        ListObject.getString("member_profile_image");

                        nickname.setText( ListObject.getString("member_nickname"));

                        if(ListObject.getString("member_introduction").equals("null")){
                            account.setText( "");
                        }else {
                            introduction.setText(ListObject.getString("member_introduction"));
                        }

                        name.setText(ListObject.getString("member_name"));

                        id.setText( ListObject.getString("member_id"));
                        setup_map.put("id", ListObject.getString("member_id") );

                        password.setText(ListObject.getString("member_pw"));
                        setup_map.put("pw", ListObject.getString("member_pw") );

                        if(ListObject.getString("member_account").equals("null")){
                            account.setText( "계좌를 등록해 주세요");
                        }else {
                            account.setText( "계좌 변경하기");
                        }
                        String imageurl = local_url + "/";
                        imageurl = imageurl + ListObject.getString("member_profile_image") + ".jpg";

                        FileUploadNetworkTask fileUploadNetworkTask = new FileUploadNetworkTask(imageurl, null);
                        fileUploadNetworkTask.execute();

                        if (getActivity().isFinishing()) {
                            return;
                        }
                    }
                }
            } catch (JSONException e) {
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

            return result;
        }

        @Override
        protected void onPostExecute(Bitmap json) {
            super.onPostExecute(json);
            profileImg.setImageBitmap(json);
            profileImg.setBackground(new ShapeDrawable(new OvalShape()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                profileImg.setClipToOutline(true);
            }

            if (getActivity().isFinishing()) {
                return;
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
        ((MainActivity)context).setOnKeyBackPressedListener(this);
    }
}

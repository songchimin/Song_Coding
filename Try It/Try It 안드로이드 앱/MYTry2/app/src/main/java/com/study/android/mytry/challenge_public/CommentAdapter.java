package com.study.android.mytry.challenge_public;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.study.android.mytry.R;
import com.study.android.mytry.login.GlobalApplication;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.main.MainActivity;
import com.study.android.mytry.mypage.setup.ComplaintDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.study.android.mytry.challenge_public.Challenge_Content.comment_listView;
import static com.study.android.mytry.challenge_public.CreationMain.challenge_map;
import static com.study.android.mytry.login.Intro.local_url;
import static com.study.android.mytry.main.Fragment_Search.Master_nickName;


public class CommentAdapter extends BaseAdapter {
    private static final String TAG = "lecture";

    Context context;
    ArrayList<CommentItem> items = new ArrayList<>();

    ListView listView;
    String userid;
    ImageView profile_Image;

    public CommentAdapter(Context context) {

        this.context = context;

        GlobalApplication myApp = (GlobalApplication) context.getApplicationContext();
        userid = myApp.getGlobalString();
    }

    public void addItem(CommentItem item) {
        items.add(item);
        System.out.println(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

        @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommentView view = null;

        if (convertView == null) {
            view = new CommentView(context);
        } else {
            view = (CommentView) convertView;
        }

        final CommentItem item = items.get(position);
        view.setUsername(item.getUsername());
        view.setContent(item.getContent());
        view.setWriteTime(item.getWriteTime());
        view.setLikeCount(String.valueOf(item.getLikeCount()));

        // 댓글 프로필 사진 설정
        profile_Image = view.findViewById(R.id.challenge_comment_item_profile_imageView);

        String imageurl = local_url + "/";
      //  imageurl = imageurl + mypage_map.get("member_profile_image") + ".jpg";

//        FileUploadNetworkTask fileUploadNetworkTask = new FileUploadNetworkTask(imageurl, null);
//        fileUploadNetworkTask.execute();

        // 좋아요 버튼 처리
        final Button like_btn = view.findViewById(R.id.public_comment_good);
        like_btn.setFocusable(false);

        System.out.println("좋아요"+item.getLikeExist());
        if (item.getLikeExist() == 1) {
            like_btn.setBackgroundResource(R.drawable.like_true);
        } else if (item.getLikeExist() == 0) {
            like_btn.setBackgroundResource(R.drawable.like_false);
        }

        final CommentView finalView = view;
        like_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String str = "content: " + item.getContent();
                Log.d(TAG, str);

                Toast.makeText(context, str, Toast.LENGTH_LONG).show();

                finalView.setLikeCount(String.valueOf(item.getLikeCount()));

                String url = local_url + "/yejin/public_comment_like";

                String msg = "?challenge_num=" + item.getChallengeNum() +
                        "&member_id=" + userid +
                        "&comment_num=" + item.getCommentNum();

                Log.d("lecture", msg);
                url = url + msg;
                Log.d("lecture", url);
                challenge_map.put("num",String.valueOf(item.getChallengeNum()));

                NetworkTask1 networkTask = new NetworkTask1(url, null);
                networkTask.execute();

                if (item.getLikeExist() == 1) {
                    like_btn.setBackgroundResource(R.drawable.like_true);
                } else if (item.getLikeExist() == 0) {
                    like_btn.setBackgroundResource(R.drawable.like_false);
                }
            }
        });


        LinearLayout complainBtn = view.findViewById(R.id.challenge_item);
        complainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, v);
                MenuInflater inflater = popup.getMenuInflater();

                Menu menu = popup.getMenu();

                inflater.inflate(R.menu.context_menu, menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {

                        switch(menuitem.getItemId()) {

                            case R.id.delete:
                                Toast.makeText(context, "정상적으로 삭제 되었습니다.", Toast.LENGTH_SHORT);
                                break;

                            case R.id.complaint:
                                // 다이얼로그 창 생성
                                ComplaintDialog complaintDialog = new ComplaintDialog(context,item.getCommentNum());
                                complaintDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                                complaintDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                complaintDialog.show();

                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });

            return view;
    }

    // 통신 = insert, delete?
    public class NetworkTask1 extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask1(String url, ContentValues values) {

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
            // textView.setText(json);

            CommentAdapter commentAdapter = new CommentAdapter(context);

            JSONObject jsonObject = null;
            try {

                jsonObject = new JSONObject(json);

                JSONArray ListArray = jsonObject.getJSONArray("List");

                CommentItem[] commentItems = new CommentItem[ListArray.length()];

                for (int i = 0; i < ListArray.length(); i++) {

                    JSONObject ListObject = ListArray.getJSONObject(i);

                    Log.d("lecture", String.valueOf(ListObject.getInt("comment_num")));
                    Log.d("lecture", String.valueOf(ListObject.getInt("challenge_num")));
                    Log.d("lecture", ListObject.getString("member_id"));
                    Log.d("lecture", ListObject.getString("comment_content"));
                    Log.d("lecture", "있니없니"+ String.valueOf(ListObject.getInt("commment_like_exist")));
                    Log.d("lecture", String.valueOf(ListObject.getInt("commment_like_count")));

                    if (challenge_map.get("num").equals(String.valueOf(ListObject.getInt("challenge_num")))) {

                        commentItems[i] = new CommentItem(ListObject.getInt("comment_num"),ListObject.getInt("challenge_num"), ListObject.getString("member_id"), ListObject.getString("comment_content"), ListObject.getString("commment_date"), ListObject.getInt("commment_like_count"), ListObject.getInt("commment_like_exist"));

                        commentAdapter.addItem(commentItems[i]);
                    }
                }

                comment_listView.setAdapter(commentAdapter);

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
            profile_Image.setImageBitmap(json);
        }
    }


}

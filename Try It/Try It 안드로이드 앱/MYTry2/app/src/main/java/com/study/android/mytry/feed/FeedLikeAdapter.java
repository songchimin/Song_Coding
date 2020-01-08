package com.study.android.mytry.feed;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.ColorInt;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.study.android.mytry.R;
import com.study.android.mytry.challenge_public.CommentItem;
import com.study.android.mytry.challenge_public.CommentView;
import com.study.android.mytry.login.RequestHttpConnection;
import com.study.android.mytry.main.Fragment_Mypage;
import com.study.android.mytry.mypage.setup.ComplaintDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.study.android.mytry.feed.FeedComment.feed_comment_list;
import static com.study.android.mytry.feed.FeedComment.feed_map;
import static com.study.android.mytry.feed.FeedLike.feed_like_list;
import static com.study.android.mytry.feed.FeedLike.feed_like_map;
import static com.study.android.mytry.login.Intro.local_url;


public class FeedLikeAdapter extends BaseAdapter {
    private static final String tag = "selee";

    Context context;
    ArrayList<FeedLikeItem> items = new ArrayList<>();

    public FeedLikeAdapter(Context context) {

        this.context = context;
    }

    public void addItem(FeedLikeItem item) {
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

        FeedLikeView view = null;

        if (convertView == null) {
            view = new FeedLikeView(context);
        } else {
            view = (FeedLikeView) convertView;
        }

        final FeedLikeItem item = items.get(position);
        view.setFollw_profile(feed_like_map.get("member_profile_image"));
        view.setFollw_namw(feed_like_map.get("member_nickname"));

        // 팔로우 버튼 처리
        final Button follow_btn = view.findViewById(R.id.followbtn);
        follow_btn.setFocusable(false);

        if (item.getFollowExist() == 1) {
            follow_btn.setSelected(true);
            follow_btn.setText("팔로잉");
        } else if (item.getFollowExist() == 0) {
            follow_btn.setSelected(false);
            follow_btn.setText("팔로우");
        }

        follow_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                String url = local_url + "/selee/follow";

                String msg = "?my_id=" + feed_like_map.get("my_id") +
                        "&member_id=" + item.getFeedLikeUserid()+
                        "&feed_num=" + feed_like_map.get("feed_num");

                Log.d(tag, msg);
                url = url + msg;
                Log.d(tag, url);

                NetworkTask1 networkTask = new NetworkTask1(url, null);
                networkTask.execute();

                if (item.getFollowExist() == 1) {
                    follow_btn.setSelected(true);
                    follow_btn.setText("팔로잉");
                } else if (item.getFollowExist() == 0) {
                    follow_btn.setSelected(false);
                    follow_btn.setText("팔로우");
                }
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

            FeedLikeAdapter feedLikeAdapter= new FeedLikeAdapter(context);

            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray ListArray = jsonObject.getJSONArray("List");

                FeedLikeItem[] feedLikeItems = new FeedLikeItem[ListArray.length()];

                // 생성
                for (int i = 0; i < ListArray.length(); i++) {

                    JSONObject ListObject = ListArray.getJSONObject(i);

                    feedLikeItems[i] = new FeedLikeItem(String.valueOf(ListObject.getString("feedLikeUserid")), ListObject.getInt("followExist"));
                    feedLikeAdapter.addItem(feedLikeItems[i]);

                    Log.d(tag, "feedLikeUserid     "+ListObject.getString("feedLikeUserid"));
                }
                feed_like_list.setAdapter(feedLikeAdapter);
                Log.d(tag, "commentAdapter 갯수 " + String.valueOf(feedLikeAdapter.getCount()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.study.android.mytry.feed;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
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
import com.study.android.mytry.main.Fragment_Feed;
import com.study.android.mytry.mypage.setup.ComplaintDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.study.android.mytry.feed.FeedComment.feed_comment_list;
import static com.study.android.mytry.feed.FeedComment.feed_map;

import static com.study.android.mytry.login.Intro.local_url;


public class FeedCommentAdapter extends BaseAdapter {
    private static final String tag = "selee";

    Context context;
    ArrayList<FeedCommentItem> items = new ArrayList<>();

    public FeedCommentAdapter(Context context) {

        this.context = context;
    }

    public void addItem(FeedCommentItem item) {
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

        FeedCommentView view = null;

        if (convertView == null) {
            view = new FeedCommentView(context);
        } else {
            view = (FeedCommentView) convertView;
        }

        final FeedCommentItem item = items.get(position);

        view.setUsername(item.getContent());
        view.setWriteTime(item.getWriteTime());
        view.setLikeCount(String.valueOf(item.getLikeCount()));
        view.setContent(item.getUserNicname());
        view.setProfile_imageView(item.getUserProfile());

        // 좋아요 버튼 처리
        final Button like_btn = view.findViewById(R.id.public_comment_good2);
        like_btn.setFocusable(false);

        if (item.getLikeExist() == 1) {
            like_btn.setBackgroundResource(R.drawable.like_true);
        } else if (item.getLikeExist() == 0) {
            like_btn.setBackgroundResource(R.drawable.like_false);
        }

        final FeedCommentView finalView = view;
        like_btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String str = "content: " + item.getContent();
                Log.d(tag, str);

                Toast.makeText(context, str, Toast.LENGTH_LONG).show();

                finalView.setLikeCount(String.valueOf(item.getLikeCount()));

                String url = local_url + "/selee/feed_comment_like";

                String msg = "?feed_num=" + feed_map.get("num") +
                        "&member_id=" + feed_map.get("user") +
                        "&comment_num=" + item.getCommentNum();
                Log.d("lecture", msg);
                url = url + msg;
                Log.d("lecture", url);

                NetworkTask1 networkTask = new NetworkTask1(url, null);
                networkTask.execute();

                if (item.getLikeExist() == 1) {
                    like_btn.setBackgroundResource(R.drawable.like_true);
                } else if (item.getLikeExist() == 0) {
                    like_btn.setBackgroundResource(R.drawable.like_false);
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
            // textView.setText(json);

            FeedCommentAdapter commentAdapter = new FeedCommentAdapter(context);

            JSONObject jsonObject = null;
            try {

                jsonObject = new JSONObject(json);

                JSONArray ListArray = jsonObject.getJSONArray("List");

                FeedCommentItem[] commentItems = new FeedCommentItem[ListArray.length()];

                for (int i = 0; i < ListArray.length(); i++) {

                    JSONObject ListObject = ListArray.getJSONObject(i);

                    Log.d("lecture", String.valueOf(ListObject.getInt("comment_num")));
                    Log.d("lecture", String.valueOf(ListObject.getInt("feed_num")));
                    Log.d("lecture", ListObject.getString("member_id"));
                    Log.d("lecture", ListObject.getString("comment_content"));
                    Log.d("lecture", ListObject.getString("commment_date"));
                    Log.d("lecture", String.valueOf(ListObject.getInt("commment_like_count")));

                    if (feed_map.get("num").equals(String.valueOf(ListObject.getInt("feed_num")))) {
                        commentItems[i] = new FeedCommentItem(ListObject.getInt("comment_num"), ListObject.getString("member_id"), ListObject.getString("comment_content"), ListObject.getString("commment_date"), ListObject.getInt("commment_like_count"), ListObject.getInt("commment_like_exist"));
                        commentAdapter.addItem(commentItems[i]);
                    }
                }
                feed_comment_list.setAdapter(commentAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}

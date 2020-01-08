package com.study.android.mytry.feed;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.study.android.mytry.R;

;

public class FeedCommentView extends LinearLayout {

    TextView username;
    TextView content;
    TextView writeTime;
    Button likeTo;
    TextView likeCount;
    ImageView profile_imageView;
    String url;

    public FeedCommentView(Context context){
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       inflater.inflate(R.layout.feed_comment_item,this, true);

        username = (TextView) findViewById(R.id.public_comment_username2);
        content = (TextView) findViewById(R.id.public_comment_content2);
        writeTime = (TextView) findViewById(R.id.public_comment_time2);
        likeTo = (Button) findViewById(R.id.public_comment_good2);
        likeCount = (TextView) findViewById(R.id.public_comment_good_count2);
        profile_imageView = (ImageView) findViewById(R.id.challenge_comment_item_profile_imageView2);

    }

    public void setUsername(String str){
        username.setText(str);
    }
    public void setContent(String str){
        content.setText(str);
    }
    public void setWriteTime(String str){
        writeTime.setText(str);
    }
    public void setLikeCount(String str){
        likeCount.setText(str);
    }

    public void setProfile_imageView(String str) { url = str;
        String profile_url = url + "/" + str + ".jpg";
        Picasso.with(getContext())
                .load(profile_url)
                .transform(new CircleTransform())        // 이미지 후 처리(여기선 둥근이미지)
                .into(profile_imageView);
    }
}

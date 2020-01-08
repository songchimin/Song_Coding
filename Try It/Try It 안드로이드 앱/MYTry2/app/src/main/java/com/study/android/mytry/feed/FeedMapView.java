package com.study.android.mytry.feed;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.study.android.mytry.R;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import static com.study.android.mytry.login.Intro.local_url;

public class FeedMapView extends LinearLayout{
    private static final String tag="feed";

    ImageView feed_map_item_profile_imageView;
    TextView feed_map_item_email_textView;
    TextView feed_map_item_date_textView;
    TextView feed_map_item_title_textView;
    TextView feed_map_item_comment;
    RelativeLayout feed_map_view;
    ImageView feed_main_image;
    TextView feed_map_item_likeCount_textView;
    TextView feed_item_comment_userNicname;
    TextView feed_item_comment;
    TextView feed_map_item_commentCount_textView;
    ImageView feed_item_myprofile_imageView;
    EditText feed_item_comment_editText;

    Context context;
    String profile = "";
    String myprofile = "";

    public FeedMapView(Context context) {
        super(context);
        this.context=context;

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.feed_item_map, this, true);

        feed_map_item_profile_imageView = (ImageView)findViewById(R.id.feed_map_item_profile_imageView);
        feed_map_item_email_textView = (TextView)findViewById(R.id.feed_map_item_email_textView);
        feed_map_item_date_textView = (TextView)findViewById(R.id.feed_map_item_date_textView);
        feed_map_item_title_textView = (TextView)findViewById(R.id.feed_map_item_title_textView);
        feed_map_item_comment = (TextView)findViewById(R.id.feed_map_item_comment);
        feed_map_view = (RelativeLayout)findViewById(R.id.feed_map_view);
        feed_main_image = (ImageView)findViewById(R.id.feed_main_image);
        feed_map_item_likeCount_textView = (TextView)findViewById(R.id.feed_map_item_likeCount_textView);
//        feed_item_comment_userNicname = (TextView)findViewById(R.id.feed_item_comment_userNicname);
//        feed_item_comment = (TextView)findViewById(R.id.feed_item_comment);
        feed_map_item_commentCount_textView = (TextView)findViewById(R.id.feed_map_item_commentCount_textView);
        feed_item_myprofile_imageView = (ImageView)findViewById(R.id.feed_item_myprofile_imageView);
        feed_item_comment_editText = (EditText)findViewById(R.id.feed_item_comment_editText);
    }

    public void setFeed_map_item_profile_imageView (String str) { profile = str;
        String profile_url = local_url + "/" + profile + ".jpg";
        Picasso.with(getContext())
                .load(profile_url)
                .transform(new CircleTransform())        // 이미지 후 처리(여기선 둥근이미지)
                .into(feed_map_item_profile_imageView);
    }

    public void setFeed_map_item_email_textView(String str) {
        feed_map_item_email_textView.setText(str);
    }

    public void setFeed_map_item_date_textView(String str) {
        this.feed_map_item_date_textView.setText(str);
    }

    public void setFeed_map_item_title_textView(String str) {
        feed_map_item_title_textView.setText(str);
    }

    public void setFeed_map_item_comment(String str) {
        feed_map_item_comment.setText(str);
    }

    public void setFeed_item_main_imageView(String str) {
        Log.d(tag, "ssssssssssss"+str);
        String main_image = str;
        if(main_image != null && !main_image.equals("map")) {

//            Picasso.with(Context)        // Context
//                    .load(URL)            // 로드할 이미지 경로(로컬도 가능합니다)
//                    .placeholder(R.drawable.image_holder)    // 다운로드 중 표시할 이미지
//                    .error(R.drawable.image_error)           // 다운로드 실패시 표시할 이미지
//                    .into(ImageView);    // 이미지를 담을 ImageView 객체

            String url = local_url+"/Feed/"+main_image+".jpg";
            Picasso.with(getContext()).load(url).into(feed_main_image);
        } else {
            String url = local_url+"/Feed/"+main_image+".png";
            Picasso.with(getContext()).load(url).into(feed_main_image);
        }
    }

    public void setFeed_map_item_likeCount_textView(int str) {
        feed_map_item_likeCount_textView.setText("좋아요 "+str+"개");
    }

    public void setFeed_item_comment_userNicname(String str) {
        feed_item_comment_userNicname.setText(str);
    }

    public void setFeed_map_item_commentCount_textView(int str) {
        feed_map_item_commentCount_textView.setText("댓글 "+str+"개 모두보기");
    }

    public void setFeed_item_myprofile_imageView(String str) { myprofile = str;
        String profile_url = local_url + "/" + myprofile + ".jpg";
        Picasso.with(getContext())
                .load(profile_url)
                .transform(new CircleTransform())        // 이미지 후 처리(여기선 둥근이미지)
                .into(feed_item_myprofile_imageView);
    }

    public EditText getFeed_item_comment_editText() {
        return feed_item_comment_editText;
    }

    public void setFeed_item_comment_editText(String str) {
        feed_item_comment_editText.setText(str);
    }

}
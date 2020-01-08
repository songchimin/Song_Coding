package com.study.android.mytry.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.study.android.mytry.R;

;import static com.study.android.mytry.login.Intro.local_url;

public class FeedLikeView extends LinearLayout {

    ImageView follw_profile;
    TextView follw_namw;
    Button followbtn;
    String profile;

    public FeedLikeView(Context context){
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       inflater.inflate(R.layout.follow_item,this, true);

        follw_profile = (ImageView) findViewById(R.id.follw_profile);
        follw_namw = (TextView) findViewById(R.id.follw_namw);
        followbtn = (Button) findViewById(R.id.followbtn);
    }

    public void setFollw_profile (String str) { profile = str;
        String profile_url = local_url + "/" + str + ".jpg";
        Picasso.with(getContext())
                .load(profile_url)
                .transform(new CircleTransform())        // 이미지 후 처리(여기선 둥근이미지)
                .into(follw_profile);
    }

    public void setFollw_namw(String str){
        follw_namw.setText(str);
    }

}

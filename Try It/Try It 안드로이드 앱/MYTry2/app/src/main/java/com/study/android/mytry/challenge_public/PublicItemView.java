package com.study.android.mytry.challenge_public;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.android.mytry.R;

public class PublicItemView extends LinearLayout {

    TextView category;
    TextView title;
    LinearLayout likebtn;
    TextView like_count;

    public PublicItemView(Context context){
      super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.challenge_public_item,this, true);

        category = (TextView) findViewById(R.id.public_item_category);
        title = (TextView) findViewById(R.id.public_item_name);
        likebtn = (LinearLayout) findViewById(R.id.public_item_good);
        like_count = (TextView) findViewById(R.id.public_like_count);
    }

    public void setCategory(String str){
        category.setText(str);
    }
    public void setTitle(String str){
        title.setText(str);
    }
    public void setLikeCount(int likecount){
        like_count.setText(String.valueOf(likecount)+"개");
    }
}

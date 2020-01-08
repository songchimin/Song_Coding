package com.study.android.mytry.chat;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.study.android.mytry.R;

import static com.study.android.mytry.login.Intro.local_url;

public class chat_roomView extends LinearLayout{

    ImageView chat_room_image;
    TextView chat_room_title;
    TextView chat_room_previous;
    TextView chat_room_previous_time;
    TextView chat_room_count;
    TextView chat_room_participant_count;

    //머여
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    public chat_roomView(Context context){
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.chat_room_item_view,this, true);

        chat_room_image =  findViewById(R.id.chat_room_image);
        GradientDrawable drawable = (GradientDrawable) context.getDrawable(R.drawable.background_rounding);

        chat_room_image.setBackground(drawable);
        chat_room_image.setClipToOutline(true);

        chat_room_title =  findViewById(R.id.chat_room_title);
        //chat_room_previous =  findViewById(R.id.chat_room_previous);
        chat_room_previous_time =  findViewById(R.id.chat_room_previous_time);
        chat_room_count =  findViewById(R.id.chat_room_count);
        chat_room_participant_count =  findViewById(R.id.chat_room_participant_count);

    }


    public void setChat_room_image(String chat_room_image) {
        String url = local_url+"/"+chat_room_image+".jpg";
        Picasso.with(getContext()).load(url).into(this.chat_room_image);
    }

    public void setChat_room_title(String chat_room_title) {
        this.chat_room_title.setText(chat_room_title);
    }

    public void setChat_room_previous(String chat_room_previous) {

    }

    public void setChat_room_previous_time(String chat_room_previous_time) {

    }

    public void setChat_room_count(String chat_room_count) {

    }

    public void setChat_room_participant_count(String chat_room_participant_count) {
        this.chat_room_participant_count.setText(chat_room_participant_count);
    }
}

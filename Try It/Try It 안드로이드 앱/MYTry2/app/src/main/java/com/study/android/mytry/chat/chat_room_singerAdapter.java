package com.study.android.mytry.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.study.android.mytry.R;

import java.util.ArrayList;

public class chat_room_singerAdapter extends BaseAdapter {

    private static final String TAG="lecture";

    Context context;

    ArrayList<chat_room_singerItem> items = new ArrayList<>();

    Activity activity;

    public chat_room_singerAdapter(Context context){

        this.context = context;

        if (context instanceof Activity)
            activity = (Activity) context;
    }

    public void addItem(chat_room_singerItem item){
        items.add(item);
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public Object getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    //머여

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent){

        chat_roomView view = null;

        if(convertView==null){
            view = new chat_roomView(context);
        }else {
            view = (chat_roomView) convertView;
        }

        final chat_room_singerItem item = items.get(position);

        view.setChat_room_image(item.getChat_room_image());
        view.setChat_room_title(item.getChat_room_title());
        view.setChat_room_participant_count(item.getChat_room_participant_count()+"명 참가중");

        view.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){

                Intent intent = new Intent(context, private_chat.class);
                intent.putExtra("chat_room_num", item.getChat_room_num()); // 이 메서드를 통해 데이터를 전달합니다.
                intent.putExtra("chat_room_title", item.getChat_room_title());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

                activity.overridePendingTransition(R.anim.rightin_activity,R.anim.not_move_activity);
            }
        });

        return view;
    }



}

package com.study.android.mytry.chat;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.study.android.mytry.R;

import java.util.ArrayList;

import static com.study.android.mytry.chat.chat_room.nickname;

public class chat_content_singerAdapter extends BaseAdapter {

    private static final String TAG="lecture";
    Context context;

    private LayoutInflater _inflater;
    private ArrayList list;
    private int _layout;
    private int _layout2;



    public chat_content_singerAdapter(Context context, int layout, int layout2, ArrayList list){
        _inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        _layout = layout;
        _layout2 = layout2;
    }


    @Override
    public int getCount(){
//        return items.size();
        return list.size();
    }

    @Override
    public Object getItem(int position){
        return "";
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    //머여

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

//        chat_contentView view = null;
        chat_PersonViewHolder viewHolder;
        viewHolder = new chat_PersonViewHolder();

        ChatDTO chatDTO = (ChatDTO)list.get(position);
//        TextView content;
//        TextView name;
//        TextView time;

        if(convertView==null){

            if(chatDTO.getUserName().equals(nickname)){
                convertView = _inflater.inflate(_layout2, parent,false);
                viewHolder.content = convertView.findViewById(R.id.chat_content);
                viewHolder.time = convertView.findViewById(R.id.chat_content_time);
                viewHolder.name = convertView.findViewById(R.id.chat_content_name);
                convertView.setTag(viewHolder);
            }else{
                convertView = _inflater.inflate(_layout, parent,false);
                viewHolder.content = convertView.findViewById(R.id.chat_content);
                viewHolder.time = convertView.findViewById(R.id.chat_content_time);
                viewHolder.name = convertView.findViewById(R.id.chat_content_name);
                convertView.setTag(viewHolder);
            }

        }else{
            if(chatDTO.getUserName().equals(nickname)){
                convertView = _inflater.inflate(_layout2, parent,false);
                viewHolder.content = convertView.findViewById(R.id.chat_content);
                viewHolder.time = convertView.findViewById(R.id.chat_content_time);
                viewHolder.name = convertView.findViewById(R.id.chat_content_name);
                convertView.setTag(viewHolder);
            }else{
                convertView = _inflater.inflate(_layout, parent,false);
                viewHolder.content = convertView.findViewById(R.id.chat_content);
                viewHolder.time = convertView.findViewById(R.id.chat_content_time);
                viewHolder.name = convertView.findViewById(R.id.chat_content_name);
                convertView.setTag(viewHolder);
            }
        }



        viewHolder.content.setText(chatDTO.getMessage());
        String str = chatDTO.getTime().substring(0,5);
        viewHolder.time.setText(str);
        viewHolder.name.setText(chatDTO.getUserName());


        return convertView;
    }
}


package com.study.android.mytry.mypage.setup;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.study.android.mytry.R;
import com.study.android.mytry.challenge_private.CreationFirst;

import java.util.ArrayList;

public class ComplaintAdapter extends BaseAdapter {
    private static final String TAG = "lecture";

    Context context;
    ArrayList<ComplaintItem> items = new ArrayList<>();


    public ComplaintAdapter(Context context) {

        this.context = context;
    }

    public void addItem( ComplaintItem item) {
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

        ComplaintItemView view = null;

        if (convertView == null) {
            view = new ComplaintItemView(context);
        } else {
            view = (ComplaintItemView) convertView;
        }

        final ComplaintItem item = items.get(position);
        view.setComment_title("댓글 신고 "+String.valueOf(item.getComment_num()));

        if(item.getComplaint_state().equals("0")){
            view.setState("대기중");
        }else if(item.getComplaint_state().equals("1")){
            view.setState("처리중");
        }else if(item.getComplaint_state().equals("2")){
            view.setState("처리완료");
        }
        view.setDate(item.getComplaint_date());

        ImageView imageview = view.findViewById(R.id.imageViewh13);
        imageview.setBackgroundResource(R.drawable.reply_complaint);

        LinearLayout complaint_list = view.findViewById(R.id.complaint_Item);
        complaint_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ComplaintDetail.class);
                intent.putExtra("comment_num",String.valueOf(item.getComment_num()));
                intent.putExtra("state",item.getComplaint_state());
                intent.putExtra("date",item.getComplaint_date());
                intent.putExtra("content",item.getComplaint_content());
                intent.putExtra("reply",item.getComplaint_reply());
                intent.putExtra("reply_date",item.getComplaint_reply_date());
                             context.startActivity(intent);
            }
        });

        return view;
    }

}

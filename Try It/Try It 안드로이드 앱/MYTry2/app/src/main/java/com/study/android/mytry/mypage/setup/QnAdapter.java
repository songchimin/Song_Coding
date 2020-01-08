package com.study.android.mytry.mypage.setup;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.study.android.mytry.R;
import com.study.android.mytry.challenge_private.CreationQrCode;
import com.study.android.mytry.challenge_public.FileLoadConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;


public class QnAdapter extends BaseAdapter {
    private static final String TAG = "lecture";

    Context context;
    ArrayList<QnAItem> items = new ArrayList<>();
    LinearLayout linearLayout;

    HashMap<String, String> map = new HashMap<>();

    public QnAdapter(Context context) {
        this.context = context;
    }

    public void addItem(QnAItem item) {
        items.add(item);
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

        QnAItemView view = null;

        if (convertView == null) {
            view = new QnAItemView(context);
        } else {
            view = (QnAItemView) convertView;
        }

        final QnAItem item = items.get(position);
        view.setTitle(item.getQuestion_title());
        view.setState(item.getQuestion_state());
        view.setDate(item.getQuestion_date());

        final LinearLayout linearLayout = view.findViewById(R.id.qna_Item);

        linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, QnAResult.class);

                    map.put("question_num", String.valueOf(item.getQuestion_num()));
                    map.put("member_id", item.getMember_id());
                    map.put("manager_id", item.getManager_id());
                    map.put("question_title", item.getQuestion_title());
                    map.put("question_Content", item.getQuestion_Content());
                    map.put("question_Picture", item.getQuestion_Picture());
                    map.put("question_answer", item.getQuestion_answer());
                    map.put("question_divide", item.getQuestion_divide());

                    map.put("question_state", item.getQuestion_state());
                    map.put("question_date", item.getQuestion_date());
                    map.put("answer_date", item.getAnswer_date());

                    intent.putExtra("hashmap", map);
                    context.startActivity(intent);
                }
            });

        return view;
    }

}

package com.study.android.mytry.certification;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import com.study.android.mytry.R;
import java.util.ArrayList;


public class CertificateAdapter extends BaseAdapter {
    private static final String tag="selee";

    Context certificate_context;
    ArrayList<CertificateItem> items = new ArrayList<>();

    public CertificateAdapter(Context context) {
        this.certificate_context = context;
    }

    public void addItem(CertificateItem item) {
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

        final CertificateItem item = items.get(position);
        CertificateItemView view = null;

        if(convertView == null){
            view = new CertificateItemView(certificate_context);
        }else {
            view =(CertificateItemView) convertView;
        }

        String rootTime = item.getTime();
        String start_time = rootTime.substring(0,5);
        String end_time = rootTime.substring(6,11);

        view.setTitle(item.getTitle());
        view.setTerm(item.getStart() + " ~ " + item.getEnd());
        view.setFrequency(item.getFrequency());
        view.setTime(start_time+" ~ " +end_time);
        view.setBack_image(item.getImage());





        Log.d(tag,"사진이름"+item.getImage());


        double rate;
        if(item.getCheck_count() != 0) {
            rate = (item.getCheck_count()/item.getAll_count())*100;
        } else {
            rate = 0;
        }

        view.setRate("달성률 : "+String.format("%.1f", rate)+"%");

        if(item.getCheck().equals("1")) {
            view.setOver_image(R.drawable.challenge_liketo);
            view.setOver_text("오늘 끝!");
        } else {
            view.setOver_image(R.drawable.certificate_camera);
            view.setOver_text("인증하기");
        }

        //********************************
        //리스트뷰안의 버튼 클릭 이벤트 처리
        FrameLayout frame = view.findViewById(R.id.certificate_item_frame);
        frame.setFocusable(false);
        frame.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                Intent intent = null;
                if(item.getCheck().equals("0")) {
                    if(item.getType().equals("카메라")) {
                        intent = new Intent(certificate_context, Certificate_camera.class); // 다음넘어갈 화면
                    } else if(item.getType().equals("갤러리")) {
                        intent = new Intent(certificate_context, Certificate_gallery.class); // 다음넘어갈 화면
                    } else if(item.getType().equals("음성")) {
                        intent = new Intent(certificate_context, Certificate_voice.class); // 다음넘어갈 화면
                    }  else if(item.getType().equals("지도")) {
                        intent = new Intent(certificate_context, Certificate_map.class); // 다음넘어갈 화면
                    } else if(item.getType().equals("영화"))  {
                        intent = new Intent(certificate_context, Certificate_gallery.class); // 다음넘어갈 화면
                    }
                } else {
                    intent = new Intent(certificate_context, Certificate_detail.class); // 다음넘어갈 화면
                }

                intent.putExtra("challenge_num", item.getNum());
                intent.putExtra("certificate_check", item.getCheck());
                intent.putExtra("challenge_type", item.getType());
                intent.putExtra("challenge_title", item.getTitle());
                intent.putExtra("challenge_start", item.getStart());
                intent.putExtra("challenge_end", item.getEnd());
                intent.putExtra("challenge_frequency", item.getFrequency());
                intent.putExtra("challenge_time", item.getTime());
                intent.putExtra("challenge_first_image", item.getImage());

                certificate_context.startActivity(intent);
            }
        });

        return view;
    }
}
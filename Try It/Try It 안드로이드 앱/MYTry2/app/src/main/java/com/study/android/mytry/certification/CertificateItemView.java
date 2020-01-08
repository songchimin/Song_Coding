package com.study.android.mytry.certification;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.study.android.mytry.R;

import static com.study.android.mytry.login.Intro.local_url;

public class CertificateItemView extends LinearLayout {

    TextView title;
    TextView term;
    TextView frequency;
    TextView time;
    TextView rate;

    ImageView back_image;
    ImageView over_image;
    TextView over_text;

    String test="";

    public CertificateItemView(Context context) {
        super(context);

        LayoutInflater inflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.certification_main_item, this, true);

        title = findViewById(R.id.certification_item_title);
        term = findViewById(R.id.certification_item_term);
        frequency = findViewById(R.id.certification_item_frequency);
        time = findViewById(R.id.certification_item_time);
        rate = findViewById(R.id.certification_item_rate);

        back_image = findViewById(R.id.certification_item_back_imageView);
        over_image = findViewById(R.id.certification_item_over_imageView);
        over_text = findViewById(R.id.certification_item_over_textView);
    }

    public void setTitle(String str) {
        title.setText(str);
    }

    public void setTerm(String str) {
        term.setText(str);
    }

    public void setFrequency(String str) {
        frequency.setText(str);
    }

    public void setTime(String str) {
        time.setText(str);
    }

    public void setRate(String str) {
        rate.setText(str);
    }

    public void setBack_image(String str) {test=str;
        String url = local_url+"/"+test+".jpg";
        Picasso.with(getContext()).load(url).into(back_image);
    }

    public void setOver_image(int drawable) {
        over_image.setImageResource(drawable);
    }

    public void setOver_text(String str) {
        over_text.setText(str);
    }
}

package com.study.android.mytry.mypage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.AsyncTask;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.study.android.mytry.R;
import com.study.android.mytry.challenge_public.FileLoadConnection;
import com.study.android.mytry.main.Fragment_Mypage;
import com.study.android.mytry.search.Search_detail;

import java.util.ArrayList;

import static com.study.android.mytry.login.Intro.local_url;

public class MyChallengeAdapter extends BaseAdapter {
    private static final String TAG="lecture";

    Context context;

    ArrayList<MyChallengeItem> items=new ArrayList<>();

    ImageView imageView;

    public MyChallengeAdapter(Context context){

        this.context=context;
    }

    public void addItem(MyChallengeItem item){
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

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent){

        MyChallengeView view=null;

        if(convertView==null){
            view=new MyChallengeView(context);
        }else {
            view=(MyChallengeView) convertView;
        }

        final MyChallengeItem item=items.get(position);

        view.setTitle(item.getTitle());
        view.setCategory(item.getCategory());
        view.setDate(item.getStart()+" ~ "+item.getEnd());
        view.setState(item.getState());

        imageView = view.findViewById(R.id.mypage_challenge_Img);
        String imageurl = local_url + "/";
        imageurl = imageurl + item.getFirst_image() + ".jpg";

        ChallengeImgNetworkTask fileUploadNetworkTask = new ChallengeImgNetworkTask(imageurl, null);
        fileUploadNetworkTask.execute();

        LinearLayout linearLayout = view.findViewById(R.id.mychallenge_item);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Search_detail.class);

                intent.putExtra("challenge_num",item.getNum());
                intent.putExtra("challenge_title",item.getTitle());
                intent.putExtra("challenge_category",item.getCategory());
                intent.putExtra("challenge_type",item.getType());
                intent.putExtra("challenge_frequency",item.getFrequency());
                intent.putExtra("challenge_start",item.getStart());
                intent.putExtra("challenge_end",item.getEnd());
                intent.putExtra("challenge_fee",item.getFee());
                intent.putExtra("challenge_time",item.getChallenge_time());
                intent.putExtra("challenge_detail",item.getDetail());
                intent.putExtra("challenge_first_image",item.getFirst_image());
                intent.putExtra("challenge_state",item.getState());
                intent.putExtra("challenge_public",item.getPub());
                intent.putExtra("challenge_exp",item.getExp());
                intent.putExtra("challenge_host",item.getHost());
                intent.putExtra("alongBtn","yejin");

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return view;
    }

    public class ChallengeImgNetworkTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ContentValues values;

        public ChallengeImgNetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap result; // 요청 결과를 저장할 변수.
            FileLoadConnection requestHttpURLConnection = new FileLoadConnection();
            result = requestHttpURLConnection.request(url); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(Bitmap json) {
            super.onPostExecute(json);
            imageView.setImageBitmap(json);
            imageView.setBackground(new ShapeDrawable(new OvalShape()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageView.setClipToOutline(true);
            }

        }
    }

}

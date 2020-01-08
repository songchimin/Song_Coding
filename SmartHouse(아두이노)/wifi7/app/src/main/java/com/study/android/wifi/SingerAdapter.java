package com.study.android.wifi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SingerAdapter extends RecyclerView.Adapter<SingerAdapter.SingerItemViewHolder> {
    public class SingerItemViewHolder extends RecyclerView.ViewHolder{
        protected TextView viewDate;
        protected TextView viewTime;
        protected ImageView imageView;
        protected TextView viewTemp;
        protected TextView viewWater;

        public SingerItemViewHolder(View view){
            super(view);

            viewDate=view.findViewById(R.id.viewDate);
            viewTime=view.findViewById(R.id.viewWeek);
            imageView=view.findViewById(R.id.imageView);
            viewTemp=view.findViewById(R.id.viewHigh);
            viewWater=view.findViewById(R.id.viewLow);
         }
    }

    Context context;
    ArrayList<SingerItem> items=new ArrayList<>();

    public SingerAdapter(Context context){
        this.context=context;
    }

    public void addItem(SingerItem item){
        items.add(item);
    }

    //RecyclerView 에 새로운 데이터를 보여주기 위해 필요한 ViewHolder를 생성해야 할 때 호출된다.
    public SingerItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.singer_item_view, viewGroup, false);

        SingerItemViewHolder viewHolder=new SingerItemViewHolder(view);

        return viewHolder;
    }


    // Adapter의 특정 위치에 있는 데이터를 보여줘야 할때 호출 됩니다.
    public void onBindViewHolder(@NonNull SingerItemViewHolder viewHolder, int position){
        //데이터 추가 등의 수정 없이 기존 데이터만 사용한다면 다음으로 정렬을 바꾸어 사용할 수 있다

        viewHolder.viewDate.setText(items.get(position).getDate());
        viewHolder.viewTime.setText(items.get(position).getTime());
        viewHolder.imageView.setImageResource(items.get(position).getResId());
        viewHolder.viewTemp.setText(items.get(position).getTemp());
        viewHolder.viewWater.setText(items.get(position).getWater());

        final int num = position;

    }

    @Override
    public int getItemCount(){
        return (null != items ? items.size() : 0);
    }

    public Object getItem(int position){
        return items.get(position);
    }

}

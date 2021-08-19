package com.example.e_shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.e_shop.R;
import com.example.e_shop.model.SliderModel;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderHolder> {
    Context context;
    List<SliderModel> sliderList;

    public SliderAdapter(Context context, List<SliderModel> sliderList) {
        this.context = context;
        this.sliderList = sliderList;
    }

    @Override
    public SliderHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.slider_item_layout, parent, false);
        return new SliderHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderHolder viewHolder, int position) {
        Picasso.get().load(sliderList.get(position).getImageUrl()).into(viewHolder.sliderImage);
        viewHolder.sliderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getCount() {
        return sliderList.size();
    }

    static class SliderHolder extends SliderViewAdapter.ViewHolder {
        ImageView sliderImage;

        public SliderHolder(View itemView) {
            super(itemView);
            sliderImage = itemView.findViewById(R.id.sliderImageView);
        }
    }
}


package com.example.e_shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.R;
import com.example.e_shop.activity.PerCategoryAll_item;
import com.example.e_shop.activity.PerItemDetails;
import com.example.e_shop.model.PerCatModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PerCatItemAdapter extends RecyclerView.Adapter<PerCatItemAdapter.PcIHolder> {
    Context context;
    List<PerCatModel> perCatModelList;

    public PerCatItemAdapter(Context context, List<PerCatModel> perCatModelList) {
        this.context = context;
        this.perCatModelList = perCatModelList;
    }

    @NonNull
    @Override
    public PcIHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.per_cat_item_layout, parent, false);
        return new PcIHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PcIHolder holder, int position) {
        PerCatModel item = perCatModelList.get(position);
        Picasso.get().load(item.getPerCatImageItemUrl()).into(holder.perCatItemImage);
        holder.perCatItemName.setText(item.getPerCatItemName());
        holder.perCatItemPrice.setText(item.getPerCatItemPrice());

        holder.perCatItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PerItemDetails.class)
                        .putExtra("image", item.getPerCatImageItemUrl())
                        .putExtra("name", item.getPerCatItemName())
                        .putExtra("price", item.getPerCatItemPrice())
                        .putExtra("description", item.getPerCatItemDescription())

//                          send categories array collection for suggestion
                        .putExtra("s_image", PerCategoryAll_item.productImageUrl)
                        .putExtra("s_name", PerCategoryAll_item.productName)
                        .putExtra("s_price", PerCategoryAll_item.productPrice)
                        .putExtra("s_description", PerCategoryAll_item.description));
            }
        });
    }

    @Override
    public int getItemCount() {
        return perCatModelList.size();
    }

    public void getFilter(ArrayList<PerCatModel> catModelList) {
        perCatModelList=catModelList;
        notifyDataSetChanged();
    }

    public static class PcIHolder extends RecyclerView.ViewHolder {
        CardView perCatItemCardView;
        ImageView perCatItemImage;
        TextView perCatItemName;
        TextView perCatItemPrice;

        public PcIHolder(@NonNull View itemView) {
            super(itemView);
            perCatItemCardView = itemView.findViewById(R.id.perCatItemCardView);
            perCatItemImage = itemView.findViewById(R.id.perCatItemImageView);
            perCatItemName = itemView.findViewById(R.id.perCatItemName);
            perCatItemPrice = itemView.findViewById(R.id.perCatItemPrice);
        }
    }
}

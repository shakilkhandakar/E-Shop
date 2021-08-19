package com.example.e_shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.R;
import com.example.e_shop.activity.PerItemDetails;
import com.example.e_shop.model.SuggestionModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.SgHolder> {
    Context context;
    List<SuggestionModel> suggestionModelList;
    public static String imageUrl;

    public SuggestionAdapter(Context context, List<SuggestionModel> suggestionModelList) {
        this.context = context;
        this.suggestionModelList = suggestionModelList;
    }

    @NonNull
    @Override
    public SgHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.suggestion_item_layout, parent, false);
        return new SgHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SgHolder holder, int position) {
        SuggestionModel item = suggestionModelList.get(position);
        Picasso.get().load(item.getSuggestionImageItemUrl()).into(holder.suggestionItemImage);
        holder.suggestionItemName.setText(item.getSuggestionItemName());
        holder.suggestionItemPrice.setText(item.getSuggestionItemPrice());
        holder.suggestionItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUrl=item.getSuggestionImageItemUrl();
                PerItemDetails.imageUrl=imageUrl;
                Picasso.get().load(imageUrl).into(PerItemDetails.imageView);
                PerItemDetails.productName.setText(item.getSuggestionItemName());
                PerItemDetails.productPrice.setText(item.getSuggestionItemPrice());
                PerItemDetails.productDescription.setText(context.getResources().getString(R.string.itemDescription));
            }
        });
    }


    @Override
    public int getItemCount() {
        return suggestionModelList.size();
    }

    public static class SgHolder extends RecyclerView.ViewHolder {
        CardView suggestionItemCardView;
        ImageView suggestionItemImage;
        TextView suggestionItemName;
        TextView suggestionItemPrice;

        public SgHolder(@NonNull View itemView) {
            super(itemView);
            suggestionItemCardView = itemView.findViewById(R.id.suggestionItemCardView);
            suggestionItemImage = itemView.findViewById(R.id.suggestionItemImageView);
            suggestionItemName = itemView.findViewById(R.id.suggestionItemName);
            suggestionItemPrice = itemView.findViewById(R.id.suggestionItemPrice);
        }
    }
}

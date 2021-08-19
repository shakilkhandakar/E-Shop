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
import com.example.e_shop.activity.PerItemDetails;
import com.example.e_shop.model.WishListModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WlHolder> {
    Context context;
    List<WishListModel> wishListModelList;

    public WishListAdapter(Context context, List<WishListModel> wishListModelList) {
        this.context = context;
        this.wishListModelList = wishListModelList;
    }

    @NonNull
    @Override
    public WlHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wish_list_item_layout, parent, false);
        return new WlHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WlHolder holder, int position) {
        WishListModel item = wishListModelList.get(position);

        Picasso.get().load(item.getWishImageUrl()).into(holder.wishListItemImage);
        holder.wishListItemName.setText(item.getWishProductName());
        holder.wishListItemPrice.setText(item.getWishProductPrice());
        holder.wishListCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] imageUrl=context.getResources().getStringArray(R.array.bookImageUrl);
                String[] itemName =context. getResources().getStringArray(R.array.bookName);
                String[] itemPrice = context.getResources().getStringArray(R.array.bookPrice);

                context.startActivity(new Intent(context, PerItemDetails.class)
                        .putExtra("image", item.getWishImageUrl())
                        .putExtra("name", item.getWishProductName())
                        .putExtra("price", item.getWishProductPrice())
                        .putExtra("description", item.getWishDescription())
 //     temporary send book collection for suggestion.further change item wise show suggestion to wish list.
                        .putExtra("s_image", imageUrl)
                        .putExtra("s_name",itemName)
                        .putExtra("s_price", itemPrice)
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return wishListModelList.size();
    }

    public static class WlHolder extends RecyclerView.ViewHolder {
        CardView wishListCardView;
        ImageView wishListItemImage;
        TextView wishListItemName, wishListItemPrice;

        public WlHolder(@NonNull View ItemView) {
            super(ItemView);
            wishListCardView = ItemView.findViewById(R.id.wishListCardView);
            wishListItemImage = ItemView.findViewById(R.id.wishListImageView);
            wishListItemName = ItemView.findViewById(R.id.wishListName);
            wishListItemPrice = ItemView.findViewById(R.id.wishListPrice);
        }
    }
}

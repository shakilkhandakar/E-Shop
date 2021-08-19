package com.example.e_shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.R;
import com.example.e_shop.activity.PerItemDetails;
import com.example.e_shop.model.CartModel;
import com.example.e_shop.model.FurnitureCatModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FurnitureCatAdapter extends RecyclerView.Adapter<FurnitureCatAdapter.FrHolder> {
    Context context;
    List<FurnitureCatModel>furnitureCatModelList;

    public FurnitureCatAdapter(Context context, List<FurnitureCatModel> furnitureCatModelList) {
        this.context = context;
        this.furnitureCatModelList = furnitureCatModelList;
    }

    @NonNull
    @Override
    public FrHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.furniture_item_layout, parent, false);
        return new FrHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FrHolder holder, int position) {
        FurnitureCatModel item=furnitureCatModelList.get(position);
        Picasso.get().load(item.getFurnitureImageUrls()).into(holder.furnitureCategoriesImage);
        holder.furnitureCategoriesName.setText(item.getFurnitureName());
        holder.furnitureCategoriesPrice.setText(item.getFurniturePrice());

        holder.cartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAddToCart(item.getFurnitureImageUrls(),item.getFurnitureName(),item.getFurniturePrice());
            }
        });
        holder.furnitureCatCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] imageUrl = context.getResources().getStringArray(R.array.furnitureImageUrl);
                String[] itemName = context.getResources().getStringArray(R.array.furnitureName);
                String[] itemPrice = context.getResources().getStringArray(R.array.furniturePrice);


                context.startActivity(new Intent(context, PerItemDetails.class)
                        .putExtra("image", item.getFurnitureImageUrls())
                        .putExtra("name", item.getFurnitureName())
                        .putExtra("price", item.getFurniturePrice())
                        .putExtra("description", item.getFurnitureDescription())

                        //     send furniture collection for suggestion
                        .putExtra("s_image", imageUrl)
                        .putExtra("s_name", itemName)
                        .putExtra("s_price", itemPrice)
                );
            }
        });
    }

    private void valueAddToCart(String furnitureImageUrls, String furnitureName, String furniturePrice) {
//                check which value is already exist in firebase
        FirebaseDatabase.getInstance().getReference("Cart Product Information")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(furnitureName)) {
                            Toast.makeText(context, "This Product Already Available in Cart", Toast.LENGTH_SHORT).show();
                        } else {
                            CartModel item = new CartModel(furnitureImageUrls, furnitureName, furniturePrice, 1);
                            FirebaseDatabase.getInstance().getReference("Cart Product Information").child(furnitureName)
                                    .setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public int getItemCount() {
        return furnitureCatModelList.size();
    }

    public static class FrHolder extends RecyclerView.ViewHolder {
        CardView furnitureCatCardView;
        ImageView furnitureCategoriesImage;
        TextView furnitureCategoriesName,furnitureCategoriesPrice;
        ImageButton cartImageButton;
        public FrHolder(@NonNull View itemView) {
            super(itemView);
            furnitureCatCardView=itemView.findViewById(R.id.furnitureCategoriesCardView);
            furnitureCategoriesImage=itemView.findViewById(R.id.furnitureCategoriesImageView);
            furnitureCategoriesName=itemView.findViewById(R.id.furnitureCategoriesName);
            furnitureCategoriesPrice=itemView.findViewById(R.id.furnitureCategoriesPrice);
            cartImageButton=itemView.findViewById(R.id.furnitureCategoriesCartIconImageButton);
        }
    }
}

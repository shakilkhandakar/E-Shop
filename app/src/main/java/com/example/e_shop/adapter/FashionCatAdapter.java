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
import com.example.e_shop.model.FashionCatModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FashionCatAdapter extends RecyclerView.Adapter<FashionCatAdapter.FcHolder> {
    Context context;
    List<FashionCatModel> fashionCatModelList;

    public FashionCatAdapter(Context context, List<FashionCatModel> fashionCatModelList) {
        this.context = context;
        this.fashionCatModelList = fashionCatModelList;
    }

    @NonNull
    @Override
    public FcHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fashion_item_layout, parent, false);
        return new FcHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FcHolder holder, int position) {
        FashionCatModel item=fashionCatModelList.get(position);
        Picasso.get().load(item.getFashionImageUrls()).into(holder.fashionCategoriesImage);
        holder.fashionCategoriesName.setText(item.getFashionName());
        holder.fashionCategoriesPrice.setText(item.getFashionPrice());

        holder.cartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAddToCart(item.getFashionImageUrls(),item.getFashionName(),item.getFashionPrice());
            }
        });
        holder.fashionCatCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] imageUrl = context.getResources().getStringArray(R.array.fashionImageUrl);
                String[] itemName = context.getResources().getStringArray(R.array.fashionName);
                String[] itemPrice = context.getResources().getStringArray(R.array.fashionPrice);

                context.startActivity(new Intent(context, PerItemDetails.class)
                        .putExtra("image", item.getFashionImageUrls())
                        .putExtra("name", item.getFashionName())
                        .putExtra("price", item.getFashionPrice())
                        .putExtra("description", item.getFashionDescription())

                        //     send fashion collection for suggestion
                        .putExtra("s_image", imageUrl)
                        .putExtra("s_name", itemName)
                        .putExtra("s_price", itemPrice)
                );
            }
        });
    }

    private void valueAddToCart(String fashionImageUrls, String fashionName, String fashionPrice) {
//                check which value is already exist in firebase
        FirebaseDatabase.getInstance().getReference("Cart Product Information")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(fashionName)) {
                            Toast.makeText(context, "This Product Already Available in Cart", Toast.LENGTH_SHORT).show();
                        } else {
                            CartModel item = new CartModel(fashionImageUrls, fashionName, fashionPrice, 1);
                            FirebaseDatabase.getInstance().getReference("Cart Product Information").child(fashionName)
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
        return fashionCatModelList.size();
    }

    public static class FcHolder extends RecyclerView.ViewHolder {
        CardView fashionCatCardView;
        ImageView fashionCategoriesImage;
        TextView fashionCategoriesName,fashionCategoriesPrice;
        ImageButton cartImageButton;
        public FcHolder(@NonNull View itemView) {
            super(itemView);
            fashionCatCardView=itemView.findViewById(R.id.fashionCategoriesCardView);
            fashionCategoriesImage=itemView.findViewById(R.id.fashionCategoriesImageView);
            fashionCategoriesName=itemView.findViewById(R.id.fashionCategoriesName);
            fashionCategoriesPrice=itemView.findViewById(R.id.fashionCategoriesPrice);
            cartImageButton=itemView.findViewById(R.id.fashionCategoriesCartIconImageButton);
        }
    }
}

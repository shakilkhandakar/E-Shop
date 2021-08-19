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
import com.example.e_shop.model.ToyCatModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ToysCatAdapter extends RecyclerView.Adapter<ToysCatAdapter.TcHolder> {
    Context context;
    List<ToyCatModel> toyCatModelList;

    public ToysCatAdapter(Context context, List<ToyCatModel> toyCatModelList) {
        this.context = context;
        this.toyCatModelList = toyCatModelList;
    }

    @NonNull
    @Override
    public ToysCatAdapter.TcHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.toys_item_layout, parent, false);
        return new TcHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToysCatAdapter.TcHolder holder, int position) {
        ToyCatModel item=toyCatModelList.get(position);
        Picasso.get().load(item.getToysImageUrls()).into(holder.toysCategoriesImage);
        holder.toysCategoriesName.setText(item.getToysName());
        holder.toysCategoriesPrice.setText(item.getToysPrice());

        holder.cartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAddToCart(item.getToysImageUrls(),item.getToysName(),item.getToysPrice());
            }
        });
        holder.toysCatCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] imageUrl = context.getResources().getStringArray(R.array.toysImageUrl);
                String[] itemName = context.getResources().getStringArray(R.array.toysName);
                String[] itemPrice = context.getResources().getStringArray(R.array.toysPrice);

                context.startActivity(new Intent(context, PerItemDetails.class)
                        .putExtra("image", item.getToysImageUrls())
                        .putExtra("name", item.getToysName())
                        .putExtra("price", item.getToysPrice())
                        .putExtra("description", item.getToysDescription())

                        //     send toys collection for suggestion
                        .putExtra("s_image", imageUrl)
                        .putExtra("s_name", itemName)
                        .putExtra("s_price", itemPrice)
                );
            }
        });
    }

    private void valueAddToCart(String toysImageUrls, String toysName, String toysPrice) {
//                check which value is already exist in firebase
        FirebaseDatabase.getInstance().getReference("Cart Product Information")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(toysName)) {
                            Toast.makeText(context, "This Product Already Available in Cart", Toast.LENGTH_SHORT).show();
                        } else {
                            CartModel item = new CartModel(toysImageUrls, toysName, toysPrice, 1);
                            FirebaseDatabase.getInstance().getReference("Cart Product Information").child(toysName)
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
        return toyCatModelList.size();
    }

    public static class TcHolder extends RecyclerView.ViewHolder {
        CardView toysCatCardView;
        ImageView toysCategoriesImage;
        TextView toysCategoriesName,toysCategoriesPrice;
        ImageButton cartImageButton;
        public TcHolder(@NonNull View itemView) {
            super(itemView);
            toysCatCardView=itemView.findViewById(R.id.toysCategoriesCardView);
            toysCategoriesImage=itemView.findViewById(R.id.toysCategoriesImageView);
            toysCategoriesName=itemView.findViewById(R.id.toysCategoriesName);
            toysCategoriesPrice=itemView.findViewById(R.id.toysCategoriesPrice);
            cartImageButton=itemView.findViewById(R.id.toysCategoriesCartIconImageButton);
        }
    }
}

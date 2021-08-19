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
import com.example.e_shop.model.SportsCatModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SportsCatAdapter extends RecyclerView.Adapter<SportsCatAdapter.SpHolder> {
    Context context;
    List<SportsCatModel> sportsCatModelList;

    public SportsCatAdapter(Context context, List<SportsCatModel> sportsCatModelList) {
        this.context = context;
        this.sportsCatModelList = sportsCatModelList;
    }

    @NonNull
    @Override
    public SportsCatAdapter.SpHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sports_item_layout, parent, false);
        return new SpHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SportsCatAdapter.SpHolder holder, int position) {
        SportsCatModel item=sportsCatModelList.get(position);
        Picasso.get().load(item.getSportsImageUrls()).into(holder.sportsCategoriesImage);
        holder.sportsCategoriesName.setText(item.getSportsName());
        holder.sportsCategoriesPrice.setText(item.getSportsPrice());

        holder.cartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAddToCart(item.getSportsImageUrls(),item.getSportsName(),item.getSportsPrice());
            }
        });
        holder.sportsCatCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] imageUrl = context.getResources().getStringArray(R.array.sportsImageUrl);
                String[] itemName = context.getResources().getStringArray(R.array.sportsName);
                String[] itemPrice = context.getResources().getStringArray(R.array.sportsPrice);

                context.startActivity(new Intent(context, PerItemDetails.class)
                        .putExtra("image", item.getSportsImageUrls())
                        .putExtra("name", item.getSportsName())
                        .putExtra("price", item.getSportsPrice())
                        .putExtra("description", item.getSportsDescription())

                        //     send sports collection for suggestion
                        .putExtra("s_image", imageUrl)
                        .putExtra("s_name", itemName)
                        .putExtra("s_price", itemPrice)
                );
            }
        });
    }

    private void valueAddToCart(String sportsImageUrls, String sportsName, String sportsPrice) {
//                check which value is already exist in firebase
        FirebaseDatabase.getInstance().getReference("Cart Product Information")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(sportsName)) {
                            Toast.makeText(context, "This Product Already Available in Cart", Toast.LENGTH_SHORT).show();
                        } else {
                            CartModel item = new CartModel(sportsImageUrls, sportsName, sportsPrice, 1);
                            FirebaseDatabase.getInstance().getReference("Cart Product Information").child(sportsName)
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
        return sportsCatModelList.size();
    }

    public static class SpHolder extends RecyclerView.ViewHolder {
        CardView sportsCatCardView;
        ImageView sportsCategoriesImage;
        TextView sportsCategoriesName,sportsCategoriesPrice;
        ImageButton cartImageButton;
        public SpHolder(@NonNull View itemView) {
            super(itemView);
            sportsCatCardView=itemView.findViewById(R.id.sportsCategoriesCardView);
            sportsCategoriesImage=itemView.findViewById(R.id.sportsCategoriesImageView);
            sportsCategoriesName=itemView.findViewById(R.id.sportsCategoriesName);
            sportsCategoriesPrice=itemView.findViewById(R.id.sportsCategoriesPrice);
            cartImageButton=itemView.findViewById(R.id.sportsCategoriesCartIconImageButton);
        }
    }
}

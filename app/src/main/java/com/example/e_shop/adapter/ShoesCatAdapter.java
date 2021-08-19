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
import com.example.e_shop.model.ShoesCatModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShoesCatAdapter extends RecyclerView.Adapter<ShoesCatAdapter.ScHolder> {
    Context context;
    List<ShoesCatModel> shoesCatModelList;

    public ShoesCatAdapter(Context context, List<ShoesCatModel> shoesCatModelList) {
        this.context = context;
        this.shoesCatModelList = shoesCatModelList;
    }

    @NonNull
    @Override
    public ShoesCatAdapter.ScHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shoes_item_layout, parent, false);
        return new ScHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoesCatAdapter.ScHolder holder, int position) {
        ShoesCatModel item = shoesCatModelList.get(position);
        Picasso.get().load(item.getShoesImageUrls()).into(holder.shoesCategoriesImage);
        holder.shoesCategoriesName.setText(item.getShoesName());
        holder.shoesCategoriesPrice.setText(item.getShoesPrice());

        holder.cartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAddToCart(item.getShoesImageUrls(), item.getShoesName(), item.getShoesPrice());
            }
        });
        holder.shoeCatCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] imageUrl = context.getResources().getStringArray(R.array.shoesImageUrl);
                String[] itemName = context.getResources().getStringArray(R.array.shoesName);
                String[] itemPrice = context.getResources().getStringArray(R.array.shoesPrice);

                context.startActivity(new Intent(context, PerItemDetails.class)
                                .putExtra("image", item.getShoesImageUrls())
                                .putExtra("name", item.getShoesName())
                                .putExtra("price", item.getShoesPrice())
                                .putExtra("description", item.getShoesDescription())

                                 //     send shoes collection for suggestion
                                .putExtra("s_image", imageUrl)
                                .putExtra("s_name", itemName)
                                .putExtra("s_price", itemPrice)
                );
            }
        });
    }

    private void valueAddToCart(String shoesImageUrls, String shoesName, String shoesPrice) {
//                check which value is already exist in firebase
        FirebaseDatabase.getInstance().getReference("Cart Product Information")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(shoesName)) {
                            Toast.makeText(context, "This Product Already Available in Cart", Toast.LENGTH_SHORT).show();
                        } else {
                            CartModel item = new CartModel(shoesImageUrls, shoesName, shoesPrice, 1);
                            FirebaseDatabase.getInstance().getReference("Cart Product Information").child(shoesName)
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
        return shoesCatModelList.size();
    }

    public static class ScHolder extends RecyclerView.ViewHolder {
        CardView shoeCatCardView;
        ImageView shoesCategoriesImage;
        TextView shoesCategoriesName, shoesCategoriesPrice;
        ImageButton cartImageButton;

        public ScHolder(@NonNull View itemView) {
            super(itemView);
            shoeCatCardView = itemView.findViewById(R.id.shoesCategoriesCardView);
            shoesCategoriesImage = itemView.findViewById(R.id.shoesCategoriesImageView);
            shoesCategoriesName = itemView.findViewById(R.id.shoesCategoriesName);
            shoesCategoriesPrice = itemView.findViewById(R.id.shoesCategoriesPrice);
            cartImageButton = itemView.findViewById(R.id.shoesCategoriesCartIconImageButton);
        }
    }
}

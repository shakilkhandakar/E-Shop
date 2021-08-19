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
import com.example.e_shop.model.ElectronicsCatModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ElectronicsCatAdapter extends RecyclerView.Adapter<ElectronicsCatAdapter.EcHolder> {
    Context context;
    List<ElectronicsCatModel>electronicsCatModelList;

    public ElectronicsCatAdapter(Context context, List<ElectronicsCatModel> electronicsCatModelList) {
        this.context = context;
        this.electronicsCatModelList = electronicsCatModelList;
    }

    @NonNull
    @Override
    public EcHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.electronics_item_layout, parent, false);
        return new EcHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EcHolder holder, int position) {
        ElectronicsCatModel item=electronicsCatModelList.get(position);
        Picasso.get().load(item.getElectronicImageUrls()).into(holder.electronicsCategoriesImage);
        holder.electronicsCategoriesName.setText(item.getElectronicName());
        holder.electronicsCategoriesPrice.setText(item.getElectronicPrice());

        holder.cartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAddToCart(item.getElectronicImageUrls(),item.getElectronicName(),item.getElectronicPrice());
            }
        });
        holder.electronicsCatCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] imageUrl = context.getResources().getStringArray(R.array.electronicsImageUrl);
                String[] itemName = context.getResources().getStringArray(R.array.electronicsName);
                String[] itemPrice = context.getResources().getStringArray(R.array.electronicsPrice);

                context.startActivity(new Intent(context, PerItemDetails.class)
                        .putExtra("image", item.getElectronicImageUrls())
                        .putExtra("name", item.getElectronicName())
                        .putExtra("price", item.getElectronicPrice())
                        .putExtra("description", item.getElectronicDescription())

                        //     send furniture collection for suggestion
                        .putExtra("s_image", imageUrl)
                        .putExtra("s_name", itemName)
                        .putExtra("s_price", itemPrice)
                );
            }
        });
    }

    private void valueAddToCart(String electronicsImageUrls, String electronicsName, String electronicsPrice) {
//                check which value is already exist in firebase
        FirebaseDatabase.getInstance().getReference("Cart Product Information")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(electronicsName)) {
                            Toast.makeText(context, "This Product Already Available in Cart", Toast.LENGTH_SHORT).show();
                        } else {
                            CartModel item = new CartModel(electronicsImageUrls, electronicsName, electronicsPrice, 1);
                            FirebaseDatabase.getInstance().getReference("Cart Product Information").child(electronicsName)
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
        return electronicsCatModelList.size();
    }

    public static class EcHolder extends RecyclerView.ViewHolder {
        CardView electronicsCatCardView;
        ImageView electronicsCategoriesImage;
        TextView electronicsCategoriesName,electronicsCategoriesPrice;
        ImageButton cartImageButton;
        public EcHolder(@NonNull View itemView) {
            super(itemView);
            electronicsCatCardView=itemView.findViewById(R.id.electronicsCategoriesCardView);
            electronicsCategoriesImage=itemView.findViewById(R.id.electronicsCategoriesImageView);
            electronicsCategoriesName=itemView.findViewById(R.id.electronicsCategoriesName);
            electronicsCategoriesPrice=itemView.findViewById(R.id.electronicsCategoriesPrice);
            cartImageButton=itemView.findViewById(R.id.electronicsCategoriesCartIconImageButton);
        }
    }
}

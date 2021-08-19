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
import com.example.e_shop.activity.MyCartListActivity;
import com.example.e_shop.activity.PerCategoryAll_item;
import com.example.e_shop.activity.PerItemDetails;
import com.example.e_shop.model.BookCatModel;
import com.example.e_shop.model.CartModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookCatAdapter extends RecyclerView.Adapter<BookCatAdapter.BcHolder> {
    Context context;
    List<BookCatModel>bookCatModelList;

    public BookCatAdapter(Context context, List<BookCatModel> bookCatModelList) {
        this.context = context;
        this.bookCatModelList = bookCatModelList;
    }

    @NonNull
    @Override
    public BookCatAdapter.BcHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_items_layout, parent, false);
        return new BcHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookCatAdapter.BcHolder holder, int position) {
        BookCatModel item=bookCatModelList.get(position);
        Picasso.get().load(item.getBookImageUrls()).into(holder.bookCategoriesImage);
        holder.bookCategoriesName.setText(item.getBookName());
        holder.bookCategoriesPrice.setText(item.getBookPrice());

        holder.cartImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAddToCart(item.getBookImageUrls(),item.getBookName(),item.getBookPrice());
            }
        });

        holder.bookCatCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] imageUrl=context.getResources().getStringArray(R.array.bookImageUrl);
                String[] itemName =context. getResources().getStringArray(R.array.bookName);
                String[] itemPrice = context.getResources().getStringArray(R.array.bookPrice);

                context.startActivity(new Intent(context, PerItemDetails.class)
                        .putExtra("image", item.getBookImageUrls())
                        .putExtra("name", item.getBookName())
                        .putExtra("price", item.getBookPrice())
                        .putExtra("description", item.getBookDescription())

//                       send book collection for suggestion
                        .putExtra("s_image", imageUrl)
                        .putExtra("s_name",itemName)
                        .putExtra("s_price", itemPrice)
                );
            }
        });
    }

    private void valueAddToCart(String bookImageUrls, String bookName, String bookPrice) {
//                check which value is already exist in firebase
        FirebaseDatabase.getInstance().getReference("Cart Product Information")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(bookName)) {
                            Toast.makeText(context, "This Product Already Available in Cart", Toast.LENGTH_SHORT).show();
                        } else {
                            CartModel item = new CartModel(bookImageUrls, bookName, bookPrice, 1);
                            FirebaseDatabase.getInstance().getReference("Cart Product Information").child(bookName)
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
        return bookCatModelList.size();
    }

    public static class BcHolder extends RecyclerView.ViewHolder {
        CardView bookCatCardView;
        ImageView bookCategoriesImage;
        TextView bookCategoriesName,bookCategoriesPrice;
        ImageButton cartImageButton;
        public BcHolder(@NonNull View itemView) {
            super(itemView);
            bookCatCardView=itemView.findViewById(R.id.bookCategoriesCardView);
            bookCategoriesImage=itemView.findViewById(R.id.bookCategoriesImageView);
            bookCategoriesName=itemView.findViewById(R.id.bookCategoriesName);
            bookCategoriesPrice=itemView.findViewById(R.id.bookCategoriesPrice);
            cartImageButton=itemView.findViewById(R.id.bookCategoriesCartIconImageButton);
        }
    }
}

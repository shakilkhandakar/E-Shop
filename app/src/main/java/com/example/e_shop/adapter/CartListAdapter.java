package com.example.e_shop.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.R;
import com.example.e_shop.activity.MyCartListActivity;
import com.example.e_shop.model.CartModel;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ClHolder> {
    Context context;
    List<CartModel> cartModelList;
    int count;
    String productPrice;

    public CartListAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public ClHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_list_item_layout, parent, false);
        return new ClHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClHolder holder, int position) {
        CartModel item = cartModelList.get(position);

        Picasso.get().load(item.getCartImageUrl()).into(holder.cartListItemImage);
        productPrice = item.getCartProductPrice();
        count = item.getOrderCount();
        holder.cartListItemName.setText(item.getCartProductName());
        holder.cartListItemPrice.setText(productPrice);
        holder.cartListItemOrderNumber.setText(String.valueOf(count));

        holder.decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalCount = Integer.parseInt(String.valueOf(holder.cartListItemOrderNumber.getText()));
                if (totalCount > 1) {
                    totalCount--;
                    saveToFireBase(item,totalCount);
                }
            }
        });

        holder.increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalCount = Integer.parseInt(String.valueOf(holder.cartListItemOrderNumber.getText()));
                totalCount++;
                saveToFireBase(item,totalCount);
            }
        });


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setIcon(R.drawable.ic_baseline_delete_24);
                dialog.setTitle("Remove");
                dialog.setMessage("Do you want to delete this item ?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference("Cart Product Information")
                                .child(item.getCartProductName()).removeValue();
                        notifyDataSetChanged();
                        cartModelList.clear();
                        if (cartModelList.isEmpty()) {
                            MyCartListActivity.continueShoppingLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });
                dialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.create().show();
            }
        });

    }

    private void saveToFireBase(CartModel item, int totalCount) {
        FirebaseDatabase.getInstance().getReference("Cart Product Information")
                .child(item.getCartProductName()).child("orderCount")
                .setValue(totalCount);
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }


    public static class ClHolder extends RecyclerView.ViewHolder {
        CardView cartListCardView;
        ImageView cartListItemImage;
        TextView cartListItemName, cartListItemPrice, cartListItemOrderNumber;
        Button deleteButton;
        ImageButton decreaseButton, increaseButton;

        public ClHolder(@NonNull View ItemView) {
            super(ItemView);
            cartListCardView = ItemView.findViewById(R.id.cartListCardView);
            cartListItemImage = ItemView.findViewById(R.id.cartListImageView);
            cartListItemName = ItemView.findViewById(R.id.cartListName);
            cartListItemPrice = ItemView.findViewById(R.id.cartListPrice);
            cartListItemOrderNumber = ItemView.findViewById(R.id.numberOfOrderProduct);
            deleteButton = ItemView.findViewById(R.id.cartListDeleteButton);
            decreaseButton = ItemView.findViewById(R.id.decreaseButton);
            increaseButton = ItemView.findViewById(R.id.increaseButton);
        }
    }

}

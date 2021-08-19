package com.example.e_shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.R;
import com.example.e_shop.activity.ConfirmActivityForCartListItem;
import com.example.e_shop.model.CartModel;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ConfirmAdapter extends RecyclerView.Adapter<ConfirmAdapter.CnHolder> {
    Context context;
    List<CartModel> cartModelList;
    String productPrice;
    int count,totalPrice;

    public ConfirmAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public CnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.product_information_layout,parent,false);
        return new CnHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CnHolder holder, int position) {
        CartModel item = cartModelList.get(position);

        Picasso.get().load(item.getCartImageUrl()).into(holder.confirmItemImage);
        productPrice = item.getCartProductPrice();
        count = item.getOrderCount();
        totalPrice=Integer.parseInt(productPrice)*count;
        holder.confirmItemName.setText(item.getCartProductName());
        holder.confirmItemPrice.setText(productPrice);
        holder.confirmItemOrderNumber.setText(String.valueOf(count));
        holder.confirmTotalPrice.setText(String.valueOf(totalPrice));

        totalAmountCalculation();

        holder.decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalCount = Integer.parseInt(String.valueOf(holder.confirmItemOrderNumber.getText()));
                if (totalCount > 1) {
                    totalCount--;
                    saveToFireBase(item,totalCount);
                }
            }
        });

        holder.increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalCount = Integer.parseInt(String.valueOf(holder.confirmItemOrderNumber.getText()));
                totalCount++;
                saveToFireBase(item,totalCount);
            }
        });

    }

//      here calculate each item total price into total amount
    private void totalAmountCalculation() {
        int totalAmount=0;
            for (int i=0;i<cartModelList.size();i++){
                    CartModel data=cartModelList.get(i);
                    totalAmount+=Integer.parseInt(data.getCartProductPrice())*data.getOrderCount();
            }
        ConfirmActivityForCartListItem.totalAmount.setText(String.valueOf(totalAmount));
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

    public static class CnHolder extends RecyclerView.ViewHolder {
        ImageView confirmItemImage;
        TextView confirmItemName, confirmItemPrice, confirmItemOrderNumber,confirmTotalPrice;
        ImageButton decreaseButton, increaseButton;
        public CnHolder(@NonNull View itemView) {
            super(itemView);
            confirmItemImage = itemView.findViewById(R.id.confirmImageView);
            confirmItemName = itemView.findViewById(R.id.confirmProductName);
            confirmItemPrice = itemView.findViewById(R.id.confirmProductPrice);
            decreaseButton = itemView.findViewById(R.id.decreaseButton);
            increaseButton = itemView.findViewById(R.id.increaseButton);
            confirmItemOrderNumber = itemView.findViewById(R.id.confirmTotalOrderItemNumber);
            confirmTotalPrice = itemView.findViewById(R.id.confirmTotalProductPrice);
        }
    }
}

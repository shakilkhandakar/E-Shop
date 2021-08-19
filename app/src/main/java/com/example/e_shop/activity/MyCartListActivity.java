package com.example.e_shop.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.R;
import com.example.e_shop.adapter.CartListAdapter;
import com.example.e_shop.model.CartModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyCartListActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
   public static LinearLayout continueShoppingLayout;
    @SuppressLint("StaticFieldLeak")
    RelativeLayout cartRecyclerLayout;
    RecyclerView cartRecyclerView;
    public static Button checkOutButton;
    CartListAdapter adapter;
    public List<CartModel> cartModelList;
    long hasData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart_list);

        continueShoppingLayout = findViewById(R.id.continueShoppingLayout);
        cartRecyclerLayout = findViewById(R.id.cartListRecyclerLayout);
        cartRecyclerView = findViewById(R.id.cartListRecyclerView);
        checkOutButton = findViewById(R.id.checkOutButtonId);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setHasFixedSize(true);
        cartModelList = new ArrayList<>();

        fetchCartValue();
        adapter = new CartListAdapter(this, cartModelList);
        cartRecyclerView.setAdapter(adapter);

    }

    private void fetchCartValue() {

        FirebaseDatabase.getInstance().getReference("Cart Product Information")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartModelList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    hasData=snapshot.getChildrenCount();
                    CartModel data=dataSnapshot.getValue(CartModel.class);
                    if (hasData!=0){
                        cartModelList.add(data);
                        continueShoppingLayout.setVisibility(View.GONE);
                    }else {
                        continueShoppingLayout.setVisibility(View.VISIBLE);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyCartListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void continueShopping(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void backArrow(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void checkOutFromCartList(View view) {
        if (cartModelList.size()>0){
            startActivity(new Intent(this, ConfirmActivityForCartListItem.class));
            finish();
        }else {
            Toast.makeText(this, "Cart is Empty", Toast.LENGTH_SHORT).show();
        }

    }
}
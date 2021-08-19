package com.example.e_shop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.e_shop.R;
import com.example.e_shop.adapter.WishListAdapter;
import com.example.e_shop.model.CartModel;
import com.example.e_shop.model.WishListModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyWishListActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static LinearLayout continueShoppingLayout;
    RecyclerView wishRecyclerView;
    WishListAdapter adapter;
    public List<WishListModel> wishModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wish_list);

        continueShoppingLayout = findViewById(R.id.continueShoppingLayoutForWishList);
        wishRecyclerView = findViewById(R.id.myWishListRecyclerView);

        wishRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        wishRecyclerView.setHasFixedSize(true);
        wishModelList = new ArrayList<>();

        fetchWishListValue();
        adapter = new WishListAdapter(this, wishModelList);
        wishRecyclerView.setAdapter(adapter);
    }

    private void fetchWishListValue() {
        FirebaseDatabase.getInstance().getReference("WishList Product Information")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        wishModelList.clear();
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            WishListModel data=dataSnapshot.getValue(WishListModel.class);
                            if (data!=null){
                                wishModelList.add(data);
                                continueShoppingLayout.setVisibility(View.GONE);
                            }else {
                                continueShoppingLayout.setVisibility(View.VISIBLE);
                                wishRecyclerView.setVisibility(View.GONE);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MyWishListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void backArrow(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }
    @Override
    public void onBackPressed() {
        finish();
    }
    public void continueShopping(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
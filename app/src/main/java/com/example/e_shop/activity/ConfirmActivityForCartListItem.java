package com.example.e_shop.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.R;
import com.example.e_shop.adapter.ConfirmAdapter;
import com.example.e_shop.model.CartModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConfirmActivityForCartListItem extends AppCompatActivity {
    RecyclerView productRecycler;
   @SuppressLint("StaticFieldLeak")
   public static TextView totalAmount;
    ConfirmAdapter adapter;
    List<CartModel> cartModelList;
    EditText buyerNameInputEditText,buyerPhoneInputEditText,buyerEmailInputEditText,
            buyerCityInputEditText,buyerAddressInputEditText;
    String deliveryName,deliveryPhone,deliveryEmail,deliveryCity,deliveryAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        productRecycler = findViewById(R.id.productInformationRecycler);
        totalAmount = findViewById(R.id.confirmTotalAmount);
        buyerNameInputEditText = findViewById(R.id.confirmNameEditText);
        buyerPhoneInputEditText = findViewById(R.id.confirmPhoneEditText);
        buyerEmailInputEditText = findViewById(R.id.confirmEmailEditText);
        buyerCityInputEditText = findViewById(R.id.confirmCityEditText);
        buyerAddressInputEditText = findViewById(R.id.confirmAddressEditText);

        productRecycler.setLayoutManager(new LinearLayoutManager(this));
        productRecycler.setHasFixedSize(true);
        cartModelList = new ArrayList<>();

        fetchProductInformation();
        adapter = new ConfirmAdapter(this, cartModelList);
        productRecycler.setAdapter(adapter);

        deliveryName=buyerNameInputEditText.getText().toString();
        deliveryPhone=buyerPhoneInputEditText.getText().toString();
        deliveryEmail=buyerEmailInputEditText.getText().toString();
        deliveryCity=buyerCityInputEditText.getText().toString();
        deliveryAddress=buyerAddressInputEditText.getText().toString();
    }

    private void fetchProductInformation() {
        FirebaseDatabase.getInstance().getReference("Cart Product Information")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        cartModelList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            CartModel data = dataSnapshot.getValue(CartModel.class);
                            cartModelList.add(data);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ConfirmActivityForCartListItem.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void confirmOrderToProcess(View view) {

    }

    public void backArrow(View view) {
        onBackPressed();
    }
}
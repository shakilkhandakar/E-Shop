package com.example.e_shop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.e_shop.R;

public class MyOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

    }

    public void continueShopping(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void backArrow(View view) {
        onBackPressed();
    }
}
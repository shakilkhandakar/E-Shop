package com.example.e_shop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_shop.R;
import com.squareup.picasso.Picasso;

public class ConfirmActivityForSingleItem extends AppCompatActivity {
    ImageView imageView;
    TextView productName, productPrice, productPiece, totalProductPrice;
    String imageUrl;
    String name;
    String price;
    ImageButton decreaseButton, increaseButton;
    int piece, totalPrice;
    EditText buyerNameInputEditText, buyerPhoneInputEditText, buyerEmailInputEditText,
            buyerCityInputEditText, buyerAddressInputEditText;
    String deliveryName, deliveryPhone, deliveryEmail, deliveryCity, deliveryAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_for_single_item);

        imageView = findViewById(R.id.confirmForSingleItemImageView);
        productName = findViewById(R.id.confirmForSingleItemProductName);
        productPrice = findViewById(R.id.confirmForSingleItemProductPrice);
        productPiece = findViewById(R.id.confirmForSingleItemTotalOrderItemNumber);
        totalProductPrice = findViewById(R.id.confirmForSingleItemTotalProductPrice);

        decreaseButton = findViewById(R.id.confirm_decreaseButton);
        increaseButton = findViewById(R.id.confirm_increaseButton);

        buyerNameInputEditText = findViewById(R.id.confirmSingleNameEditText);
        buyerPhoneInputEditText = findViewById(R.id.confirmSinglePhoneEditText);
        buyerEmailInputEditText = findViewById(R.id.confirmSingleEmailEditText);
        buyerCityInputEditText = findViewById(R.id.confirmSingleCityEditText);
        buyerAddressInputEditText = findViewById(R.id.confirmSingleAddressEditText);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageUrl = bundle.getString("image");
            name = bundle.getString("name");
            price = bundle.getString("price");
            piece = bundle.getInt("piece");
        }

        Picasso.get().load(imageUrl).into(imageView);
        productName.setText(name);
        productPrice.setText(price);
        productPiece.setText(String.valueOf(piece));
        totalProductPrice.setText(price);

        deliveryName = buyerNameInputEditText.getText().toString();
        deliveryPhone = buyerPhoneInputEditText.getText().toString();
        deliveryEmail = buyerEmailInputEditText.getText().toString();
        deliveryCity = buyerCityInputEditText.getText().toString();
        deliveryAddress = buyerAddressInputEditText.getText().toString();

        decreaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalCount = Integer.parseInt(String.valueOf(productPiece.getText()));
                if (totalCount > 1) {
                    totalCount--;
                    productPiece.setText(String.valueOf(totalCount));
                    totalPrice = Integer.parseInt(price)*totalCount;
                    totalProductPrice.setText(String.valueOf(totalPrice));
                }
            }
        });
        increaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalCount = Integer.parseInt(String.valueOf(productPiece.getText()));
                totalCount++;
                productPiece.setText(String.valueOf(totalCount));
                totalPrice = Integer.parseInt(price)*totalCount;
                totalProductPrice.setText(String.valueOf(totalPrice));
            }
        });

    }

    public void confirmSingleOrderToProcess(View view) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void backArrow(View view) {
        onBackPressed();
    }
}
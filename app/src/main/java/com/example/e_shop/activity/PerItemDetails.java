package com.example.e_shop.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.R;
import com.example.e_shop.adapter.SuggestionAdapter;
import com.example.e_shop.model.CartModel;
import com.example.e_shop.model.SuggestionModel;
import com.example.e_shop.model.WishListModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PerItemDetails extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static ImageView imageView;
    @SuppressLint("StaticFieldLeak")
    public static TextView productName, productPrice, productDescription, cartCounter;
    public static String imageUrl, w_name;
    CheckBox wishListButton;
    int hasWishChild;
    Button addToCartButton, orderNowButton;
    private String name, price, description;
    RecyclerView suggestionRecyclerView;
    private SuggestionAdapter suggestionAdapter;
    private List<SuggestionModel> suggestionModelList;

    String[]suggestionImageUrl;
    String[]suggestionName;
    String[]suggestionPrice;
    String[]suggestionDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_item_details);

        imageView = findViewById(R.id.perDetailsItemImageView);
        productName = findViewById(R.id.perDetailsItemName);
        productPrice = findViewById(R.id.perDetailsItemPrice);
        productDescription = findViewById(R.id.productDescription);
        cartCounter = findViewById(R.id.cartCounterTextView);
        suggestionRecyclerView = findViewById(R.id.suggestionRecyclerView);
        wishListButton = findViewById(R.id.wishListId);
        addToCartButton = findViewById(R.id.addToCartButton);
        orderNowButton = findViewById(R.id.buyNowButton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageUrl = bundle.getString("image");
            name = bundle.getString("name");
            price = bundle.getString("price");
            description = bundle.getString("description");

            suggestionImageUrl = (String[]) bundle.getSerializable("s_image");
            suggestionName = (String[]) bundle.getSerializable("s_name");
            suggestionPrice = (String[]) bundle.getSerializable("s_price");
            suggestionDescription = (String[]) bundle.getSerializable("description");
        }

        Picasso.get().load(imageUrl).into(imageView);
        productName.setText(name);
        productPrice.setText(price);
        productDescription.setText(R.string.itemDescription);

        checkTotalCartItemNumber();
        checkAlreadyAddedWishListItem();
        setUpSuggestionProduct();

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAddToCart();
            }
        });

        orderNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), ConfirmActivityForSingleItem.class)
                        .putExtra("image", imageUrl)
                        .putExtra("name", productName.getText().toString())
                        .putExtra("price", productPrice.getText().toString())
                        .putExtra("piece", 1));
            }
        });

        wishListButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (hasWishChild == 1) {
                        removeWisListValue();
                    } else {
                        upLoadWishListValue();
                    }
                } else {
                    removeWisListValue();
                }
            }
        });

    }

    private void valueAddToCart() {
        String p_name, p_price;
        p_name = productName.getText().toString();
        p_price = productPrice.getText().toString();
//                check which value is already exist in firebase
        FirebaseDatabase.getInstance().getReference("Cart Product Information")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(p_name)) {
                            Toast.makeText(PerItemDetails.this, "This Product Already Available in Cart", Toast.LENGTH_SHORT).show();
                        } else {
                            CartModel item = new CartModel(imageUrl, p_name, p_price, 1);
                            upLoadCart(item, p_name);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PerItemDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //     get total cart item number from firebase
    private void checkTotalCartItemNumber() {
        FirebaseDatabase.getInstance().getReference("Cart Product Information").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() != 0) {
                    PerItemDetails.cartCounter.setVisibility(View.VISIBLE);
                    PerItemDetails.cartCounter.setText(String.valueOf(snapshot.getChildrenCount()));
                } else {
                    PerItemDetails.cartCounter.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PerItemDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //       check which item already available wishList
    private void checkAlreadyAddedWishListItem() {
        FirebaseDatabase.getInstance().getReference("WishList Product Information")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        w_name = productName.getText().toString();
                        if (snapshot.hasChild(w_name)) {
                            wishListButton.setButtonDrawable(R.drawable.ic_baseline_favorite_24);
                            hasWishChild = 1;
                        } else {
                            hasWishChild = 0;
                            wishListButton.setButtonDrawable(R.drawable.ic_heart_blank);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PerItemDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void setUpSuggestionProduct() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        suggestionRecyclerView.setLayoutManager(layoutManager);
        suggestionRecyclerView.setHasFixedSize(true);

//      here fetch suggestion data by category wise
        suggestionModelList = new ArrayList<>();
        for (int i = 0; i < suggestionName.length; i++) {
            SuggestionModel item = new SuggestionModel(suggestionImageUrl[i], suggestionName[i], suggestionPrice[i],null);
            suggestionModelList.add(item);
        }
        suggestionAdapter = new SuggestionAdapter(this, suggestionModelList);
        suggestionRecyclerView.setAdapter(suggestionAdapter);
        suggestionAdapter.notifyDataSetChanged();
    }

    private void upLoadCart(CartModel item, String p_name) {
        FirebaseDatabase.getInstance().getReference("Cart Product Information").child(p_name)
                .setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Snackbar.make(findViewById(android.R.id.content), "Product Added to cart", Snackbar.LENGTH_LONG)
                        .setAction("Go to Cart", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getApplicationContext(), MyCartListActivity.class));
                            }
                        })
                        .setActionTextColor(getResources().getColor(R.color.primaryColor))
                        .show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PerItemDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Upload WishList value
    private void upLoadWishListValue() {
        wishListButton.setButtonDrawable(R.drawable.ic_baseline_favorite_24);
        String p_name, p_price;
        p_name = productName.getText().toString();
        p_price = productPrice.getText().toString();

        WishListModel item = new WishListModel(imageUrl, p_name, p_price, null);
        FirebaseDatabase.getInstance().getReference("WishList Product Information").child(p_name)
                .setValue(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PerItemDetails.this, "Product Added to WishList", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PerItemDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //    here remove wish list value
    private void removeWisListValue() {
        wishListButton.setButtonDrawable(R.drawable.ic_heart_blank);
        FirebaseDatabase.getInstance().getReference("WishList Product Information")
                .child(productName.getText().toString()).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PerItemDetails.this, "Product remove from wishList", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void backArrow(View view) {
       onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void cartIconButton(View view) {
        startActivity(new Intent(this, MyCartListActivity.class));
    }
}
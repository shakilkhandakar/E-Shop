package com.example.e_shop.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.R;
import com.example.e_shop.adapter.PerCatItemAdapter;
import com.example.e_shop.model.PerCatModel;

import java.util.ArrayList;
import java.util.List;


public class PerCategoryAll_item extends AppCompatActivity {
    EditText searchBar;
    private RecyclerView perCatItemRecycler;
    private PerCatItemAdapter adapter;
    private List<PerCatModel> perCatModelList;
    public static String[] productPrice;
    public static String[] productImageUrl;
    public static String[] productName;
    public static String[] description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_category_all_item);

        searchBar = findViewById(R.id.searchIdForPerCategoryAllItem);
        perCatItemRecycler = findViewById(R.id.perCatAll_itemRecyclerView);

        perCatItemRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        perCatItemRecycler.setHasFixedSize(true);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productImageUrl = (String[]) bundle.getSerializable("image");
            productName = (String[]) bundle.getSerializable("name");
            productPrice = (String[]) bundle.getSerializable("price");
            description = (String[]) bundle.getSerializable("description");
        }

        perCatModelList = new ArrayList<>();
        for (int i = 0; i < productName.length; i++) {
            PerCatModel item = new PerCatModel(productImageUrl[i], productName[i], productPrice[i], null);
            perCatModelList.add(item);
        }
        adapter = new PerCatItemAdapter(this, perCatModelList);
        perCatItemRecycler.setAdapter(adapter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<PerCatModel>catModelList=new ArrayList<>();
                for (PerCatModel data:perCatModelList){
                        if (data.getPerCatItemName().toLowerCase().contains(s.toString().toLowerCase())){
                            catModelList.add(data);
                        }
                }
                adapter.getFilter(catModelList);
            }

            @Override
            public void afterTextChanged(Editable s) {

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
    public void clearSearchText(View view) {
        searchBar.setText(null);
    }
}
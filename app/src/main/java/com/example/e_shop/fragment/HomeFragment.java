package com.example.e_shop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_shop.R;
import com.example.e_shop.adapter.BookCatAdapter;
import com.example.e_shop.adapter.ElectronicsCatAdapter;
import com.example.e_shop.adapter.FashionCatAdapter;
import com.example.e_shop.adapter.FurnitureCatAdapter;
import com.example.e_shop.adapter.ShoesCatAdapter;
import com.example.e_shop.adapter.ShopCategoriesAdapter;
import com.example.e_shop.adapter.SliderAdapter;
import com.example.e_shop.adapter.SportsCatAdapter;
import com.example.e_shop.adapter.ToysCatAdapter;
import com.example.e_shop.model.BookCatModel;
import com.example.e_shop.model.ElectronicsCatModel;
import com.example.e_shop.model.FashionCatModel;
import com.example.e_shop.model.FurnitureCatModel;
import com.example.e_shop.model.ShoesCatModel;
import com.example.e_shop.model.ShopCategoriesModel;
import com.example.e_shop.model.SliderModel;
import com.example.e_shop.model.SportsCatModel;
import com.example.e_shop.model.ToyCatModel;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomeFragment extends Fragment {
    private Context context;
    //    all recyclerView declare here
    private RecyclerView recyclerView, bookRecyclerView, shoesRecyclerView, toysRecyclerView, sportsRecyclerView,
            fashionRecyclerView, furnitureRecyclerView, electronicsRecyclerView;

    //    all adapter declare here
    private ShopCategoriesAdapter adapter;
    private SliderAdapter sliderAdapter;
    private BookCatAdapter bookCatAdapter;
    private ShoesCatAdapter shoesCatAdapter;
    private ToysCatAdapter toysCatAdapter;
    private SportsCatAdapter sportsCatAdapter;
    private FashionCatAdapter fashionCatAdapter;
    private FurnitureCatAdapter furnitureCatAdapter;
    private ElectronicsCatAdapter electronicsCatAdapter;

    //    all ArrayList declare here
    private List<ShopCategoriesModel> shopCategoriesModelList;
    private List<SliderModel> sliderList;
    private List<BookCatModel> bookCatModelList;
    private List<ShoesCatModel> shoesCatModelList;
    private List<ToyCatModel> toyCatModelList;
    private List<SportsCatModel> sportsCatModelList;
    private List<FashionCatModel> fashionCatModelList;
    private List<FurnitureCatModel> furnitureCatModelList;
    private List<ElectronicsCatModel> electronicsCatModelList;

    private String[] shopCatName;
    private int[] shopCatIcon = {R.drawable.ic_baseline_home_24, R.drawable.ic_baseline_menu_book_24,
            R.drawable.ic_shoe, R.drawable.ic_toys, R.drawable.ic_baseline_sports_kabaddi_24,
            R.drawable.ic_sewing, R.drawable.ic_lamp, R.drawable.ic_baseline_electrical_services_24};
    private SliderView sliderView;

    private String productUrl="";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.categoriesRecyclerView);
        sliderView = view.findViewById(R.id.imageSlider);

//        finding categories recyclerview
        bookRecyclerView = view.findViewById(R.id.bookCategoriesRecyclerView);
        shoesRecyclerView = view.findViewById(R.id.shoesCategoriesRecyclerView);
        toysRecyclerView = view.findViewById(R.id.toysCategoriesRecyclerView);
        sportsRecyclerView = view.findViewById(R.id.sportsCategoriesRecyclerView);
        fashionRecyclerView = view.findViewById(R.id.fashionCategoriesRecyclerView);
        furnitureRecyclerView = view.findViewById(R.id.furnitureCategoriesRecyclerView);
        electronicsRecyclerView = view.findViewById(R.id.electronicsCategoriesRecyclerView);

//        shop categories item set here
        setLayoutManager(recyclerView);
        shopCategoriesModelList = new ArrayList<>();
        shopCatName = getResources().getStringArray(R.array.shopCategoriesName);

        for (int i = 0; i < shopCatName.length; i++) {
            ShopCategoriesModel model = new ShopCategoriesModel(shopCatIcon[i], shopCatName[i]);
            shopCategoriesModelList.add(model);
        }
        adapter = new ShopCategoriesAdapter(context, shopCategoriesModelList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        setUpSlider();
        setUpBookItems();
        setUpShoesItems();
        setUpToysItems();
        setUpSportsItems();
        setUpFashionItems();
        setUpFurnitureItems();
        setUpElectronicsItems();
        return view;
    }

    private void setUpSlider() {
        String[] imageUrl=getResources().getStringArray(R.array.sliderImageUrl);
        sliderList = new ArrayList<>();
        for (String s : imageUrl) {
            SliderModel image = new SliderModel(s);
            sliderList.add(image);
        }
        sliderAdapter = new SliderAdapter(context, sliderList);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderAdapter.notifyDataSetChanged();

    }

    private void setUpBookItems() {
        String[] imageUrl=getResources().getStringArray(R.array.bookImageUrl);
        String[] itemName = getResources().getStringArray(R.array.bookName);
        String[] itemPrice = getResources().getStringArray(R.array.bookPrice);

        setLayoutManager(bookRecyclerView);
        bookCatModelList = new ArrayList<>();
        for (int i = 0; i < itemName.length; i++) {
            BookCatModel item = new BookCatModel(imageUrl[i], itemName[i],itemPrice[i],null);
            bookCatModelList.add(item);
        }
        bookCatAdapter = new BookCatAdapter(context, bookCatModelList);
        bookCatAdapter.notifyDataSetChanged();
        bookRecyclerView.setAdapter(bookCatAdapter);
    }

    private void setUpShoesItems() {
        String[] imageUrl=getResources().getStringArray(R.array.shoesImageUrl);
        String[] itemName = getResources().getStringArray(R.array.shoesName);
        String[] itemPrice = getResources().getStringArray(R.array.shoesPrice);

        setLayoutManager(shoesRecyclerView);
        shoesCatModelList = new ArrayList<>();
        for (int i = 0; i < itemName.length; i++) {
            ShoesCatModel item = new ShoesCatModel(imageUrl[i], itemName[i],itemPrice[i],null);
            shoesCatModelList.add(item);
        }
        shoesCatAdapter = new ShoesCatAdapter(context, shoesCatModelList);
        shoesCatAdapter.notifyDataSetChanged();
        shoesRecyclerView.setAdapter(shoesCatAdapter);
    }

    private void setUpToysItems() {
        String[] imageUrl=getResources().getStringArray(R.array.toysImageUrl);
        String[] itemName = getResources().getStringArray(R.array.toysName);
        String[] itemPrice = getResources().getStringArray(R.array.toysPrice);

        setLayoutManager(toysRecyclerView);
        toyCatModelList = new ArrayList<>();
        for (int i = 0; i < itemName.length; i++) {
            ToyCatModel item = new ToyCatModel(imageUrl[i], itemName[i],itemPrice[i],null);
            toyCatModelList.add(item);
        }
        toysCatAdapter = new ToysCatAdapter(context, toyCatModelList);
        toysCatAdapter.notifyDataSetChanged();
        toysRecyclerView.setAdapter(toysCatAdapter);
    }

    private void setUpSportsItems() {
        String[] imageUrl=getResources().getStringArray(R.array.sportsImageUrl);
        String[] itemName = getResources().getStringArray(R.array.sportsName);
        String[] itemPrice = getResources().getStringArray(R.array.sportsPrice);

        setLayoutManager(sportsRecyclerView);
        sportsCatModelList = new ArrayList<>();
        for (int i = 0; i < itemName.length; i++) {
            SportsCatModel item = new SportsCatModel(imageUrl[i], itemName[i],itemPrice[i],null);
            sportsCatModelList.add(item);
        }
        sportsCatAdapter = new SportsCatAdapter(context, sportsCatModelList);
        sportsCatAdapter.notifyDataSetChanged();
        sportsRecyclerView.setAdapter(sportsCatAdapter);
    }

    private void setUpFashionItems() {
        String[] imageUrl=getResources().getStringArray(R.array.fashionImageUrl);
        String[] itemName = getResources().getStringArray(R.array.fashionName);
        String[] itemPrice = getResources().getStringArray(R.array.fashionPrice);

        setLayoutManager(fashionRecyclerView);
        fashionCatModelList = new ArrayList<>();
        for (int i = 0; i < itemName.length; i++) {
            FashionCatModel item = new FashionCatModel(imageUrl[i], itemName[i],itemPrice[i],null);
            fashionCatModelList.add(item);
        }
        fashionCatAdapter = new FashionCatAdapter(context, fashionCatModelList);
        fashionCatAdapter.notifyDataSetChanged();
        fashionRecyclerView.setAdapter(fashionCatAdapter);
    }

    private void setUpFurnitureItems() {
        String[] imageUrl=getResources().getStringArray(R.array.furnitureImageUrl);
        String[] itemName = getResources().getStringArray(R.array.furnitureName);
        String[] itemPrice = getResources().getStringArray(R.array.furniturePrice);

        setLayoutManager(furnitureRecyclerView);
        furnitureCatModelList = new ArrayList<>();
        for (int i = 0; i < itemName.length; i++) {
            FurnitureCatModel item = new FurnitureCatModel(imageUrl[i], itemName[i],itemPrice[i],null);
            furnitureCatModelList.add(item);
        }
        furnitureCatAdapter = new FurnitureCatAdapter(context, furnitureCatModelList);
        furnitureCatAdapter.notifyDataSetChanged();
        furnitureRecyclerView.setAdapter(furnitureCatAdapter);
    }

    private void setUpElectronicsItems() {
        String[] imageUrl=getResources().getStringArray(R.array.electronicsImageUrl);
        String[] itemName = getResources().getStringArray(R.array.electronicsName);
        String[] itemPrice = getResources().getStringArray(R.array.electronicsPrice);

        setLayoutManager(electronicsRecyclerView);
        electronicsCatModelList = new ArrayList<>();
        for (int i = 0; i < itemName.length; i++) {
            ElectronicsCatModel item = new ElectronicsCatModel(imageUrl[i], itemName[i],itemPrice[i],null);
            electronicsCatModelList.add(item);
        }
        electronicsCatAdapter = new ElectronicsCatAdapter(context, electronicsCatModelList);
        electronicsCatAdapter.notifyDataSetChanged();
        electronicsRecyclerView.setAdapter(electronicsCatAdapter);
    }

    private void setLayoutManager(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void fetchingProductInformation() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, productUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Please check your internet connection and try again !", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
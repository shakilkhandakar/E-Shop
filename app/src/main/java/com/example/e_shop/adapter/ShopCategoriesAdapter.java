package com.example.e_shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.R;
import com.example.e_shop.activity.PerCategoryAll_item;
import com.example.e_shop.model.ShopCategoriesModel;

import java.util.List;

public class ShopCategoriesAdapter extends RecyclerView.Adapter<ShopCategoriesAdapter.CategoriesHolder> {
    private Context context;
    private List<ShopCategoriesModel> categoryList;

    public ShopCategoriesAdapter(Context context, List<ShopCategoriesModel> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_categories_layout, parent, false);
        return new CategoriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesHolder holder, int position) {
        ShopCategoriesModel data = categoryList.get(position);
        holder.categoriesImage.setImageResource(data.getCat_icon());
        holder.categoriesName.setText(data.getCat_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position==0){
                   holder.itemView.setClickable(false);
                }
                else if (position==1){
                String[] price = context.getResources().getStringArray(R.array.bookPrice);
                String[] imageUrl = context.getResources().getStringArray(R.array.bookImageUrl);
                String[] name = context.getResources().getStringArray(R.array.bookName);
                String[] description=null;
                sendDataPerWiseCategories(imageUrl,name,price,description);
                 }
                else if (position==2){
                String[] price = context.getResources().getStringArray(R.array.shoesPrice);
                String[] imageUrl = context.getResources().getStringArray(R.array.shoesImageUrl);
                String[] name = context.getResources().getStringArray(R.array.shoesName);
                String[] description=null;
                sendDataPerWiseCategories(imageUrl,name,price,description);
                 }
                else if (position==3){
                String[] price = context.getResources().getStringArray(R.array.toysPrice);
                String[] imageUrl = context.getResources().getStringArray(R.array.toysImageUrl);
                String[] name = context.getResources().getStringArray(R.array.toysName);
                String[] description=null;
                sendDataPerWiseCategories(imageUrl,name,price,description);
                 }
                else if (position==4){
                String[] price = context.getResources().getStringArray(R.array.sportsPrice);
                String[] imageUrl = context.getResources().getStringArray(R.array.sportsImageUrl);
                String[] name = context.getResources().getStringArray(R.array.sportsName);
                String[] description=null;
                sendDataPerWiseCategories(imageUrl,name,price,description);
                 }
                else if (position==5){
                String[] price = context.getResources().getStringArray(R.array.fashionPrice);
                String[] imageUrl = context.getResources().getStringArray(R.array.fashionImageUrl);
                String[] name = context.getResources().getStringArray(R.array.fashionName);
                String[] description=null;
                sendDataPerWiseCategories(imageUrl,name,price,description);
                 }
                else if (position==6){
                String[] price = context.getResources().getStringArray(R.array.furniturePrice);
                String[] imageUrl = context.getResources().getStringArray(R.array.furnitureImageUrl);
                String[] name = context.getResources().getStringArray(R.array.furnitureName);
                String[] description=null;
                sendDataPerWiseCategories(imageUrl,name,price,description);
                 }
                else if (position==7){
                String[] price = context.getResources().getStringArray(R.array.electronicsPrice);
                String[] imageUrl = context.getResources().getStringArray(R.array.electronicsImageUrl);
                String[] name = context.getResources().getStringArray(R.array.electronicsName);
                String[] description=null;
                sendDataPerWiseCategories(imageUrl,name,price,description);
                 }
            }
        });
    }

    private void sendDataPerWiseCategories(String[] imageUrl, String[] name, String[] price, String[] description) {
        context.startActivity(new Intent(context, PerCategoryAll_item.class)
                .putExtra("image", imageUrl)
                .putExtra("name", name)
                .putExtra("price", price)
                .putExtra("description", description));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class CategoriesHolder extends RecyclerView.ViewHolder {
        ImageView categoriesImage;
        TextView categoriesName;

        public CategoriesHolder(@NonNull View itemView) {
            super(itemView);
            categoriesImage = itemView.findViewById(R.id.categoriesImageView);
            categoriesName = itemView.findViewById(R.id.categoriesName);
        }
    }
}

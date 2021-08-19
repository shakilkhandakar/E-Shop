package com.example.e_shop.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.e_shop.R;
import com.example.e_shop.fragment.HomeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle(null);
        toolbar = findViewById(R.id.toolBarId);
        frameLayout = findViewById(R.id.main_frameLayout);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawerLayoutId);

//      DrawerLayout implementation
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.my_order:
                        startActivity(new Intent(getApplicationContext(), MyOrderActivity.class));
                        break;
                    case R.id.my_cart:
                        startActivity(new Intent(getApplicationContext(), MyCartListActivity.class));
                        break;
                    case R.id.my_wishList:
                        startActivity(new Intent(getApplicationContext(), MyWishListActivity.class));
                        break;
                    case R.id.my_account:
                        startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));
                        break;
                    case R.id.sign_out:
                        processToSignOut();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        setFragment(new HomeFragment());
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }


    //    Search bar implementation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchView search = (SearchView) menu.findItem(R.id.search_id).getActionView();
        return true;
    }

    private void processToSignOut() {

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
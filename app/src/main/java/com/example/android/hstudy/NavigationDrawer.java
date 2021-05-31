package com.example.android.hstudy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.net.URL;

import model.accounts.StudentResponse;
import model.courses.Course;
import retrofit.APIUtils;
import retrofit.service.AccountService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected TextView headerTitle, headerEmail;
    protected  ImageView headerImage;

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected Toolbar toolbar;

    protected AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        accountService = APIUtils.getAccountService();

        // Handle header menu business logic.
        View headerView = navigationView.getHeaderView(0);
        headerTitle = (TextView) headerView.findViewById(R.id.username);
        headerEmail = (TextView) headerView.findViewById(R.id.email);
        headerImage = (ImageView) headerView.findViewById(R.id.account_image);

        SharedPreferences prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String user = prefs.getString("user", null);

        String[] chunks = user.split("\\.");
        String payload = new String(android.util.Base64.decode(chunks[1], Base64.DEFAULT));
        JsonObject headerPayload = new Gson().fromJson(payload, JsonObject.class);

        Call<StudentResponse> call = accountService.getStudent(Integer.parseInt(headerPayload.get("id").toString()));
        call.enqueue(new Callback<StudentResponse>() {
            @Override
            public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                headerTitle.setText(response.body().getData().getUsername());
                headerEmail.setText(response.body().getData().getEmail());
            }

            @Override
            public void onFailure(Call<StudentResponse> call, Throwable t) {
                Toast.makeText(NavigationDrawer.this,"Failed",Toast.LENGTH_SHORT);
            }
        });




        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    public Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_logout:
                getSharedPreferences("MyPref", MODE_PRIVATE).edit().clear().apply();
                intent = new Intent(NavigationDrawer.this, Login.class);
                startActivity(intent);
                break;
            case R.id.nav_account:
                intent = new Intent(NavigationDrawer.this, AccountActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_catalog:
                intent = new Intent(NavigationDrawer.this,Catalog.class);
                startActivity(intent);
                break;
            case R.id.nav_password:
                intent = new Intent(NavigationDrawer.this,ChangePassword.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}

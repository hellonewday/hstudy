package com.example.android.hstudy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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



public class NavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected TextView headerTitle, headerEmail;

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        // Handle header menu business logic.
        View headerView = navigationView.getHeaderView(0);
        headerTitle = (TextView) headerView.findViewById(R.id.username);
        headerEmail = (TextView) headerView.findViewById(R.id.email);

        SharedPreferences prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String user = prefs.getString("user", null);

        String[] chunks = user.split("\\.");
        String payload = new String(android.util.Base64.decode(chunks[1], Base64.DEFAULT));
        JsonObject headerPayload = new Gson().fromJson(payload, JsonObject.class);

        headerTitle.setText(headerPayload.get("username").getAsString());
        headerEmail.setText("Student ID: " + headerPayload.get("id").toString());


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

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_logout:
                SharedPreferences preferences = getSharedPreferences("Mypref", 0);
                preferences.edit().remove("user").commit();

                Intent intent = new Intent(NavigationDrawer.this, Login.class);
                startActivity(intent);
                break;
            case R.id.nav_account:
                Toast.makeText(NavigationDrawer.this, "Account", Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_catalog:
                Toast.makeText(NavigationDrawer.this, "Catalog", Toast.LENGTH_LONG).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}

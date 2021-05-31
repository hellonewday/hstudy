package com.example.android.hstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.BasicResponse;
import retrofit.APIUtils;
import retrofit.service.AccountService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends NavigationDrawer {
    TextInputLayout inp1, inp2;
    Button btn1;
    AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_change_password, null, false);
        drawerLayout.addView(contentView, 0);

        View thisView = findViewById(R.id.changePassword_view);

        inp1 = findViewById(R.id.old_password);
        inp2 = findViewById(R.id.new_password);
        btn1 = findViewById(R.id.submitChange);

        SharedPreferences prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String user = prefs.getString("user", null);

        String[] chunks = user.split("\\.");
        String payload = new String(android.util.Base64.decode(chunks[1], Base64.DEFAULT));
        JsonObject headerPayload = new Gson().fromJson(payload, JsonObject.class);
        String username = headerPayload.get("username").getAsString();

        accountService = APIUtils.getAccountService();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<BasicResponse> call = accountService.changePassword(username, inp1.getEditText().getText().toString(), inp2.getEditText().getText().toString());
                call.enqueue(new Callback<BasicResponse>() {
                    @Override
                    public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                        Snackbar.make(thisView,R.string.change_password,Snackbar.LENGTH_LONG).setAction("Log out", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getSharedPreferences("MyPref", MODE_PRIVATE).edit().clear().apply();
                                Intent intent = new Intent(ChangePassword.this, Login.class);
                                startActivity(intent);
                            }
                        }).show();
                    }

                    @Override
                    public void onFailure(Call<BasicResponse> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });


    }
}
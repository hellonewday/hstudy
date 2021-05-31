package com.example.android.hstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import model.accounts.Account;
import model.accounts.LoginResponse;
import retrofit.APIUtils;
import retrofit.service.AccountService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    Button btnLogin;
    TextInputLayout ed1, ed2;
    Account myAccount;
    AccountService accountService;
    TextView txt1, txt2, txt3, txt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        btnLogin = findViewById(R.id.loginbtn);
        ed1 = findViewById(R.id.username);
        ed2 = findViewById(R.id.password);

        txt1 = findViewById(R.id.register);
        txt2 = findViewById(R.id.title);
        txt3 = findViewById(R.id.slogan);
        txt4 = findViewById(R.id.forgot);

        accountService = APIUtils.getAccountService();


//        account.setUsername(ed1.getEditText().getText());

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                Pair[] pairs = new Pair[6];

                pairs[0] = new Pair<View, String>(txt2, "title_text");
                pairs[1] = new Pair<View, String>(txt3, "subTitle_text");
                pairs[2] = new Pair<View, String>(ed1, "username_input");
                pairs[3] = new Pair<View, String>(ed2, "password_input");
                pairs[4] = new Pair<View, String>(btnLogin, "submitBtn");
                pairs[5] = new Pair<View, String>(txt1, "redirect_text");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        txt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                Pair[] pairs = new Pair[6];

                pairs[0] = new Pair<View, String>(txt2, "title_text");
                pairs[1] = new Pair<View, String>(txt3, "subTitle_text");
                pairs[2] = new Pair<View, String>(ed1, "username_input");
                pairs[3] = new Pair<View, String>(ed2, "password_input");
                pairs[4] = new Pair<View, String>(btnLogin, "submitBtn");
                pairs[5] = new Pair<View, String>(txt1, "redirect_text");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAccount = new Account(ed1.getEditText().getText().toString(), ed2.getEditText().getText().toString());
                Log.i("Info:", "" + myAccount);
                // Goi du lieu len
                Call<LoginResponse> call = accountService.login(myAccount);
                // Lay du lieu ve
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        Log.i("Info: ", " " + response.body());

                        if (response.body() != null) {
                            SharedPreferences prefs = getSharedPreferences("MyPref", 0);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("user", response.body().getToken());
                            editor.commit();

                            Intent intent = new Intent(Login.this, SearchActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Login.this, "Wrong password", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(Login.this, "Failure", Toast.LENGTH_LONG).show();
                        Log.e("Error: ", t.getMessage());
                    }
                });

//                Toast.makeText(Login.this, myAccount.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
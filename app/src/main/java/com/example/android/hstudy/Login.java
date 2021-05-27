package com.example.android.hstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import model.accounts.Account;
import model.accounts.LoginResponse;
import model.courses.CourseResponse;
import model.courses.CoursesInfo;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.loginbtn);
        ed1 = findViewById(R.id.username);
        ed2 = findViewById(R.id.password);

        accountService = APIUtils.getAccountService();

//        account.setUsername(ed1.getEditText().getText());


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAccount = new Account(ed1.getEditText().getText().toString(), ed2.getEditText().getText().toString());
                Log.i("Info:", "" + myAccount);
                Call<LoginResponse> call = accountService.login(myAccount);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        Log.i("Info: ", " " + response.body());

                        if (response.body() != null) {
                            SharedPreferences prefs = getSharedPreferences("MyPref", 0);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("user",response.body().getToken());
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
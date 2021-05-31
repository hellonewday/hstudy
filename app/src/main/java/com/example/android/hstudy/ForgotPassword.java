package com.example.android.hstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import model.BasicResponse;
import retrofit.APIUtils;
import retrofit.service.AccountService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {
    TextInputLayout inp1, inp2;
    Button btn1;
    TextView txt1;
    AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View contentView = findViewById(R.id.forgotView);

        inp1 = findViewById(R.id.ask_username);
        inp2 = findViewById(R.id.new_password);
        btn1 = findViewById(R.id.updateForgot);
        txt1 = findViewById(R.id.loginDir);

        accountService = APIUtils.getAccountService();

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPassword.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<BasicResponse> call = accountService.forgotPassword(inp1.getEditText().getText().toString(), inp2.getEditText().getText().toString());
                call.enqueue(new Callback<BasicResponse>() {
                    @Override
                    public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                        Snackbar.make(contentView, R.string.retrieve_success, Snackbar.LENGTH_LONG).setAction("Login", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(ForgotPassword.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
                    }

                    @Override
                    public void onFailure(Call<BasicResponse> call, Throwable t) {

                    }
                });
            }
        });


    }
}
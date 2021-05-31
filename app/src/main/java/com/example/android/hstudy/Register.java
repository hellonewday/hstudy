package com.example.android.hstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import model.BasicResponse;
import retrofit.APIUtils;
import retrofit.service.AccountService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    TextInputLayout ed1, ed2, ed3, ed4, ed5;
    TextView txt1;
    Button btn1;
    Calendar userCalendar;
    AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View contentView = findViewById(R.id.registerView);
        ed1 = findViewById(R.id.username);
        ed2 = findViewById(R.id.email);
        ed3 = findViewById(R.id.dob);
        ed4 = findViewById(R.id.password);
        ed5 = findViewById(R.id.retype);
        txt1 = findViewById(R.id.loginDir);
        btn1 = findViewById(R.id.registerBtn);

        accountService = APIUtils.getAccountService();

        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<BasicResponse> call = accountService.register(
                        ed1.getEditText().getText().toString(),
                        ed4.getEditText().getText().toString(),
                        ed2.getEditText().getText().toString(),
                        ed3.getEditText().getText().toString()
                );
                call.enqueue(new Callback<BasicResponse>() {
                    @Override
                    public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                        Snackbar.make(contentView, R.string.register_success, Snackbar.LENGTH_LONG).setAction("Login", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
                                finish();
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

        userCalendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                userCalendar.set(Calendar.YEAR, year);
                userCalendar.set(Calendar.MONTH, month);
                userCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        ed3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Register.this, date, userCalendar
                        .get(Calendar.YEAR), userCalendar.get(Calendar.MONTH),
                        userCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        // 2021 - 05 - 15
        ed3.getEditText().setText(sdf.format(userCalendar.getTime()));
    }
}
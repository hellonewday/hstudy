package com.example.android.hstudy;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import model.BasicResponse;
import model.accounts.Account;
import model.accounts.StudentResponse;
import retrofit.APIUtils;
import retrofit.service.AccountService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends NavigationDrawer {

    AccountService accountService;
    TextInputLayout ed1, ed2, ed3;
    Button btn1;
    Calendar userCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_account, null, false);
        drawerLayout.addView(contentView, 0);

        // Bo dem: user {token}. => id username

        SharedPreferences prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String user = prefs.getString("user", null);

        String[] chunks = user.split("\\.");
        String payload = new String(android.util.Base64.decode(chunks[1], Base64.DEFAULT));
        JsonObject headerPayload = new Gson().fromJson(payload, JsonObject.class);

        ed1 = findViewById(R.id.edit_username);
        ed2 = findViewById(R.id.edit_email);
        ed3 = findViewById(R.id.edit_dob);
        btn1 = findViewById(R.id.submitEdit);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = ed1.getEditText().getText().toString();
                String dob = ed3.getEditText().getText().toString();
                String email = ed2.getEditText().getText().toString();
                Call<BasicResponse> call = accountService.updateStudent(Integer.parseInt(headerPayload.get("id").toString()), username, email, dob);

                call.enqueue(new Callback<BasicResponse>() {
                    @Override
                    public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                        Log.i("Result:",response.body().toString());
                        Snackbar.make(contentView, R.string.edit_success,Snackbar.LENGTH_LONG).setAction("Back", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(AccountActivity.this, SearchActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
                    }

                    @Override
                    public void onFailure(Call<BasicResponse> call, Throwable t) {
                        Log.i("Error",t.toString());
                    }
                });

            }
        });

        // Su dung calendar trong trang nay.
        userCalendar = Calendar.getInstance();
        // Tôi sẽ xử lý ntn với dữ liệu từ trong calendar này?
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                userCalendar.set(Calendar.YEAR, year);
                userCalendar.set(Calendar.MONTH, month);
                userCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        // Xu ly click vao cho nhap
        ed3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AccountActivity.this, date, userCalendar
                        .get(Calendar.YEAR), userCalendar.get(Calendar.MONTH),
                        userCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        accountService = APIUtils.getAccountService();
        Call<StudentResponse> call = accountService.getStudent(Integer.parseInt(headerPayload.get("id").toString()));

        call.enqueue(new Callback<StudentResponse>() {
            @Override
            public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                ed1.getEditText().setText(response.body().getData().getUsername());
                ed2.getEditText().setText(response.body().getData().getEmail());
                ed3.getEditText().setText(response.body().getData().getDob().split("T")[0]);
            }

            @Override
            public void onFailure(Call<StudentResponse> call, Throwable t) {

            }
        });


    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ed3.getEditText().setText(sdf.format(userCalendar.getTime()));
    }
}
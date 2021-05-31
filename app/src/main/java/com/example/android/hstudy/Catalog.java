package com.example.android.hstudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import model.accounts.StudentCourseResponse;
import model.accounts.StudentResponse;
import model.accounts.StudentVideoCounts;
import model.courses.Course;
import retrofit.APIUtils;
import retrofit.service.AccountService;
import retrofit.service.ManagerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Catalog extends NavigationDrawer {

    private RecyclerView recyclerView;
    TextView txt1, txt2, txt3, txt4;

    AccountService accountService;
    ManagerService managerService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_catalog, null, false);
        drawerLayout.addView(contentView, 0);

        txt1 = findViewById(R.id.username);
        txt2 = findViewById(R.id.email);

        txt3 = findViewById(R.id.courses_number);
        txt4 = findViewById(R.id.videos_number);

        managerService = APIUtils.getManagerService();
        accountService = APIUtils.getAccountService();

        SharedPreferences prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String user = prefs.getString("user", null);

        String[] chunks = user.split("\\.");
        String payload = new String(android.util.Base64.decode(chunks[1], Base64.DEFAULT));
        JsonObject headerPayload = new Gson().fromJson(payload, JsonObject.class);

        Call<StudentResponse> headerCall = accountService.getStudent(Integer.parseInt(headerPayload.get("id").toString()));
        headerCall.enqueue(new Callback<StudentResponse>() {
            @Override
            public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                txt1.setText(response.body().getData().getUsername());
                txt2.setText(response.body().getData().getEmail());
            }

            @Override
            public void onFailure(Call<StudentResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
        // Bo dem tren dien thoai -> lay id tu bo dem tren dien thoai
        Call<StudentCourseResponse> summaryCoursesCall = accountService.getStudentCourses(Integer.parseInt(headerPayload.get("id").toString()));
        summaryCoursesCall.enqueue(new Callback<StudentCourseResponse>() {
            @Override
            public void onResponse(Call<StudentCourseResponse> call, Response<StudentCourseResponse> response) {
                txt3.setText(response.body().getCourses().size() + "");

                Catalog.this.recyclerView = (RecyclerView) findViewById(R.id.enrolled_courses);
                recyclerView.setAdapter(new CourseAdapter(Catalog.this, response.body().getCourses()));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Catalog.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);

            }

            @Override
            public void onFailure(Call<StudentCourseResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        Call<StudentVideoCounts> summaryVideosCall = accountService.countStudentVideos(Integer.parseInt(headerPayload.get("id").toString()));
        summaryVideosCall.enqueue(new Callback<StudentVideoCounts>() {
            @Override
            public void onResponse(Call<StudentVideoCounts> call, Response<StudentVideoCounts> response) {
                txt4.setText(response.body().getCount() + "");
            }

            @Override
            public void onFailure(Call<StudentVideoCounts> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
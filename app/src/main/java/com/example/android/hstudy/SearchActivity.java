package com.example.android.hstudy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.chip.Chip;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

import model.courses.Course;
import model.courses.CourseResponse;
import model.courses.CoursesInfo;
import retrofit.APIUtils;
import retrofit.service.CourseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchActivity extends NavigationDrawer {

    Chip chip1, chip2, chip3;
    CourseService courseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_search, null, false);
        drawerLayout.addView(contentView, 0);

        chip1 = (Chip) findViewById(R.id.chip5);
        chip2 = (Chip) findViewById(R.id.chip6);


        courseService = APIUtils.getCourseService();

        chip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<CourseResponse> call = courseService.listSearchCourses("Java");
                call.enqueue(new Callback<CourseResponse>() {
                    @Override
                    public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                        if (response.isSuccessful()) {
                            CoursesInfo info;
                            info = response.body().getCoursesInfo();
//                            Toast.makeText(SearchActivity.this, "Number of records: " + info.getCounts(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SearchActivity.this, ListCourses.class);
                            intent.putExtra("courses", (Serializable) info.getCourses());
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<CourseResponse> call, Throwable t) {
                        Toast.makeText(SearchActivity.this, "Failure", Toast.LENGTH_LONG).show();
                        Log.e("Error: ", t.getMessage());
                    }
                });
            }
        });

        chip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this,"Hello World",Toast.LENGTH_LONG).show();
            }
        });


    }


}
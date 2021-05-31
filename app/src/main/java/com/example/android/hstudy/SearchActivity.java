package com.example.android.hstudy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
    TextInputLayout inp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_search, null, false);
        drawerLayout.addView(contentView, 0);

        chip1 = (Chip) findViewById(R.id.chip5);
        chip2 = (Chip) findViewById(R.id.chip6);
        chip3 = (Chip) findViewById(R.id.chip7);
        inp = findViewById(R.id.search);

        inp.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getSearchCourses(inp.getEditText().getText().toString());
                    Log.i("Info: ", inp.getEditText().getText().toString());
                }
                return false;
            }
        });


        courseService = APIUtils.getCourseService();

        chip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSearchCourses("Learning");
            }
        });

        chip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSearchCourses("Web");
            }
        });

        chip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSearchCourses("Python");
            }
        });


    }

    private void getSearchCourses(String query) {
        Call<CourseResponse> call = courseService.listSearchCourses(query);
        call.enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                if (response.isSuccessful()) {
                    CoursesInfo info;
                    info = response.body().getCoursesInfo();
//                            Toast.makeText(SearchActivity.this, "Number of records: " + info.getCounts(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SearchActivity.this, ListCourses.class);
                    intent.putExtra("courses", (Serializable) info.getCourses());
                    intent.putExtra("query", query);
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


}
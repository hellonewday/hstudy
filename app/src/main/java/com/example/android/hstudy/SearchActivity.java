package com.example.android.hstudy;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.chip.Chip;

import java.util.List;

import model.Course;
import model.CourseResponse;
import retrofit.APIUtils;
import retrofit.service.CourseService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    Chip chip1, chip2, chip3;
    CourseService courseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        chip1 = (Chip) findViewById(R.id.chip5);
        courseService = APIUtils.getCourseService();

        chip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<CourseResponse> call = courseService.listSearchCourses("Java");
                call.enqueue(new Callback<CourseResponse>() {
                    @Override
                    public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                        if (response.isSuccessful()) {
                            List<Course> data;
                            data = response.body().getCourseList();
                            Toast.makeText(SearchActivity.this, "Success", Toast.LENGTH_LONG).show();
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(SearchActivity.this, "Hello Again", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
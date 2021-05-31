package com.example.android.hstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;

import model.BasicResponse;
import model.courses.Course;
import model.manager.VideoResponse;
import retrofit.APIUtils;
import retrofit.service.ManagerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseDetail extends NavigationDrawer {

    private Course course;
    private VideoResponse videoResponse;
    ImageView imgView;
    TextView tv1, tv2, tv3;
    Button btn1;
    ManagerService managerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_course_detail, null, false);
        drawerLayout.addView(contentView, 0);

        tv1 = findViewById(R.id.courseName);
        tv2 = findViewById(R.id.author);
        tv3 = findViewById(R.id.description);
        btn1 = findViewById(R.id.enroll);
        imgView = findViewById(R.id.imageView5);


        Intent intent = getIntent();
        course = (Course) intent.getSerializableExtra("data");

        CourseAdapter adapter = new CourseAdapter();
        Bitmap courseBitmap = adapter.getBitmapFromUrl(course.getCourseImage());

        imgView.setImageBitmap(courseBitmap);
        tv1.setText(course.getCourseName());
        tv2.setText(course.getAuthor());
        tv3.setText(course.getDescription());

        SharedPreferences prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String user = prefs.getString("user", null);

        String[] chunks = user.split("\\.");
        String payload = new String(android.util.Base64.decode(chunks[1], Base64.DEFAULT));
        JsonObject headerPayload = new Gson().fromJson(payload, JsonObject.class);

        int userId = Integer.parseInt(headerPayload.get("id").toString());
        int courseId = course.getId();

        managerService = APIUtils.getManagerService();


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Undone:",videoResponse.getUndone() + "");
                if (!videoResponse.getUndone()) {
                    Call<BasicResponse> call = managerService.enrollCourseForStudent(courseId, userId);
                    call.enqueue(new Callback<BasicResponse>() {
                        @Override
                        public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                            Intent intent = new Intent(CourseDetail.this, VideoList.class);
                            intent.putExtra("videos", (Serializable) videoResponse.getData());
                            intent.putExtra("remains", videoResponse.getRemains());
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<BasicResponse> call, Throwable t) {
                            Snackbar.make(contentView, R.string.action_error, Snackbar.LENGTH_SHORT);
                        }
                    });
                } else {
                    Intent intent = new Intent(CourseDetail.this, VideoList.class);
                    intent.putExtra("videos", (Serializable) videoResponse.getData());
                    intent.putExtra("remains", videoResponse.getRemains());
                    startActivity(intent);
                    finish();
                }


            }
        });


        Call<VideoResponse> call = managerService.getVideoForStudent(courseId, userId);


        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                Log.i("Info: ", response.body().toString());
                videoResponse = response.body();
                if (response.body().getUndone()) {
                    btn1.setBackgroundColor(Color.rgb(32, 145, 92));
                    btn1.setText("Access");
                    Drawable go_icon = getResources().getDrawable(R.drawable.go);
                    go_icon.setBounds(0, 0, 60, 60);
                    btn1.setCompoundDrawables(null, null, go_icon, null);
                }


            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Toast.makeText(CourseDetail.this, "Error", Toast.LENGTH_SHORT);
            }
        });

    }
}
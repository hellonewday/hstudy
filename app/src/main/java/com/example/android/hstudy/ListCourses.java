package com.example.android.hstudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import model.courses.Course;

public class ListCourses extends NavigationDrawer {

    List<Course> data;
    TextView txt1, txt2;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_list_courses, null, false);
        drawerLayout.addView(contentView, 0);


        Intent i = getIntent();
        data = (List<Course>) i.getSerializableExtra("courses");
        String query = (String) i.getSerializableExtra("query");

        txt1 = (TextView) findViewById(R.id.textView5);
        txt2 = findViewById(R.id.searchTitle);

        txt1.setText("Number of courses: " + data.size());
        txt2.setText("Serch results for: " + query);

        this.recyclerView = (RecyclerView) this.findViewById(R.id.list_courses);

        recyclerView.setAdapter(new CourseAdapter(this, data));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ListCourses.this, SearchActivity.class);
        startActivity(intent);
        finish();
    }
}
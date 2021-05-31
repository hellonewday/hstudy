package com.example.android.hstudy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import model.courses.Course;
import model.manager.Video;

public class VideoList extends NavigationDrawer {
    List<Video> videos;
    TextView tvSummary;
    int remains;
    private RecyclerView recyclerView;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        MediaController mediaController = new MediaController(this);

        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_video_list, null, false);
        drawerLayout.addView(contentView, 0);

        tvSummary = findViewById(R.id.summary_title);
        pieChart = findViewById(R.id.summary);

        Intent i = getIntent();
        videos = (List<Video>) i.getSerializableExtra("videos");
        remains = (int) i.getSerializableExtra("remains");

        if (remains == 0) {
            tvSummary.setText("Great! You have finished this course.");
        } else {
            tvSummary.setText("Lesson remains: " + remains);

        }

        ArrayList<PieEntry> summaryRes = new ArrayList<>();

        summaryRes.add(new PieEntry(videos.size() - remains, "Finished"));
        summaryRes.add(new PieEntry(remains, "Remains"));

        PieDataSet pieDataSet = new PieDataSet(summaryRes,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(10f);


        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText(remains + " / "+ videos.size());
        pieChart.animate();


        this.recyclerView = (RecyclerView) this.findViewById(R.id.list_videos);
        recyclerView.setAdapter(new VideoAdapter(this, videos));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


    }
}
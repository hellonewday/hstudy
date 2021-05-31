package com.example.android.hstudy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.material.snackbar.Snackbar;

import model.manager.Video;

public class WatchVideo extends NavigationDrawer {

    Button btn1, btn2;
    TextView tvVideoName;
    VideoView videoView1;
    Video data;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_watch_video, null, false);
        drawerLayout.addView(contentView, 0);

        View thisView = findViewById(R.id.watch_video);

        Intent intent = getIntent();
        data = (Video) intent.getSerializableExtra("data");

        videoView1 = findViewById(R.id.videoView);
        tvVideoName = (TextView) findViewById(R.id.videoTitle);

        btn1 = findViewById(R.id.reportBtn);
        btn2 = findViewById(R.id.likeBtn);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(thisView, R.string.not_supported, Snackbar.LENGTH_LONG).setAction("OK", null).show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(thisView, R.string.not_supported, Snackbar.LENGTH_LONG).setAction("OK", null).show();
            }
        });


        tvVideoName.setText(data.getVideoName());
        videoView1.setVideoPath(data.getVideoUrl());
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView1);
        videoView1.setMediaController(mediaController);


    }
}
package com.example.android.hstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3000;

    Animation topAnimation, bottomAnimation;
    TextView title, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Animation : this: o giao dien nay
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        title = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);

        title.setAnimation(topAnimation);
        slogan.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Bo dem cua ung dung android.
                SharedPreferences prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                String user = prefs.getString("user", null);

                //
                Intent intent;
                if (user == null) {
                    intent = new Intent(MainActivity.this, Login.class);
                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View,String>(title,"title_text");
                    pairs[1] = new Pair<View,String>(slogan,"subTitle_text");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                    startActivity(intent,options.toBundle());
                } else {
                    intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                }

            }
        }, SPLASH_SCREEN);
    }
}
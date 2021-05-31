package com.example.android.hstudy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import model.BasicResponse;
import model.courses.Course;
import model.manager.Video;
import retrofit.APIUtils;
import retrofit.service.ManagerService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private List<Video> videos;
    private Context mcontext;
    private ManagerService managerService;

    public VideoAdapter() {
    }

    public VideoAdapter(Context context, List<Video> list) {
        videos = list;
        mcontext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvVideoName;
        ImageView doneIcon, playIcon;

        public ViewHolder(@NonNull View itemView, int i) {
            super(itemView);

            tvVideoName = itemView.findViewById(R.id.tvVideoName);
            doneIcon = itemView.findViewById(R.id.doneIcon);
            playIcon = itemView.findViewById(R.id.playIcon);

        }
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item, viewGroup, false);
        return new ViewHolder(v, i);
    }


    public Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }


    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder viewHolder, int i) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        managerService = APIUtils.getManagerService();

        viewHolder.itemView.setTag(videos.get(i));
        viewHolder.tvVideoName.setText(videos.get(i).getVideoName());

        SharedPreferences prefs = mcontext.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        String user = prefs.getString("user", null);

        String[] chunks = user.split("\\.");
        String payload = new String(android.util.Base64.decode(chunks[1], Base64.DEFAULT));
        JsonObject headerPayload = new Gson().fromJson(payload, JsonObject.class);

        if (videos.get(i).getIsDone()) {
            viewHolder.playIcon.setVisibility(View.INVISIBLE);
            viewHolder.doneIcon.setVisibility(View.VISIBLE);
        } else {
            viewHolder.playIcon.setVisibility(View.VISIBLE);
            viewHolder.doneIcon.setVisibility(View.INVISIBLE);
        }


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (videos.get(i).getIsDone() == false) {
                    Call<BasicResponse> call = managerService.watchVideoForStudent(videos.get(i).getId(), Integer.parseInt(headerPayload.get("id").toString()));
                    call.enqueue(new Callback<BasicResponse>() {
                        @Override
                        public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                            videos.get(i).setIsDone(true);
                            viewHolder.playIcon.setVisibility(View.INVISIBLE);
                            viewHolder.doneIcon.setVisibility(View.VISIBLE);
                            Log.i("Info:", response.body().getMessage());

                            Intent intent = new Intent(mcontext, WatchVideo.class);
                            intent.putExtra("data", videos.get(i));
                            mcontext.startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<BasicResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });

                } else {
                    Log.i("Info: ", "Video is ready");
                    Intent intent = new Intent(mcontext, WatchVideo.class);
                    intent.putExtra("data", videos.get(i));
                    mcontext.startActivity(intent);
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return videos.size();
    }
}

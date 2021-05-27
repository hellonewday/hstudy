package com.example.android.hstudy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import model.courses.Course;

class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>
{

    private List<Course> courses;
    private Context mcontext;
    public CourseAdapter (Context context,List<Course> list)
    {
        courses= list;
        mcontext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView courseImage;
        TextView tvCourseName,tvAuthor;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCourseName =itemView.findViewById(R.id.tvCourseName);
            tvAuthor=itemView.findViewById(R.id.tvAuthor);
            courseImage=itemView.findViewById(R.id.courseImage);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(mcontext,CourseDetail.class);
                    mcontext.startActivity(intent);

                }
            });
        }
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_item,viewGroup,false);
        return new ViewHolder(v);
    }


    public Bitmap getBitmapFromUrl(String imageUrl)
    {
        try
        {
            URL url = new URL(imageUrl);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;

        } catch (Exception e)
        {
            e.printStackTrace();
            return null;

        }
    }



    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder viewHolder, int i) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        viewHolder.itemView.setTag(courses.get(i));

        viewHolder.tvCourseName.setText(courses.get(i).getCourseName());
        viewHolder.tvAuthor.setText(courses.get(i).getAuthor());
        viewHolder.courseImage.setImageBitmap(getBitmapFromUrl(courses.get(i).getCourseImage()));

    }



    @Override
    public int getItemCount() {
        return courses.size();
    }
}

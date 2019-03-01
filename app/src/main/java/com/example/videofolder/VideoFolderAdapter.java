package com.example.videofolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class VideoFolderAdapter extends RecyclerView.Adapter<VideoFolderAdapter.MyViewHolder> {

    ArrayList<ModelVideo> allvideos;
    Context context;
    Activity activity;

    public VideoFolderAdapter(ArrayList<ModelVideo> allvideos, Context context, Activity activity) {
        this.allvideos = allvideos;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_video_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Glide.with(context).load("file://" + allvideos.get(position).getThum())

                .into(holder.imageView);
        holder.relativeLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        holder.relativeLayout.setAlpha(0);

        Log.e("path", allvideos.get(position).getPath());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_gallery = new Intent(context, PlayVideo.class);
                intent_gallery.putExtra("video", allvideos.get(position).getPath());

                activity.startActivity(intent_gallery);
            }
        });

    }

    @Override
    public int getItemCount() {
        return allvideos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.videoimage);
            relativeLayout = itemView.findViewById(R.id.relative);
        }
    }
}

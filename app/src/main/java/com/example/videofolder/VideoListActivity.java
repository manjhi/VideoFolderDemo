package com.example.videofolder;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class VideoListActivity extends AppCompatActivity {

    String getdata;
    VideoFolderAdapter videoFolderAdapter;
    RecyclerView recyclerView;
    ArrayList allvideo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);


        recyclerView = findViewById(R.id.get);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        getdata = intent.getStringExtra("MyData");

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                VideosData();
            }
        }, 500);

    }

    private void VideosData() {

        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name, column_id, thum;
        long duration;

        //File f = new File(Environment.getExternalStorageDirectory() + "/Videos");

        //  /storage/emulated/0/WhatsApp/Media/WhatsApp Video/VID-20180903-WA0002.mp4
        String absolutePathOfImage = null;
        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;


        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media._ID, MediaStore.Video.Thumbnails.DATA, MediaStore.Video.Media.DURATION};
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        String selection = MediaStore.Video.Media.DATA + " like?";
        String[] selectionArgs = new String[]{"%" + getdata + "%"};
        cursor = getApplicationContext().getContentResolver().query(uri, projection, selection, selectionArgs, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME);
        column_id = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
        thum = cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);
        duration = cursor.getColumnIndexOrThrow(
                MediaStore.Video.Media.DURATION);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));
            Log.e("column_id", cursor.getString(column_id));
            Log.e("thum", cursor.getString(thum));
            Log.e("duration", String.valueOf(duration));
            ModelVideo modelVideo = new ModelVideo();
            modelVideo.setSelected(false);
            modelVideo.setPath(absolutePathOfImage);
            modelVideo.setThum(cursor.getString(thum));
            allvideo.add(modelVideo);
        }
        videoFolderAdapter = new VideoFolderAdapter(allvideo, getApplicationContext(), VideoListActivity.this);
        recyclerView.setAdapter(videoFolderAdapter);
    }
}

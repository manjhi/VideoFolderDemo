package com.example.videofolder;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getPermisssion();

    }

    private void getPermisssion() {
        if (ActivityCompat.checkSelfPermission(Splash.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Splash.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        } else {
//            OpenDialog();
            init();
            Toast.makeText(Splash.this, "Granted", Toast.LENGTH_SHORT).show();
        }

    }

    private void init() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finishAffinity();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                boolean read = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                if (grantResults.length > 0 && read) {
                    init();
                    Toast.makeText(Splash.this, "Permission Granted", Toast.LENGTH_SHORT).show();

                } else if (Build.VERSION.SDK_INT > 23 && !shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);
                    builder.setTitle("Permissions");
                    builder.setMessage("Permissions are requeired");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Splash.this.getApplicationContext(), "Go to the setting for granting permissions", Toast.LENGTH_SHORT).show();
                            boolean sentToSettings = true;
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", Splash.this.getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    })
                            .create()
                            .show();

                } else {
                    Toast.makeText(Splash.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(Splash.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                }
                break;
        }
    }

}

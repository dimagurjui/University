package com.example.dima.lab1;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Vibrator;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button searchButton, notifyButton, cameraButton, vibrationButton;
    private EditText searchField;

    private ImageView mImageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init()
    {
        searchButton = (Button) findViewById(R.id.searchButton);
        notifyButton = (Button) findViewById(R.id.notifyButton);
        cameraButton = (Button) findViewById(R.id.cameraButton);
        vibrationButton = (Button) findViewById(R.id.vibrationButton);
        searchField = (EditText) findViewById(R.id.searchField);
        mImageView2 = (ImageView) findViewById(R.id.mImageView2);
        searchButton.setOnClickListener(this);
        notifyButton.setOnClickListener(this);
        cameraButton.setOnClickListener(this);
        vibrationButton.setOnClickListener(this);
    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Bravo!!!");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.utm);
        return builder.build();
    }

    static final int REQUEST_IMAGE_CAPTURE = 1888;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

            Intent cameraIntent = new Intent(MainActivity.this, ImageActivity.class);
            cameraIntent.putExtras(extras);
            startActivity(cameraIntent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notifyButton:
                scheduleNotification(getNotification("Ai așteptat 10 secunde"), 10000);
                break;

            case R.id.vibrationButton:
                Vibrator myVibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
                myVibrator.vibrate(5000);
                break;

            case R.id.cameraButton:
                dispatchTakePictureIntent();
                break;

            case R.id.searchButton:
                Intent i = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/#q=" + searchField.getText()));
                startActivity(i);
                break;
        }
    }

}
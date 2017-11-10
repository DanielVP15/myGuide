package com.example.dvpires.guideapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dvpires.guideapp.camera.CameraActivity;
import com.example.dvpires.guideapp.list.CustomCheckListAdapter;
import com.example.dvpires.guideapp.list.ShowActivity;
import com.example.dvpires.guideapp.save_data.SharedPreferenceActivity;
import com.example.dvpires.guideapp.slide.SlideViewActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private List<String> solvedList = new ArrayList<String>();
    private ListView problemList;
    private TextView problemText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillList();
        init();
    }

    public void fillList() {
        solvedList.add("Slide example");
        solvedList.add("Camera example");
        solvedList.add("SharedPreference example");
        solvedList.add("Notification example");
        solvedList.add("Still in progess");

    }

    public void init() {
        problemText = (TextView) findViewById(R.id.problem_text);
        problemList = (ListView) findViewById(R.id.problem_list);
        problemList.setOnItemClickListener(this);
        CustomCheckListAdapter customAdapter = new CustomCheckListAdapter(
                getApplicationContext(), solvedList);
        problemList.setAdapter((customAdapter));
    }

    //Notification Example
    public void notification() {
        // The id of the channel.
        String id = "my_channel_01";
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, ShowActivity.class);
        resultIntent.putExtra("dados", "It's Working!!!");
        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your app to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(ShowActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // mNotificationId is a unique integer your app uses to identify the
        // notification. For example, to cancel the notification, you can pass its ID
        // number to NotificationManager.cancel().
        int mNotificationId = 2;
        mNotificationManager.notify(mNotificationId, mBuilder.build());
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.problem_list:
                String problem = (String) adapterView.getItemAtPosition(i);
                switch (problem) {
                    case "Slide example":
                        Intent it2 = new Intent(this, SlideViewActivity.class);
                        startActivity(it2);
                        break;
                    case "SharedPreference example":
                        Intent it3 = new Intent(this, SharedPreferenceActivity.class);
                        startActivity(it3);
                        break;
                    case "Camera example":
                        Intent it4 = new Intent(this, CameraActivity.class);
                        startActivity(it4);
                        break;
                    case "Notification example":
                        notification();
                        break;
                    default:
                        Intent it = new Intent(this, ShowActivity.class);
                        it.putExtra("dados", problem);
                        startActivity(it);
                }
        }

    }

}
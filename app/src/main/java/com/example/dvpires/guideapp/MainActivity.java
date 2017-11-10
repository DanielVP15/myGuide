package com.example.dvpires.guideapp;

import android.content.Intent;
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
                    default:
                        Intent it = new Intent(this, ShowActivity.class);
                        it.putExtra("dados", problem);
                        startActivity(it);
                }
        }

    }
}
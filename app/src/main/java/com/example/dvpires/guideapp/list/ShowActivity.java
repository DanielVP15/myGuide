package com.example.dvpires.guideapp.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dvpires.guideapp.R;

public class ShowActivity extends AppCompatActivity {

    private String name;
    private TextView mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        name = getIntent().getExtras().getString("dados");
        mName = (TextView) findViewById(R.id.problem_text);
        mName.setText(name);
    }
}

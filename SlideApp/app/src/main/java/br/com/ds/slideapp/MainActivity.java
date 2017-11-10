package br.com.ds.slideapp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private HashMap<Integer, Documents> details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Internet internet = new Internet();
        Logo logo = new Logo();
        Dog dog = new Dog();

        details = new HashMap<>();
        details.put(internet.image(), internet);
        details.put(logo.image(), logo);
        details.put(dog.image(), dog);

        init();
    }

    private void init() {

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, details);
        viewPager.setAdapter(viewPagerAdapter);
    }
}

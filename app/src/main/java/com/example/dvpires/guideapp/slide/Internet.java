package com.example.dvpires.guideapp.slide;

import com.example.dvpires.guideapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dvpires on 23/10/2017.
 */

public class Internet extends Documents {

    @Override
    public List<String> campos() {
        List<String> ret = new ArrayList<>();
        ret.add("internet");
        ret.add("3.2");

        return ret;
    }

    @Override
    public Integer image() {
        return R.drawable.slide1;
    }
}


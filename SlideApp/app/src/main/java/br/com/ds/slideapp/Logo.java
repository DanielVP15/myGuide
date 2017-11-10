package br.com.ds.slideapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rbalencar on 18/10/2017.
 */

public class Logo extends Documents {

    private Integer image;

    @Override
    public List<String> campos() {
        List<String> ret = new ArrayList<>();
        return ret;
    }

    @Override
    public Integer image() {
        return R.drawable.slide2;
    }
}

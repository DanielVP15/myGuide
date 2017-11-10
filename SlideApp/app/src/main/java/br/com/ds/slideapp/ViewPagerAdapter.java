package br.com.ds.slideapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rbalencar on 18/10/2017.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private HashMap<Integer, Documents> details;
    private ArrayList<Documents> docs;

    public ViewPagerAdapter(Context mContext, HashMap<Integer, Documents> details) {
        this.mContext = mContext;
        this.details = details;

        docs = new ArrayList<>();

        for (Map.Entry<Integer, Documents> entry : details.entrySet()) {
            docs.add(entry.getValue());
        }
    }

    @Override
    public int getCount() {
        return docs.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(docs.get(position).image());

        TextView textView = (TextView) view.findViewById(R.id.txtView);
        TextView textView2 = (TextView) view.findViewById(R.id.txtView2);

        List<String> campos = docs.get(position).campos();

        if (campos != null && campos.size() > 0) {

            for (String campo : campos) {
                if (TextUtils.isEmpty(textView.getText().toString())) {
                    textView.setText(campo);
                } else {
                   textView2.setText(campo);
                }
            }
        }


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}

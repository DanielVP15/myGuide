package com.example.dvpires.guideapp.list;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dvpires.guideapp.R;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by dvpires on 04/10/2017.
 */

public class CustomCheckListAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mArray;

    public CustomCheckListAdapter(Context mContext, List mArray) {
        this.mContext = mContext;
        this.mArray = mArray;
    }

    @Override
    public int getCount() {
        return mArray.size();
    }

    @Override
    public Object getItem(int i) {
        return mArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                ContextWrapper.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_layout_checklist, null);

        TextView textView_name = (TextView) view.findViewById(R.id.documents_text);

        String problem = mArray.get(i);

        textView_name.setText(problem);

        return view;
    }
}

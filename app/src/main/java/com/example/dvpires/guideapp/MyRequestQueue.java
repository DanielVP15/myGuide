package com.example.dvpires.guideapp;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

/**
 * Created by dvpires on 04/12/2017.
 */

public class MyRequestQueue {
    private static MyRequestQueue mInstance;
    private static Context mCtx;
    private RequestQueue mMyRequestQueue;

    private MyRequestQueue(Context context) {
        mCtx = context;
        mMyRequestQueue = getRequestQueue();
    }

    public static synchronized MyRequestQueue getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mMyRequestQueue == null) {
            Cache cache = new DiskBasedCache(mCtx.getCacheDir(), 10 * 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            mMyRequestQueue = new RequestQueue(cache, network);
            // Don't forget to start the volley request queue
            mMyRequestQueue.start();
        }
        return mMyRequestQueue;
    }
}

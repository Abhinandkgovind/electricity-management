package com.example.ksebcomplaintmanagementsystem;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class custom_view_notification extends BaseAdapter {
    private final Context context;
    String[] t,n,d;


    public custom_view_notification(Context applicationContext, String[] t,String[] n, String[] d) {
        this.context = applicationContext;
        this.t = t;
        this.n = n;
        this.d = d;

    }

    @Override
    public int getCount() {
        return d.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.activity_custom_view_notification, null);//same class name

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.t1);
        TextView tv2 = (TextView) gridView.findViewById(R.id.d);
        TextView tv3 = (TextView) gridView.findViewById(R.id.n);


        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.RED);//color setting
        tv3.setTextColor(Color.BLACK);


        tv1.setText(t[i]);
        tv2.setText(d[i]);
        tv3.setText(n[i]);
//
        return gridView;

    }
}



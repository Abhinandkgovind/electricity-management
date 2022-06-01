package com.example.ksebcomplaintmanagementsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class custom_view_connection extends BaseAdapter {

    private final Context context;
    String [] b,ct,s;

    public custom_view_connection(Context applicationContext,  String[] b, String[] ct, String[] s) {
        this.context = applicationContext;
        this.b = b;
        this.ct = ct;
        this.s = s;

    }
    @Override
    public int getCount() {
        return ct.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_connection, null);//same class name

        } else {
            gridView = (View) view;

        }
        ImageView imageView = (ImageView) gridView.findViewById(R.id.imageView7);

        TextView tv1 = (TextView) gridView.findViewById(R.id.textView46);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView50);


        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);


        tv1.setText(ct[i]);
        tv2.setText(s[i]);
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000" + b[i];
        Picasso.with(context).load(url).transform(new CircleTransform()).into(imageView);//circle

//
        return gridView;
    }
}

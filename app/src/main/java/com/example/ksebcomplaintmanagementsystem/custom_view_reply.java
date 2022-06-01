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

public class custom_view_reply extends BaseAdapter {
    private final Context context;
    String[] com, cdate, report, rdate,pic;

    public custom_view_reply(Context applicationContext,  String[] com, String[] cdate, String[] report, String[] rdate, String[]pic) {
        this.context = applicationContext;
        this.com = com;
        this.cdate = cdate;
        this.report = report;
        this.rdate = rdate;
        this.pic = pic;
    }



    @Override
    public int getCount() {
        return cdate.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_reply, null);//same class name

        } else {
            gridView = (View) view;

        }
        ImageView imageView = (ImageView) gridView.findViewById(R.id.imageView4);

        TextView tv1 = (TextView) gridView.findViewById(R.id.textView34);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView36);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView38);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView40);


        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);


        tv1.setText(com[i]);
        tv2.setText(cdate[i]);
        tv3.setText(report[i]);
        tv4.setText(rdate[i]);
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000" + pic[i];
        Picasso.with(context).load(url).transform(new CircleTransform()).into(imageView);//circle

//
        return gridView;
    }
    }

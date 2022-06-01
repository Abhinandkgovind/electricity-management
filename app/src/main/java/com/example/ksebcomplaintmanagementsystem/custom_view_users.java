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

public  class custom_view_users extends BaseAdapter {

    private final Context context;
    String []uid,ph,n,c,p,po,pi,co,e;
    public custom_view_users(Context applicationContext, String []ph,String []n,String []c,String []p,String []po,String []pi,String []co,String []e) {
        this.context = applicationContext;
        this.ph = ph;
        this.n = n;
        this.c = c;
        this.p = p;
        this.po = po;
        this.pi = pi;
        this.co = co;
        this.e = e;

    }

    @Override
    public int getCount() {
        return co.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_users, null);//same class name

        } else {
            gridView = (View) view;

        }
        ImageView imageView = (ImageView) gridView.findViewById(R.id.imageView3);

        TextView tv1 = (TextView) gridView.findViewById(R.id.textView19);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView21);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView23);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView25);
        TextView tv5 = (TextView) gridView.findViewById(R.id.textView27);
        TextView tv6 = (TextView) gridView.findViewById(R.id.textView29);
        TextView tv7 = (TextView) gridView.findViewById(R.id.textView31);


        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);
        tv6.setTextColor(Color.BLACK);
        tv7.setTextColor(Color.BLACK);


        tv1.setText(n[i]);
        tv2.setText(c[i]);
        tv3.setText(p[i]);
        tv4.setText(po[i]);
        tv5.setText(pi[i]);
        tv6.setText(co[i]);
        tv7.setText(e[i]);
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000" + ph[i];
        Picasso.with(context).load(url).transform(new CircleTransform()).into(imageView);//circle

//
        return gridView;

    }
}

package com.example.ksebcomplaintmanagementsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public  class custom_view_history extends BaseAdapter {
    private final Context context;
    String [] date,reading,units,amount;


    public custom_view_history(Context applicationContext, String[] date, String[] reading, String[] units, String[] amount) {
        this.context = applicationContext;
        this.date = date;
        this.reading = reading;
        this.units = units;
        this.amount = amount;

    }

    @Override
    public int getCount() {
        return date.length;
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
            gridView = inflator.inflate(R.layout.activity_custom_view_history, null);//same class name

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView3);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView5);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView7);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView45);


        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);


        tv1.setText(date[i]);
        tv2.setText(reading[i]);
        tv3.setText(units[i]);
        tv4.setText(amount[i]);

//
        return gridView;
    }
}

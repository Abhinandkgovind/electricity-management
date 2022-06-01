package com.example.ksebcomplaintmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public  class custom_view_complaints extends BaseAdapter {
    private final Context context;
    String [] cid,un,c,cd,p,l,r,d;


    public custom_view_complaints(Context applicationContext, String[] un, String[] c, String[] cd, String[] p, String[] l, String[] r, String[] rd, String[] cid) {
        this.context = applicationContext;
        this.un = un;
        this.c = c;
        this.cd = cd;
        this.p = p;
        this.l=l;
        this.r=r;
        this.d=rd;
        this.cid=cid;

    }

    @Override
    public int getCount() {
        return c.length;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (view == null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.activity_custom_view_complaints, null);//same class name

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.textView3);
        TextView tv2 = (TextView) gridView.findViewById(R.id.textView5);
        TextView tv3 = (TextView) gridView.findViewById(R.id.textView7);
        TextView tv4 = (TextView) gridView.findViewById(R.id.textView67);
        TextView tv5 = (TextView) gridView.findViewById(R.id.textView69);
        TextView tv6 = (TextView) gridView.findViewById(R.id.textView68);
        TextView tv7 = (TextView) gridView.findViewById(R.id.textView70);
        ImageView imageView = (ImageView) gridView.findViewById(R.id.imageView2);
        ImageView imageView1 = (ImageView) gridView.findViewById(R.id.imageView10);
        Button bk = (Button) gridView.findViewById(R.id.button9);
        imageView1.setTag(i);
        bk.setTag(i);

        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ik=(int)view.getTag();
                Toast.makeText(context, ""+cid[ik], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,send_reply.class);
                intent.putExtra("cid",cid[ik]);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        int ik=(int)view.getTag();
        String url = "http://maps.google.com/maps?daddr="+l[ik];
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
});

        tv1.setTextColor(Color.RED);//color setting
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);


        tv1.setText(un[i]);
        tv2.setText(cd[i]);
        tv3.setText(c[i]);
        tv4.setVisibility(View.INVISIBLE);
        tv5.setVisibility(View.INVISIBLE);
        tv6.setVisibility(View.INVISIBLE);
        tv7.setVisibility(View.INVISIBLE);
        bk.setVisibility(View.INVISIBLE);
        if(r[i].equalsIgnoreCase("pending")) {
            bk.setVisibility(View.VISIBLE);
        }
        else {
            tv4.setVisibility(View.VISIBLE);
            tv5.setVisibility(View.VISIBLE);
            tv6.setVisibility(View.VISIBLE);
            tv7.setVisibility(View.VISIBLE);
            tv4.setText(r[i]);
            tv5.setText(d[i]);
        }
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000" + p[i];
        Picasso.with(context).load(url).transform(new CircleTransform()).into(imageView);//circle

//
        return gridView;
    }
}

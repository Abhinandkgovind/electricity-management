package com.example.ksebcomplaintmanagementsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class u_view_complaint extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView li;
        String[] cid, com, cdate, report, rdate,pic;
    String url,ip;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_view_complaint);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        li=findViewById(R.id.lco);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent i=new Intent(getApplicationContext(),send_complaint.class);
                startActivity(i);

            }
        });


        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url=sh.getString("url","")+"view_reply";


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js = jsonObj.getJSONArray("data");//from python
                                cid = new String[js.length()];

                                com = new String[js.length()];
                                cdate = new String[js.length()];
                                report = new String[js.length()];
                                rdate = new String[js.length()];
                                pic = new String[js.length()];


                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    cid[i] = u.getString("complaint_id");//dbcolumn name in double quotes
                                    com[i] = u.getString("complaint");//dbcolumn name in double quotes
                                    cdate[i] = u.getString("c_date");
                                    report[i] = u.getString("report");
                                    rdate[i] = u.getString("r_date");
                                    pic[i] = u.getString("photo");




                                }
                                li.setAdapter(new custom_view_reply(getApplicationContext(), com, cdate, report, rdate,pic));//custom_view_service.xml and li is the listview object


                            } else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {

            //                value Passing android to python
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
//                params.put("id", sh.getString("lid",""));//passing to python
                params.put("id", sh.getString("lid",""));//passing to python
                return params;
            }
        };


        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);

        li.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        final String c=cid[i].toString();
        Toast.makeText(this, "k"+c, Toast.LENGTH_SHORT).show();


        AlertDialog.Builder builder = new AlertDialog.Builder(u_view_complaint.this);
        builder.setTitle("options");
        builder.setItems(new CharSequence[]
                        {"pic","Cancel"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:

                            {
                                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor ed=sh.edit();
                                ed.putString("cid", c);
                                ed.commit();
                                Intent i =new Intent(getApplicationContext(),update_pic.class);
                                startActivity(i);
//                                requestQueue.add(postRequest);
                            }


                            break;


                            case 1:

                                break;


                        }
                    }
                });
        builder.create().show();

    }
}

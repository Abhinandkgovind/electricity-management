package com.example.ksebcomplaintmanagementsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_history extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView li;
    String [] bid,date,reading,units,amount;
    String url,ip,url1;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        li=findViewById(R.id.lco);


        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = sh.getString("url", "");
        url = ip + "and_view_history";

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
                                bid = new String[js.length()];
                                date = new String[js.length()];
                                reading = new String[js.length()];
                                units = new String[js.length()];
                                amount = new String[js.length()];


                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    bid[i] = u.getString("bill_id");//dbcolumn name in double quotes
                                    date[i] = u.getString("b_date");//dbcolumn name in double quotes
                                    reading[i] = u.getString("current_reading");//dbcolumn name in double quotes
                                    units[i] = u.getString("unit_consumed");
                                    amount[i] = u.getString("amount");


                                }
                                li.setAdapter(new custom_view_history(getApplicationContext(),  date, reading, units, amount));//custom_view_service.xml and li is the listview object


                            } else {
                                Toast.makeText(getApplicationContext(), "No Bill history", Toast.LENGTH_LONG).show();
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
                params.put("lid", sh.getString("lid", ""));//passing to python
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
        final String b=bid[i].toString();
        String am=amount[i].toString();
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String aam=sh.getString("am",am);
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("aa",aam);
        ed.commit();


        Toast.makeText(this, ""+sh.getString("aa",""), Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(view_history.this);
        builder.setTitle("options");
        builder.setItems(new CharSequence[]
                        {"Payment","Cancel"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch (which) {
                            case 0:

                            {
//                                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                                SharedPreferences.Editor ed=sh.edit();
//                                ed.putString("uid", u);
//                                ed.commit();
//                                Intent i =new Intent(getApplicationContext(),Upipay.class);
//                                i.putExtra("am",amount);
//                                startActivity(i);
//                                requestQueue.add(postRequest);

                                sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                ip = sh.getString("url", "");
                                url1 = ip + "payment";
//                                cid=getIntent().getStringExtra("cid");

                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest postRequest = new StringRequest(Request.Method.POST, url1,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                                try {
                                                    JSONObject jsonObj = new JSONObject(response);
                                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                                        Toast.makeText(view_history.this, "Payment success", Toast.LENGTH_SHORT).show();
                                                        Intent i =new Intent(getApplicationContext(),Upipay.class);
                                                        startActivity(i);
                                                    }if (jsonObj.getString("status").equalsIgnoreCase("Already paid")) {
                                                        Toast.makeText(view_history.this, "Already paid", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(getApplicationContext(), view_history.class);
                                                        startActivity(i);

                                                    }else {
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

                                        params.put("bid", b);//passing to python
//                                        params.put("c", cid);//passing to python
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


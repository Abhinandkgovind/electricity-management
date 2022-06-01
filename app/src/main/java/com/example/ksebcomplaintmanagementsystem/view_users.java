package com.example.ksebcomplaintmanagementsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_users extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView li;
    String []uid,ph,n,c,p,po,pi,co,e;
    String url,ip;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);
        li=findViewById(R.id.lu);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = sh.getString("url", "");
        url = ip + "and_view_users";

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
                                uid = new String[js.length()];
                                ph = new String[js.length()];
                                n = new String[js.length()];
                                c = new String[js.length()];
                                p = new String[js.length()];
                                po = new String[js.length()];
                                pi = new String[js.length()];
                                co = new String[js.length()];
                                e = new String[js.length()];


                                for (int i = 0; i < js.length(); i++) {
                                    JSONObject u = js.getJSONObject(i);
                                    uid[i] = u.getString("user_id");//dbcolumn name in double quotes
                                    ph[i] = u.getString("u_photo");//dbcolumn name in double quotes
                                    n[i] = u.getString("u_name");
                                    c[i] = u.getString("consumer_no");
                                    p[i] = u.getString("u_place");
                                    po[i] = u.getString("u_post");
                                    pi[i] = u.getString("u_pin");
                                    co[i] = u.getString("u_contact");
                                    e[i] = u.getString("u_email");


                                }
                                li.setAdapter(new custom_view_users(getApplicationContext(),  ph, n,c,p,po,pi,co,e));//custom_view_service.xml and li is the listview object


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
                params.put("id", sh.getString("lid", ""));//passing to python
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
        final String u=uid[i].toString();
//        Toast.makeText(this, ""+u, Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(view_users.this);
        builder.setTitle("options");
        builder.setItems(new CharSequence[]
                        {"Meter Reading","Cancel"},
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
                                Intent i =new Intent(getApplicationContext(),w_meter_reading.class);
                                i.putExtra("uid",u);
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

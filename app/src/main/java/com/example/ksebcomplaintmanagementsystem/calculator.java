package com.example.ksebcomplaintmanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class calculator extends AppCompatActivity {

    EditText e1;
    Button b1;
    SharedPreferences sh;
    String url;

    TableLayout tl, tl2;
    TextView tv_units, tv_amount, tv_days, tv_avg_usage;
    TextView tv_nxt_read, tv_nxt_amt, tv_nxt_price, tvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        e1=findViewById(R.id.editText2);
        b1=findViewById(R.id.button2);
        tl=findViewById(R.id.tl);
        tl2=findViewById(R.id.tl2);
        tv_units=findViewById(R.id.textView56);
        tv_amount=findViewById(R.id.textView57);
        tv_days=findViewById(R.id.textView58);
        tv_avg_usage=findViewById(R.id.textView59);

        tv_nxt_read=findViewById(R.id.textView63);
        tv_nxt_amt=findViewById(R.id.textView64);
        tv_nxt_price=findViewById(R.id.textView65);
        tvv=findViewById(R.id.textView66);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String reading=e1.getText().toString();

                int flag=0;
                if (reading.equalsIgnoreCase("")){
                    e1.setError("Current reading required");
                    flag++;
                }
                if (flag==0){



                url=sh.getString("url","")+"calculate";


                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        String days = jsonObj.getString("days");
                                        String units_consumed = jsonObj.getString("units_consumed");
                                        String amt = jsonObj.getString("amt");
                                        String avg_use = jsonObj.getString("avg_use");

                                        String next_month_amt = jsonObj.getString("next_month_amt");
                                        String next_month_bill = jsonObj.getString("next_month_bill");
                                        String next_month_read = jsonObj.getString("next_month_read");

                                        tv_days.setTextColor(Color.BLACK);
                                        tv_units.setTextColor(Color.BLACK);
                                        tv_amount.setTextColor(Color.BLACK);
                                        tv_avg_usage.setTextColor(Color.BLACK);

                                        tv_nxt_amt.setTextColor(Color.BLACK);
                                        tv_nxt_read.setTextColor(Color.BLACK);
                                        tv_nxt_price.setTextColor(Color.BLACK);

                                        tv_days.setText(days);
                                        tv_units.setText(units_consumed);
                                        tv_amount.setText(amt);
                                        tv_avg_usage.setText(avg_use);

                                        tv_nxt_read.setText(next_month_read);
                                        tv_nxt_price.setText(next_month_bill);
                                        tv_nxt_amt.setText(next_month_amt);

                                        tl.setVisibility(View.VISIBLE);
                                        tl2.setVisibility(View.VISIBLE);
                                        tvv.setVisibility(View.VISIBLE);



                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                                    }

                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error" + url.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getApplicationContext(), "eror,,,,,", Toast.LENGTH_SHORT).show();
                            }

                        }
                ) {

                    //                value Passing android to python
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("reading", reading);//passing to python
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

                }
            }


        });
    }
}

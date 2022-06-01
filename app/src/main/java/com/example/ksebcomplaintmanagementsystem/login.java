package com.example.ksebcomplaintmanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class login extends AppCompatActivity {

    EditText e1,e2;
    Button b1;
    TextView t1;
    SharedPreferences sh;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1=findViewById(R.id.editText2);
        e2=findViewById(R.id.editText3);
        b1=findViewById(R.id.button2);
        t1=findViewById(R.id.textView71);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Registration.class);
                startActivity(i);
            }
        });
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String u=e1.getText().toString();
                final String p=e2.getText().toString();

                int flag=0;
                if (u.equalsIgnoreCase("")){
                    e1.setError("enter username");
                    flag++;
                }
                if (p.equalsIgnoreCase("")){
                    e2.setError("enter password");
                    flag++;
                }
                if (flag==0){



                url=sh.getString("url","")+"and_login";
                Toast.makeText(login.this, ""+url, Toast.LENGTH_SHORT).show();


                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        String id = jsonObj.getString("lid");
                                        String typ = jsonObj.getString("type");
                                        String ue = jsonObj.getString("ue");
                                        String up = jsonObj.getString("up");
                                        String un = jsonObj.getString("un");


                                        Toast.makeText(login.this, "  Successfully", Toast.LENGTH_SHORT).show();
                                        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor ed1 = sh.edit();
                                        ed1.putString("lid", id);
                                        ed1.putString("ue", ue);
                                        ed1.putString("un", un);
                                        ed1.putString("up", up);
                                        ed1.commit();


                                        {
                                            if (typ.equalsIgnoreCase("user")) {
//                                                Intent i = new Intent(getApplicationContext(), Location_Service.class);
//                                                startService(i);

                                                Toast.makeText(login.this, "  Successfully", Toast.LENGTH_SHORT).show();

                                                Intent j = new Intent(getApplicationContext(), user_home.class);
                                                startActivity(j);

                                            } else if (typ.equalsIgnoreCase("worker")) {
//                                                Intent i = new Intent(getApplicationContext(), Location_Service.class);
//                                                startService(i);
                                                Toast.makeText(login.this, "  Successfully", Toast.LENGTH_SHORT).show();
//                                            SharedPreferences.Editor ed1 = sh.edit();
//                                            ed1.putString("lid", id);
//                                            ed1.commit();

                                                Intent k = new Intent(getApplicationContext(), worker_home.class);
                                                startActivity(k);
                                            } else {
                                                Toast.makeText(login.this, "  Invalid user", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Invalid user", Toast.LENGTH_LONG).show();
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

                        params.put("username", u);//passing to python
                        params.put("password", p);


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

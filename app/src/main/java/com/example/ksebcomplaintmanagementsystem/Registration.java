package com.example.ksebcomplaintmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    EditText n,p,po,pi,co,em,ps;
    Spinner s,d;
    Button b1;
    String[]si;
    String[] sn;
    String di;
    String ssid;
    String url,ip,url1;
    ImageView imageView;

    ProgressDialog pd;
    Bitmap bitmap=null;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        n=findViewById(R.id.editText4);
        p=findViewById(R.id.editText8);
        po=findViewById(R.id.editText9);
        pi=findViewById(R.id.editText10);
        co=findViewById(R.id.editText11);
        em=findViewById(R.id.editText12);
        ps=findViewById(R.id.editText13);
        s=findViewById(R.id.spinner);
        d=findViewById(R.id.spinner2);
        imageView=findViewById(R.id.imageView9);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = sh.getString("url", "");
        url1 = ip + "customer_reg";
        b1=findViewById(R.id.button7);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 100);
            }
        });
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = sh.getString("url", "");
        url = ip + "section_view";

        d.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                di=adapterView.getSelectedItem().toString();
                sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String ddi=sh.getString("di",di);
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("ddi",ddi);
                ed.commit();

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest( Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                // response
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//view service code8
                                        JSONArray js = jsonObj.getJSONArray("data");//from python
                                        si = new String[js.length()];
                                        sn = new String[js.length()];

                                        for (int i = 0; i < js.length(); i++) {
                                            JSONObject u = js.getJSONObject(i);
                                            si[i] = u.getString("section_id");//dbcolumn name
//                                    photo[i]=u.getString("photo");
                                            sn[i] = u.getString("section");


                                        }



                                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, sn);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        s.setAdapter(adapter);
                                    }



                                    else {
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
                        params.put("dii", sh.getString("ddi",""));//passing to python
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

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int flg=0;
                String name=n.getText().toString();
                String place=p.getText().toString();
                String post=po.getText().toString();
                String pin=pi.getText().toString();
                String contact=co.getText().toString();
                String email=em.getText().toString();
                String password=ps.getText().toString();
                if(name.equalsIgnoreCase("")){
                    n.setError("*");
                    flg++;
                }
                if(place.equalsIgnoreCase("")){
                    p.setError("*");
                    flg++;
                }
                if(post.equalsIgnoreCase("")){
                    po.setError("*");
                    flg++;
                }
                if(pin.equalsIgnoreCase("")){
                    pi.setError("*");
                    flg++;
                }
                if(contact.equalsIgnoreCase("")){
                    co.setError("*");
                    flg++;
                }
                if(email.equalsIgnoreCase("")){
                    em.setError("*");
                    flg++;
                }
                if(password.equalsIgnoreCase("")){
                    ps.setError("*");
                    flg++;
                }
if(bitmap==null){
    Toast.makeText(Registration.this, "Please choose Image", Toast.LENGTH_SHORT).show();
    flg++;
}
if(flg==0){
    uploadBitmap(name,place,post,pin,contact,email,password);
}

            }
        });

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                ssid = si[pos];

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //converting to bitarray
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }



    private void uploadBitmap(final String name, final String place, final String post, final String pin, final String contact, final String email, final String password) {

        pd = new ProgressDialog(Registration.this);
        pd.setMessage("Uploading....");
        pd.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url1,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            pd.dismiss();


                            JSONObject obj = new JSONObject(new String(response.data));

                            if (obj.getString("status").equals("ok")) {
                                Toast.makeText(getApplicationContext(), " success", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), login.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), " failed", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                params.put("name", name);//passing to python
                params.put("place", place);//passing to python
                params.put("post", post);//passing to python
                params.put("pin", pin);//passing to python
                params.put("contact", contact);//passing to python
                params.put("email", email);//passing to python
                params.put("password", password);//passing to python
                params.put("d", di);//passing to python
                params.put("sid", ssid);//passing to python
//                params.put("sid", String.valueOf(si));//passing to python


                return params;
            }


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }



}

package com.example.ksebcomplaintmanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sh;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1=findViewById(R.id.ii);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1.setText(sh.getString("ip",""));
        b1=findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipaddress=e1.getText().toString();

                int flag=0;
                if (ipaddress.equalsIgnoreCase("")){
                    e1.setError("enter ip");
                    flag++;
                }
                if (flag==0){



                String url = "http://"+ipaddress+":5000/";
                SharedPreferences.Editor e1=sh.edit();
                e1.putString("ip",ipaddress);
                e1.putString("url",url);
//                e1.apply();
                e1.commit();
                Toast.makeText(MainActivity.this, ""+url, Toast.LENGTH_SHORT).show();
//                Intent is=new Intent(getApplicationContext(),Locationservice.class);
//                startService(is);

                Intent ij=new Intent(getApplicationContext(),login.class);
                startActivity(ij);
                }

            }
        });


    }

}

package com.example.ksebcomplaintmanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

public class user_home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView t1,t2;
    ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        View hd=navigationView.getHeaderView(0);
        im=hd.findViewById(R.id.imageView);
        t1=hd.findViewById(R.id.t1);
        t2=hd.findViewById(R.id.textView);
        SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        t1.setText(sh.getString("un",""));
        t2.setText(sh.getString("ue",""));
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000" + sh.getString("up","");
        Picasso.with(getApplicationContext()).load(url).transform(new CircleTransform()).into(im);//circle
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

//            return true;
            SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor e=sh.edit();
            e.clear();
            e.commit();
            Intent i = new Intent(getApplicationContext(), login.class);
            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(i.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action

            Intent i=new Intent(getApplicationContext(),view_user_profile.class);
            startActivity(i);
        } else if (id == R.id.nav_gallery) {
            Intent i=new Intent(getApplicationContext(),new_connection.class);
            startActivity(i);
        } else if (id == R.id.nav_slideshow) {
            Intent i=new Intent(getApplicationContext(),u_view_complaint.class);
            startActivity(i);

        } else if (id == R.id.nav_tools) {
            Intent i=new Intent(getApplicationContext(),view_connection.class);
            startActivity(i);

        } else if (id == R.id.nav_hist) {
            Intent i=new Intent(getApplicationContext(),view_history.class);
            startActivity(i);

        } else if (id == R.id.nav_calc) {
            Intent i=new Intent(getApplicationContext(),calculator.class);
            startActivity(i);

        }  else if (id == R.id.nav_alert) {
            Intent i=new Intent(getApplicationContext(),view_alert.class);
            startActivity(i);

        } else if (id == R.id.nav_send) {
            SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor e=sh.edit();
            e.clear();
            e.commit();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(i.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

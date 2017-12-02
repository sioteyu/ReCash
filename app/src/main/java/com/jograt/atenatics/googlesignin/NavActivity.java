package com.jograt.atenatics.googlesignin;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.InputStream;

public class NavActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView name;
    private TextView email;
<<<<<<< HEAD
    private static final int LOCATION_REQUEST_CODE = 1;
=======
    private TextView coins;
>>>>>>> e62a3f09b0b61a1706ad9b6c616f1cb0318691d7
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private StorageReference storageReference;
    private DatabaseReference db;
    private UserBean bean;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("");

        db = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        new DownloadImageTask((ImageView) findViewById(R.id.imageView))
                .execute(getIntent().getExtras().getString("url"));
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            MapFragment fragment = new MapFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction  = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment, fragment).commit();
        } else if (id == R.id.nav_coins) {

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public UserBean getBean(){
        return bean;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.v("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage = (ImageView)findViewById(R.id.imageView);
            email = (TextView)findViewById(R.id.email);
            name = (TextView)findViewById(R.id.name);
            coins= (TextView)findViewById(R.id.coins);


            db.child(getIntent().getExtras().getString("id")).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()){
                        bean = new UserBean(dataSnapshot.child("user")  .getValue().toString(),
                                dataSnapshot.child("email").getValue().toString(),
                                Integer.parseInt(dataSnapshot.child("cash").getValue().toString()),
                                dataSnapshot.child("id").getValue().toString(),
                                getIntent().getExtras().getString("url"));

                    }else{
                        bean = new UserBean(getIntent().getExtras().getString("name"),
                                getIntent().getExtras().getString("email"),
                                0,
                                getIntent().getExtras().getString("id"),
                                getIntent().getExtras().getString("url"));
                        db.child(bean.getId()).setValue(bean);
                    }
                    name.setText(getIntent().getExtras().getString("name"));
                    email.setText(getIntent().getExtras().getString("email"));
                    coins.setText("ReCash Coins: " + bean.getCash());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            bmImage.setImageBitmap(result);

            if(hasLocationPermission()){
                MapFragment fragment = new MapFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction  = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment, fragment).commit();
            }else{
                requestLocationPermission();
            }


        }
    }

    private boolean hasLocationPermission(){
        int result = 0;

        String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};

        for(String permission: permissions){
            result = checkCallingOrSelfPermission(permission);

            if(result != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    private void requestLocationPermission(){
        String[] permissions = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions, LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    MapFragment fragment = new MapFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction transaction  = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragment, fragment).commit();
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                            Toast.makeText(this, "Location Access Denied! Current location couldn't be found.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

}

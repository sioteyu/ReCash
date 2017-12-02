/*
package com.jograt.atenatics.googlesignin;

import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng manila = new LatLng(14.5995124, 120.9842195);
        mMap.addMarker(new MarkerOptions().position(manila).title("1730 Paterno St., Manila City"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(manila));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker marker) {
                    //Toast.makeText(MapsActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                    builder.setTitle("Shop junks Junk Shop"); //Junk Shop name
                    builder.setMessage(marker.getTitle() + "\n\n" + "Plastic: Php5/kg");//address
                    builder.setCancelable(true);
                    builder.setPositiveButton("OK", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                return true;
            }



        });

        //Dynamic markers based from firebase
        /*/
/***please add "latitude" and "longitude" in firebase database :D*****
        */
/*
        Firebase locationRef = mRootRef.child("your reference");
        locationRef.addValueEventListener(new com.firebase.client.ValueEventListener() {

        @Override
        public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {


        for (DataSnapshot snapm: dataSnapshot.getChildren()) {

            Double latitude = snapm.child("latitude").getValue(Double.class);
            Double longitude = snapm.child("longitude").getValue(Double.class);
          }
        }
        @Override
        public void onCancelled(FirebaseError firebaseError) {
            throw firebaseError.toException();

        }
    });
         *//*

    }
}
*/

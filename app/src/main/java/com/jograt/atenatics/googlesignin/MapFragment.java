package com.jograt.atenatics.googlesignin;


import android.app.AlertDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button sell;
    private Button buy;
    private Button mail;
    private Button profile;
    Location location;
    private DatabaseReference db;
    private TransactionBean bean;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        final LocationManager locationManager = (LocationManager)
                getActivity().getSystemService(Context.LOCATION_SERVICE);

        db = FirebaseDatabase.getInstance().getReference("transaction");
        buy = (Button)view.findViewById(R.id.buy);
        sell = (Button)view.findViewById(R.id.sell);
        mail = (Button)view.findViewById(R.id.mail);
        profile = (Button)view.findViewById(R.id.profile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialoglayout = inflater.inflate(R.layout.profile_dialog, null);
                ((TextView)dialoglayout.findViewById(R.id.profile_email)).setText(getArguments().getString("email"));
                ((TextView)dialoglayout.findViewById(R.id.profile_name)).setText(getArguments().getString("name"));

                builder.setView(dialoglayout);
                builder.show();
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialoglayout = inflater.inflate(R.layout.dialog_layout2, null);
                builder.setView(dialoglayout);
                builder.show();
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                bean = new TransactionBean(location.getLatitude(), location.getLongitude(), getArguments().getString("user"), "20", "buy");
                db.child(db.push().getKey()).setValue(bean);
//                mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()))
//                        .title("Current Location"));
                Toast.makeText(getActivity().getApplicationContext(), "BUY", Toast.LENGTH_SHORT).show();
            }
        });

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                bean = new TransactionBean(location.getLatitude(), location.getLongitude(), getArguments().getString("user"), "20", "sell");
                db.child(db.push().getKey()).setValue(bean);
                Toast.makeText(getActivity().getApplicationContext(), "SELL", Toast.LENGTH_SHORT).show();
            }
        });

        //mapFragment.getMapAsync(this);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng manila = new LatLng(14.5995124, 120.9842195);


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMap.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()){
                    if (dsp.child("type").getValue().toString().equals("buy")){
                        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(dsp.child("latitude").getValue().toString()),
                                Double.parseDouble(dsp.child("longitude").getValue().toString())))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                    }else {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(dsp.child("latitude").getValue().toString()),
                                Double.parseDouble(dsp.child("longitude").getValue().toString())))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLng(manila));
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

         @Override
            public boolean onMarkerClick(Marker marker) {
                //Toast.makeText(MapsActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = (LayoutInflater) getActivity()
                     .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialoglayout = inflater.inflate(R.layout.dialog_layout, null);
                builder.setView(dialoglayout);
                builder.show();
                return true;
            }


        });
    }
}



//mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

//@Override
//public boolean onMarkerClick(Marker marker) {
//        //Toast.makeText(MapsActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setTitle("Shop junks Junk Shop"); //Junk Shop name
//        builder.setMessage(marker.getTitle() + "\n\n" + "Plastic: Php5/kg");//address
//        builder.setCancelable(true);
//        builder.setPositiveButton("OK", null);
//        AlertDialog dialog = builder.create();
//        dialog.show();
//        return true;
//        }
//
//
//        });
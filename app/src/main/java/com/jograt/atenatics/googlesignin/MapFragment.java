package com.jograt.atenatics.googlesignin;


import android.app.AlertDialog;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button sell;
    private Button buy;
    private DatabaseReference db;
    private double longitude;
    private double latitude;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        db = FirebaseDatabase.getInstance().getReference("transaction");
        buy = (Button)view.findViewById(R.id.buy);
        sell = (Button)view.findViewById(R.id.sell);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "BUY", Toast.LENGTH_SHORT).show();
            }
        });

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        LatLng manila1 = new LatLng(14.59879427, 120.9852916);
        LatLng manila2 = new LatLng(14.60128604, 120.98392904);
        LatLng quiapo = new LatLng(14.598772, 120.983796);



        mMap.addMarker(new MarkerOptions().position(manila).title("1730 Paterno St., Manila City"));
        mMap.addMarker(new MarkerOptions().position(quiapo).title("1730 Paterno St., Manila City"));
        mMap.addMarker(new MarkerOptions().position(manila1).title("1730 Paterno St., Manila City"));
        mMap.addMarker(new MarkerOptions().position(manila2).title("1730 Paterno St., Manila City"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(manila));
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

         @Override
            public boolean onMarkerClick(Marker marker) {
                //Toast.makeText(MapsActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Shop junks Junk Shop"); //Junk Shop name
                builder.setMessage(marker.getTitle() + "\n\n" + "Plastic: Php5/kg");//address
                builder.setCancelable(true);
                builder.setPositiveButton("OK", null);
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }


        });
    }
}
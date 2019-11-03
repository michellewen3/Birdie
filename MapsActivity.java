package com.example.maptest;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityEventSource;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ScrollView;


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

        //new
        Button button= (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "Yay", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MapsActivity.this,MapsActivity.class));
            }
        });

        Button button1= (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "Yay!!!!", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MapsActivity.this,MapsActivity.class));
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
            .add(
                    new LatLng(-35.016, 143.321),
                    new LatLng(-34.747, 145.592),
                    new LatLng(-34.364, 147.891),
                    new LatLng(-33.501, 150.217),
                    new LatLng(-32.306, 149.248),
                    new LatLng(-32.491, 147.309))
            .width(10)
            .color(Color .BLUE)
            .startCap(new RoundCap())
            .jointType(JointType .ROUND)
            .endCap(new RoundCap())
            );

        //


        //(polyline1);


        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        LatLng sydney1 = new LatLng(-35, 152);
        LatLng sydney2 = new LatLng(-35, 152);
        LatLng sydney3 = new LatLng(-35, 152);
        LatLng sydney4 = new LatLng(-35, 152);
        mMap.addMarker(new MarkerOptions().position(sydney).title("1"));
        mMap.addMarker(new MarkerOptions().position(sydney1).title("2"));
        mMap.addMarker(new MarkerOptions().position(sydney2).title("3"));
        mMap.addMarker(new MarkerOptions().position(sydney3).title("4"));
        mMap.addMarker(new MarkerOptions().position(sydney4).title("5"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */

    }

    /*private void stylePolyline(Polyline polyline) {
        polyline.setEndCap(new RoundCap());
        polyline.setColor(0x1E90FF);
        polyline.setWidth(100);   //number in pixels
        polyline.setJointType(JointType.ROUND);
    }*/




}

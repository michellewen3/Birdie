package com.example.safetrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

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

import com.google.android.gms.location.*;
import com.google.android.gms.location.LocationListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//new for google maps
import com.google.android.gms.maps.GoogleMap;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{
    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();

    private GoogleMap mMap;

    int index_count=0;
    int last_index=10;
    int ret_count=0;
    int prev_size;
    double longitude;
    double latit[];
    double longit[];
    double latitude;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE=101;
    Timer timer=new Timer();

    ArrayList<String> retlong=new ArrayList<>();
    ArrayList<String> retlat= new ArrayList<>();




    List<LatLng> points = new ArrayList<>(); // list of latlng
    int i = 0;
    int zoomFlag = 1;
    LatLng prev = new LatLng(0,0);

    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();

        Button button1= (Button) findViewById(R.id.Start);

        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "Started Recording Route", Toast.LENGTH_SHORT).show();
                zoomFlag = 0;


                timer.scheduleAtFixedRate(new TimerTask(){
                    @Override
                    public void run() {
                        fetchLocation();//Called each time when 1000 milliseconds (1 second) (the period parameter)
                        DatabaseReference mpersonref=mRootRef.child("Person");
                        DatabaseReference mdataref = mpersonref.child(Integer.toString(index_count));
                        DatabaseReference mXref = mdataref.child("X");
                        DatabaseReference mYref = mdataref.child("Y");
                        mXref.setValue(latitude);
                        mYref.setValue(longitude);
                        index_count++;
                    }
                },0,5000);

               // Toast.makeText(getApplicationContext(), "Tracking Started", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MapsActivity.this,MapsActivity.class));
            }
        });
        Button button3= (Button)findViewById(R.id.Stop);
        button3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                index_count = 0;
                Toast.makeText(getApplicationContext(), "Stopped Recording Route", Toast.LENGTH_SHORT).show();
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 13));
                timer.cancel();
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                //startActivity(new Intent(MapsActivity.this,MapsActivity.class));
            }
        });




      //  longit=new double[]{-35.016,-34.747,-34.364,-33.501,-32.306,-32.491};
        //latit=new double[]{ 143.321,145.592, 147.891,150.217,149.248,147.309};
     /*   Button btnUpld = (Button) findViewById(R.id.upload);
        btnUpld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mpersonref=mRootRef.child("Person");

                int size=longit.length;


                last_index=size;
                while(index_count<last_index) {
                    DatabaseReference mdataref = mpersonref.child(Integer.toString(index_count));
                    DatabaseReference mXref = mdataref.child("X");
                    DatabaseReference mYref = mdataref.child("Y");


                    mXref.setValue(longit[index_count]);
                    mYref.setValue(latit[index_count]);
                    index_count++;

                }

                index_count=0;


            }
        });*/

       /* Button btnDownld = (Button) findViewById(R.id.download);
        btnDownld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Download button works!",
                        Toast.LENGTH_SHORT);

                toast.show();


                final DatabaseReference mpersonref=mRootRef.child("Person");
                mpersonref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         long size=dataSnapshot.getChildrenCount();


                        Toast toast = Toast.makeText(getApplicationContext(),
                                Long.toString(size),
                                Toast.LENGTH_SHORT);

                        toast.show();
                        //int index=prev_size;
                        int index=0;
                       for(DataSnapshot child: dataSnapshot.getChildren())
                        { String xcord=(child.child("X")).getValue().toString();

                            retlong.add(index, xcord);
                            String ycord=(child.child("Y").getValue()).toString();

                            retlat.add(index, ycord);
                            index++;
                        }
                       index=0;
                        TextView testing=findViewById(R.id.test);



                        testing.setText(retlong.get(0));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Failed",
                                Toast.LENGTH_SHORT);

                        toast.show();
                    }

                });




            }
        });
*/

        Button button2= (Button)findViewById(R.id.Alert);
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getApplicationContext(), "Notified others. Call 911 for emergencies.", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(MapsActivity.this,MapsActivity.class));
            }
        });
    }

    private void fetchLocation(){
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), "Attempt no: " + i + " " + currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    latitude = currentLocation.getLatitude();
                    longitude = currentLocation.getLongitude();
                   /* DatabaseReference mpersonref=mRootRef.child("Person");
                    DatabaseReference mdataref = mpersonref.child(Integer.toString(index_count));
                    DatabaseReference mXref = mdataref.child("X");
                    DatabaseReference mYref = mdataref.child("Y");
                    mXref.setValue(longitude);
                    mYref.setValue(latitude);
                    index_count++;*/

                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(MapsActivity.this);
                    i++;
                }
            }
        });
    }
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng coor = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        points.add(coor);

        for (int i = 0; i < points.size() - 1; i++) {
            LatLng src = points.get(i);
            LatLng dest = points.get(i + 1);

            // mMap is the Map Object
            Polyline line = mMap.addPolyline(
                    new PolylineOptions().add(
                            //new LatLng(src.latitude, src.longitude),
                            new LatLng(src.latitude, src.longitude),
                            new LatLng(dest.latitude, dest.longitude)
                    ).width(15).color(Color.BLUE).startCap(new RoundCap()).jointType(JointType .ROUND).endCap(new RoundCap()).geodesic(true)
            );
        }
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        if(!prev.equals(coor))
            mMap.addMarker(new MarkerOptions().position(coor).title(currentTime));
        prev = coor;
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(coor));
        if(zoomFlag == 1){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(coor));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coor, 17));
        }



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }
}

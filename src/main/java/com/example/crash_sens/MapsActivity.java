package com.example.crash_sens;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.crash_sens.databinding.ActivityMapsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

        binding = ActivityMapsBinding.inflate ( getLayoutInflater () );
        setContentView ( binding.getRoot () );
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager ()
                .findFragmentById ( R.id.map );
        mapFragment.getMapAsync ( this );

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
      FirebaseDatabase database=FirebaseDatabase.getInstance ();
        DatabaseReference lat=database.getReference ("Lattitude");
        DatabaseReference lng=database.getReference ("Longitude");
        final String[] latt = new String[1];
        final String[] lngg = new String[1];
lat.addValueEventListener ( new ValueEventListener () {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
         latt[0] =snapshot.getValue (String.class);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
} );
lng.addValueEventListener ( new ValueEventListener () {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
         lngg[0]=snapshot.getValue (String.class);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
} );
        mMap = googleMap;
        float lat1=3.3f;
        float lng1=3.3f;
        // Add a marker in Sydney and move the camera
       lat1=Float.parseFloat ( latt[0] );
       lng1=Float.parseFloat ( lngg[0] );

            LatLng sydney = new LatLng(lat1,lng1 );
            mMap.addMarker ( new MarkerOptions ().position ( sydney ).title ( "Marker in Device Location" ) );
            mMap.moveCamera ( CameraUpdateFactory.newLatLng ( sydney ) );

    }
}
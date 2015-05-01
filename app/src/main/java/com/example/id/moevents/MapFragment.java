package com.example.id.moevents; // change to your own package

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * A fragment that launches other parts of the demo application.
 */
public class MapFragment extends Fragment {

    SupportMapFragment mMapView;
    private GoogleMap googleMap;
    private Geocoder geocoder;
    public static CalendarEvent event = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View v = inflater.inflate(R.layout.activity_map_fragment, container,
                false);

        geocoder = new Geocoder(getActivity());
        mMapView = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        googleMap = mMapView.getMap();
        // latitude and longitude
        // mostate univ 37.199354, -93.281112

        double upperRightLat = 37.267510;
        double upperRightLong = -93.169596;
        double lowerLeftLat = 37.139259;
        double lowerLeftLong = -93.418505;

        String loc = "missouri state university";
        if ( event != null ) {
            loc = event.getLocation().trim();
            Log.i("loc", loc);
        }

        try {
            double latitude = 37.267510;
            double longitude = -93.169596;

            for (int j = 0; j < 25; j++) {
                List<Address> address = geocoder.getFromLocationName(loc, 1, lowerLeftLat, lowerLeftLong, upperRightLat, upperRightLong);

                if (address == null || address.isEmpty()) {
                    Log.d("Error", "Latitude and longitude not found");
                    loc = loc.substring(0, loc.lastIndexOf(' '));
                } else {
                    Address destination = address.get(0);
                    latitude = destination.getLatitude();
                    longitude = destination.getLongitude();
                    break;
                }

                if (loc.equals("")) {
                    break;
                }
            }

            googleMap.setMyLocationEnabled(true);
            MarkerOptions dest = new MarkerOptions().position(
                    new LatLng(latitude, longitude)).title("Destination");

            // Changing marker icon
            dest.icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

            // adding marker
            googleMap.addMarker(dest);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latitude, longitude)).zoom(16).build();
            googleMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return v;
    }
}


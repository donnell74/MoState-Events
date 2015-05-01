package com.example.id.moevents; // change to your own package

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * A fragment that launches other parts of the demo application.
 */
public class MapFragment extends Fragment {

    SupportMapFragment mMapView;
    private GoogleMap googleMap;
    private Geocoder geocoder;
    public static CalendarEvent event = null;
    double latitude = 37.267510;
    double longitude = -93.169596;
    private TextView mTravelTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View v = inflater.inflate(R.layout.activity_map_fragment, container,
                false);

        mTravelTv = (TextView) getActivity().findViewById(R.id.TravelText);

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
        googleMap.setMyLocationEnabled(true);
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
            for (int j = 0; j < 25; j++) {
                List<Address> address = geocoder.getFromLocationName(loc, 1, lowerLeftLat, lowerLeftLong, upperRightLat, upperRightLong);

                if (address == null || address.isEmpty()) {
                    Log.d("Error", "Latitude and longitude not found");
                    int index = loc.lastIndexOf(' ');
                    if ( index == -1 ) {
                        break;
                    }

                    loc = loc.substring(0, index);
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

            googleMap.setOnMyLocationChangeListener(myLocationChangeListener);


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

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        DateFormat m_ISO8601Local = new SimpleDateFormat("yyyyMMdd'T'HHmmss");

        @Override
        public void onMyLocationChange(Location location) {
            if (location != null) {
                float[] resultArray = new float[99];
                Location.distanceBetween(location.getLatitude(), location.getLongitude(), latitude, longitude, resultArray);
                try {
                    long secToEventStart = m_ISO8601Local.parse(event.getStart()).getTime();
                    long secToEventEnd = m_ISO8601Local.parse(event.getEnd()).getTime();
                    long currentTime = (long) (System.currentTimeMillis() + resultArray[0]*1000);
                    int minutes = (int) (resultArray[0]/60);
                    int seconds = (int) (resultArray[0]%60);
                    String timeToEvent = String.valueOf(minutes) + ":" + String.valueOf(seconds);

                    if ( currentTime < secToEventStart ) {
                        mTravelTv.setText(String.valueOf(timeToEvent));
                        mTravelTv.setBackgroundColor(getResources().getColor(R.color.green));
                    } else if ( currentTime < secToEventEnd ) {
                        mTravelTv.setText(String.valueOf(timeToEvent));
                        mTravelTv.setBackgroundColor(getResources().getColor(R.color.yellow));
                    } else {
                        mTravelTv.setText(String.valueOf(timeToEvent));
                        mTravelTv.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    };

}


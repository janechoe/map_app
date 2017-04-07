package com.example.janechoe.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import com.google.maps.android.data.Feature;
import com.google.maps.android.data.kml.KmlContainer;
import com.google.maps.android.data.kml.KmlLayer;
import com.google.maps.android.data.kml.KmlPlacemark;
import com.google.maps.android.data.kml.KmlPolygon;
import com.google.maps.android.data.kml.KmlPoint;


import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.width;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private String[] menuOptions;
    private DrawerLayout myDrawerLayout;
    private ListView myList;
//    private Fragment menuFragment;
    private MapFragment mMapFragment;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (findViewById(R.id.content_frame)!=null){
            if (savedInstanceState!=null){
                return;
            }
            // to add fragment dynamically, create new instance- add to frame layout

//
//            setContentView(R.layout.activity_maps);
//            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.map);
//            mapFragment.getMapAsync(this);
//
//
            mMapFragment= MapFragment.newInstance();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            mMapFragment.getMapAsync(this);
            fragmentTransaction.add(R.id.content_frame, mMapFragment);
            fragmentTransaction.commit();


            //make menu from array indices
            menuOptions = getResources().getStringArray(R.array.menu_array);
            myDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
            myList= (ListView)findViewById(R.id.left_drawer);
            //adapter for list view
            myList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, menuOptions));
            //list's click listener
            myList.setOnItemClickListener(new DrawerItemClickListener());

            Button menuButton = (Button) findViewById(R.id.menuButton);
            menuButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    myDrawerLayout.openDrawer(Gravity.LEFT);
                }
            });
            myDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                @Override
                //called when drawer is completely closed
                public void onDrawerClosed(View view){
                    super.onDrawerClosed(view);
                    invalidateOptionsMenu(); //creates call to prepareoptionsmenu
                }
                //called when drawer is completely opened
                public void onDrawerOpened(View view){
                    super.onDrawerOpened(view);
                    invalidateOptionsMenu();
                }
            });


            }


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
//        retrieveFilefromURL();
        retrieveFileFromResource();

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //how to add kml file from url?

    }
//    private void retrieveFilefromURL(){
//            new DownloadKmlFile(getString(R.string.kml_google)).execute();
//    }
    private void retrieveFileFromResource() {
        try {
            KmlLayer kmlLayer = new KmlLayer(mMap, R.raw.buns, getApplicationContext());
            kmlLayer.addLayerToMap();
            moveCameraToKml(kmlLayer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }
//    private class DownloadKmlFile extends AsyncTask<String, Void, byte[]> {
//        private final String mUrl;
//
//        public DownloadKmlFile(String url) {
//            mUrl = url;
//        }
//
//        protected byte[] doInBackground(String... params) {
//            try {
//                InputStream is = new URL(mUrl).openStream();
//                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//                int nRead;
//                byte[] data = new byte[16384];
//                while ((nRead = is.read(data, 0, data.length)) != -1) {
//                    buffer.write(data, 0, nRead);
//                }
//                buffer.flush();
//                return buffer.toByteArray();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }

//        protected void onPostExecute(byte[] byteArr) {
//            try {
//                KmlLayer kmlLayer = new KmlLayer(mMap, new ByteArrayInputStream(byteArr),
//                        getApplicationContext());
//                kmlLayer.addLayerToMap();
//                kmlLayer.setOnFeatureClickListener(new KmlLayer.OnFeatureClickListener() {
//                    @Override
//                    public void onFeatureClick(Feature feature) {
//                        Toast.makeText(MapsActivity.this,
//                                "Feature clicked: " + feature.getId(),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//                moveCameraToKml(kmlLayer);
//            } catch (XmlPullParserException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    private void moveCameraToKml(KmlLayer kmlLayer) {
        //Retrieve the first container in the KML layer
        KmlContainer container = kmlLayer.getContainers().iterator().next();
        //Retrieve a nested container within the first container
        container = container.getContainers().iterator().next();
        //Retrieve the first placemark in the nested container
        KmlPlacemark placemark = container.getPlacemarks().iterator().next();
        //Retrieve a polygon object in a placemark

//        KmlPolygon polygon = (KmlPolygon) placemark.getGeometry();
//        //Create LatLngBounds of the outer coordinates of the polygon
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//
//        for (LatLng latLng : polygon.getOuterBoundaryCoordinates()) {
//            builder.include(latLng);
//        }

        // how to distinguish polygons from points
//        ArrayList <LatLng> coordinates= new ArrayList<LatLng>();

        KmlPoint point = (KmlPoint) placemark.getGeometry();
        LatLng coordinates= point.getGeometryObject();
//        coordinates.add(point.getGeometryObject());

//        int width= getResources().getDisplayMetrics().widthPixels;
//        int height=getResources().getDisplayMetrics().heightPixels;
//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(coordinates, width, height, 1));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));

        //Create LatLngBounds of the outer coordinates of the polygon

        //make an arraylist of coordinates of points???
//        int width = getResources().getDisplayMetrics().widthPixels;
//        int height = getResources().getDisplayMetrics().heightPixels;
//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), width, height, 1));
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position){
        switch(position) {
            case 0:
                //Highlight selected item
//                myList.setItemChecked(position, true);
//                myDrawerLayout.closeDrawer(myList);
                mMapFragment= MapFragment.newInstance();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.content_frame, mMapFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                //about - change fragment
//                FragmentTransaction ft = fm.beginTransaction();
                Fragment aboutFragment = new AboutFragment();
                FragmentTransaction aboutTransaction= getFragmentManager().beginTransaction();
                aboutTransaction.replace(R.id.content_frame, aboutFragment);
                aboutTransaction.commit();
                break;
            case 2:
                //contributors
                Fragment contributorsFragment = new ContributorsFragment();
                FragmentTransaction contributorsTransaction = getFragmentManager().beginTransaction();
                contributorsTransaction.replace(R.id.content_frame, contributorsFragment);
                contributorsTransaction.commit();
                break;
            case 3:
                //tours
                //Intent switchIntent = new Intent(this, AboutActivity.class);
                break;
            case 4:
                //resources
                //Intent switchIntent = new Intent(this, AboutActivity.class);
                break;
        }
        //Highlight selected item
        myList.setItemChecked(position, true);
        myDrawerLayout.closeDrawer(myList);
        //close drawer

    }

}



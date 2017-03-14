package com.example.janechoe.myapplication;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private String[] menuOptions;
    private DrawerLayout myDrawerLayout;
    private ListView myList;
    private Fragment menuFragment;
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
            mMapFragment= MapFragment.newInstance();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.content_frame, mMapFragment);
            fragmentTransaction.commit();

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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
//
//        menuOptions = getResources().getStringArray(R.array.menu_array);
//        myDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
//        myList= (ListView)findViewById(R.id.left_drawer);




//        myList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, menuOptions));
//        myList.setOnItemClickListener(new DrawerItemClickListener());


//MAKE MAP FRAGMENT




//
//    private class DrawerItemClickListener implements ListView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            selectItem(position);
//        }
//    }
//
//    private void selectItem(int position){
//        Fragment fragment = new MenuFragment();
//        Bundle args = new Bundle();
//        //shows activity based on position
//        args.putInt(MenuFragment.ARG_OPTION_NUMBER, position);
//
//
//    }


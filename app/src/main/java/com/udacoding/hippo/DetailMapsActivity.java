package com.udacoding.hippo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.udacoding.hippo.fragment.dummy.DataItem;
import com.udacoding.hippo.networks.NetworkConfig;
import com.udacoding.hippo.utils.DirectionMapsV2;
import com.udacoding.hippo.utils.GPSTracker;
import com.udacoding.hippo.view.detail.model.LegsItem;
import com.udacoding.hippo.view.detail.model.OverviewPolyline;
import com.udacoding.hippo.view.detail.model.ResultRoute;
import com.udacoding.hippo.view.detail.model.RoutesItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @BindView(R.id.itemDistance)
    TextView itemDistance;
    @BindView(R.id.itemTime)
    TextView itemTime;
    private GoogleMap mMap;
    private DataItem dataItem;
    private Double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_maps);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dataItem = (DataItem) getIntent().getSerializableExtra("data");
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


        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.getUiSettings().setAllGesturesEnabled(true);

        showGps();


        String lat = dataItem.getLat();
        String lon = dataItem.getLon();

        Double latx = Double.parseDouble(lat);
        Double lonx = Double.parseDouble(lon);


        // Add a marker in Sydney and move the camera
        LatLng place = new LatLng(latx, lonx);
        mMap.addMarker(new MarkerOptions().position(place).title(dataItem.getPlacename()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(place));

        //setting zoom
      //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place, 17));

        showRoute();
    }

    private void showRoute() {


        LatLngBounds.Builder latLngBounds = new LatLngBounds.Builder();
        latLngBounds.include(new LatLng(lat,lng));
        Double ln = Double.parseDouble(dataItem.getLat());
        Double lg = Double.parseDouble(dataItem.getLon());
        latLngBounds.include(new LatLng(ln,lg));



        //get config network
        NetworkConfig config = new NetworkConfig();

        String origin = lat.toString() + "," + lng.toString();
        String destination = dataItem.getLat() + "," + dataItem.getLon();

        config.getServiceMap().route(origin, destination,
                "AIzaSyBL-OY2TGIdkkz7x7PmIUq7eKg9R6vLZkQ")
                .enqueue(new Callback<ResultRoute>() {
                    @Override
                    public void onResponse(Call<ResultRoute> call, Response<ResultRoute> response) {

                        if (response.isSuccessful()) {

                            //ambil semua json dlu
                            ResultRoute allJson = response.body();
                            List<RoutesItem> dataRoute = allJson.getRoutes();
                            RoutesItem routesItem = dataRoute.get(0);

                            //get legs untuk jarak dan waktu
                           List<LegsItem> legs =  routesItem.getLegs();
                           String distance = legs.get(0).getDistance().getText();
                           String time = legs.get(0).getDuration().getText();

                           itemDistance.setText("Jarak : "+distance);
                           itemTime.setText("Waktu : " + time);

                            OverviewPolyline overviewPolyline = routesItem.getOverviewPolyline();
                            String points = overviewPolyline.getPoints();

                            DirectionMapsV2 directionMapsV2 = new DirectionMapsV2(DetailMapsActivity.this);
                            directionMapsV2.gambarRoute(mMap, points);


                        }


                    }

                    @Override
                    public void onFailure(Call<ResultRoute> call, Throwable t) {

                    }
                });


    }

    private void showGps() {

        GPSTracker gps = new GPSTracker(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            if (gps.canGetLocation()) {

                mMap.getUiSettings().setMyLocationButtonEnabled(true);

                mMap.setMyLocationEnabled(true);

                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                lat = gps.getLatitude();
                lng = gps.getLongitude();

                //create marker berdasarkan gps

                LatLng currentLocation = new LatLng(lat, lng);

//                    mMap.addMarker(new MarkerOptions().
//                            position(currentLocation)
//                            .title("posisiku")
//                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));


            } else {
                //on gps
                gps.showSettingGps();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]
                        {Manifest.permission.ACCESS_FINE_LOCATION}, 12);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 12) {

            showGps();
        }
    }

    @OnClick({R.id.itemDistance, R.id.itemTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.itemDistance:
                break;
            case R.id.itemTime:
                break;
        }
    }
}

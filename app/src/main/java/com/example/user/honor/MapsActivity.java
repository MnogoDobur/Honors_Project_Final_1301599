package com.example.user.honor;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.content.Intent;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.DetectedActivity;

import java.util.ArrayList;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,ResultCallback<Status>,
        LocationListener
       /* ,SensorEventListener*/ {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    boolean running;
    Location mLastLocation;
    Marker firstObjective;
    Marker secondObjective;
    Marker thirdObjective;
    SensorManager sensorManager;
    boolean objectivesAdded;
    private static final String TAG = "MapsActivity";
    private Circle mCircle;
    private Circle mCircle2;
    private Circle mCircle3;
    private int cameraIndex = 0;
    private TextView creditsTextView;
    private TextView highScoreTextView;
    private ListView mListView;
    private DetectedActivitiesAdapter mAdapter;
    private ArrayList<DetectedActivity> mDetectedActivities;
    private Button play;
    public int credits;
    LatLng latLng1;
    LatLng latLng2;
    LatLng latLng3;
    protected ActivityDetectionBroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        highScoreTextView = (TextView)findViewById(R.id.highScoreMapActivity);
        Button exploreMissions = (Button) findViewById(R.id.exploreMissions);
        play=(Button) findViewById(R.id.play);
        creditsTextView = (TextView) findViewById(R.id.creditsMapActivity);
        mListView = (ListView)findViewById(R.id.detected_activities_listview_final);

        mBroadcastReceiver = new ActivityDetectionBroadcastReceiver();

        if (savedInstanceState != null && savedInstanceState.containsKey(
                Constants.DETECTED_ACTIVITIES)) {
            mDetectedActivities = (ArrayList<DetectedActivity>) savedInstanceState.getSerializable(
                    Constants.DETECTED_ACTIVITIES);
        } else {
            mDetectedActivities = new ArrayList<DetectedActivity>();

            // Set the confidence level of each monitored activity to zero.
            for (int i = 0; i < Constants.MONITORED_ACTIVITIES.length; i++) {
                mDetectedActivities.add(new DetectedActivity(Constants.MONITORED_ACTIVITIES[i], 0));
            }
        }

        // Bind the adapter to the ListView responsible for display data for detected activities.
        mAdapter = new DetectedActivitiesAdapter(this, mDetectedActivities);
        mListView.setAdapter(mAdapter);






/*
switch statement which moves the camera focusing on each of the active missions
 */
        exploreMissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCircle!=null && mCircle2!=null && mCircle3!=null) {
                  switch (cameraIndex) {
                      case 0:
                          LatLng camlatLng = new LatLng(mCircle.getCenter().latitude, mCircle.getCenter().longitude);
                          mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(camlatLng, 17));cameraIndex++;break;
                      case 1:
                          LatLng camlatLng1 = new LatLng(mCircle2.getCenter().latitude, mCircle2.getCenter().longitude);
                          mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(camlatLng1, 17));cameraIndex++;break;
                      case 2:
                          LatLng camlatLng2 = new LatLng(mCircle3.getCenter().latitude, mCircle3.getCenter().longitude);
                          mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(camlatLng2, 17));cameraIndex = 0;break;
                  }
              }
              else{
                  Toast.makeText(getBaseContext(), "The app has failed to display objectives.", Toast.LENGTH_LONG).show();
              }

            }
        });
        final SharedPreferences accountInfo = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = accountInfo.edit();
        creditsTextView.setText(accountInfo.getInt("Credits",0)+ " credits left");
        highScoreTextView.setText("High Score: "+accountInfo.getInt("HighScore",0));

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                credits = accountInfo.getInt("Credits",0);
                if(credits>0){
                    Intent game = new Intent(MapsActivity.this,Game.class);
                    startActivity(game);
                }
                else{
                    Toast.makeText(getBaseContext(), "Game cant be launched! Not enough credits.To earn more credits complete activity missions on the map!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
/*
Remove location updates if Activity is no longer active
 */
    @Override
    public void onPause() {
        super.onPause();
        running=false;
        //TODO:stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }
    /*
    Method for setting the type of the map and initialization of the Google Play Services
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mGoogleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
                this, R.raw.style_json));


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.setIndoorEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }

    }
/*
Method for bulding the GoogleApiCliet (adding LocationServices.API)
 */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .build();
        mGoogleApiClient.connect();
    }
/*
Defining the speed of the location requsts as well as priority( both of which effect the battery life)
Using the FusedLocationApi to requst location updates
 */
    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(800);
        mLocationRequest.setFastestInterval(500);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(
                mGoogleApiClient,
                Constants.DETECTION_INTERVAL_IN_MILLISECONDS,
                getActivityDetectionPendingIntent()
        ).setResultCallback(this);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onLocationChanged: " + "ON CONNECTION SUSPENDED");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "onLocationChanged: " + "ON CONNECTION FAILED");
    }
/*
Mostly all of the maps activity logic is done in this method.
It starts with checking if the objectives are already loaded to the map. If they aren't it adds them randomly around the player(first location update)
After all locations have been added several variables are created to locate the distance between the player and the objective in meter and later on
I have several if statements displaying the closest objective on the map based on the most recent location update.
There is a markerOnClick listener wich checks if the player is within several meters of the objective(10meters) and if this is true then he can click
the marker resulting in completing the objective and earning some credits. If an objective is completed another one is randomly spawned within 1-2km of the player
 */
    @Override
    public void onLocationChanged(Location location) {
        this.mLastLocation = location;
        if (!objectivesAdded) {
            ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(
                    mGoogleApiClient,
                    Constants.DETECTION_INTERVAL_IN_MILLISECONDS,
                    getActivityDetectionPendingIntent()
            ).setResultCallback(this);
        }
        final Location tempLocation = location;
        if (!objectivesAdded) {
            final SharedPreferences accountInfo = PreferenceManager.getDefaultSharedPreferences(this);
            creditsTextView.setText(accountInfo.getInt("Credits",0)+ " credits left");
            highScoreTextView.setText("High Score: "+accountInfo.getInt("HighScore",0));
            if (accountInfo.getInt("Diff",0) == 1){//easy
                 latLng1 = new LatLng(location.getLatitude() + (Math.random() / 1000), location.getLongitude() - (Math.random() / 1000));
                 latLng2 = new LatLng(location.getLatitude() - (Math.random() / 1000), location.getLongitude() + (Math.random() / 1000));
                 latLng3 = new LatLng(location.getLatitude() + (Math.random() / 1000), location.getLongitude() - (Math.random() / 1000));
            }
            if(accountInfo.getInt("Diff",0) == 2) {//medium
                latLng1 = new LatLng(location.getLatitude() + (Math.random() / 100), location.getLongitude() - (Math.random() / 1000)-(Math.random()/1000));
                latLng2 = new LatLng(location.getLatitude() - (Math.random() / 100) + (Math.random() / 1000), location.getLongitude() + (Math.random() / 1000));
                latLng3 = new LatLng(location.getLatitude() - (Math.random() / 100), location.getLongitude() + (Math.random() / 1000) - (Math.random() / 1000));
            }
            if(accountInfo.getInt("Diff",0) == 3){//hard
                latLng1 = new LatLng(location.getLatitude() + (Math.random() / 100), location.getLongitude() - (Math.random() / 1000));
                latLng2 = new LatLng(location.getLatitude() - (Math.random() / 100), location.getLongitude() + (Math.random() / 1000));
                latLng3 = new LatLng(location.getLatitude() - (Math.random() / 100), location.getLongitude() + (Math.random() / 1000));
            }
            for (int i = 0; i<3; i++) {
            if (i == 0) {drawMarkerWithCircle(latLng1,0);}
            if (i == 1) {drawMarkerWithCircle(latLng2,1);}
            if (i == 2) {drawMarkerWithCircle(latLng3,2);}
        }
        objectivesAdded = true;
    }
        Location circleOneLocation = new Location(""); circleOneLocation.setLongitude(mCircle.getCenter().longitude); circleOneLocation.setLatitude(mCircle.getCenter().latitude);
        final float metersToOne = circleOneLocation.distanceTo(mLastLocation);

        Location circleTwoLocation = new Location(""); circleTwoLocation.setLongitude(mCircle2.getCenter().longitude); circleTwoLocation.setLatitude(mCircle2.getCenter().latitude);
        final float metersToTwo = circleTwoLocation.distanceTo(mLastLocation);

        Location circleThreeLocation = new Location(""); circleThreeLocation.setLongitude(mCircle3.getCenter().longitude); circleThreeLocation.setLatitude(mCircle3.getCenter().latitude);
        final float metersToThree = circleThreeLocation.distanceTo(mLastLocation);



        mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                final SharedPreferences accountInfo = PreferenceManager.getDefaultSharedPreferences(com.example.user.honor.MapsActivity.this);
                final SharedPreferences.Editor editor = accountInfo.edit();
                if (marker.equals(firstObjective) && metersToOne < 10) {
                    Toast.makeText(getBaseContext(), "Mission Completed.", Toast.LENGTH_LONG).show();firstObjective.remove();
                    //add another objective is this one is done
                    LatLng latLng = new LatLng(tempLocation.getLatitude() - (Math.random() / 100) + (Math.random() / 1000), tempLocation.getLongitude() + (Math.random() / 1000));
                    credits=accountInfo.getInt("Credits",0)+1;editor.putInt("Credits",credits);/*editor.putInt("Mis1Lon",(int)Math.ceil(latLng.longitude));*/  editor.apply();
                    drawMarkerWithCircle(latLng,0);
                }
                if (marker.equals(secondObjective) && metersToTwo < 10) {
                    Toast.makeText(getBaseContext(), "Mission Completed.", Toast.LENGTH_LONG).show();secondObjective.remove();
                    LatLng latLng = new LatLng(tempLocation.getLatitude() - (Math.random() / 100), tempLocation.getLongitude() + (Math.random() / 1000) - (Math.random() / 1000));
                    credits=accountInfo.getInt("Credits",0)+1;editor.putInt("Credits",credits);drawMarkerWithCircle(latLng,1);editor.apply();
                }
                if (marker.equals(thirdObjective) && metersToThree < 10) {
                    Toast.makeText(getBaseContext(), "Mission Completed.", Toast.LENGTH_LONG).show();thirdObjective.remove();
                    LatLng latLng = new LatLng(tempLocation.getLatitude() + (Math.random() / 100), tempLocation.getLongitude() - (Math.random() / 1000));
                    credits=accountInfo.getInt("Credits",0)+1;editor.putInt("Credits",credits);drawMarkerWithCircle(latLng,2);editor.apply();
                }
                return false;
            }
        });
        if(metersToOne<10 || metersToTwo<10 || metersToThree<10) {
            Toast.makeText(getBaseContext(), "Well done! Click to complete", Toast.LENGTH_LONG).show();
        }
        else {
            if (metersToOne < metersToTwo && metersToOne < metersToThree) {
                Toast.makeText(getBaseContext(), "Closest mission by is 1st mission " + metersToOne + " meters away!", Toast.LENGTH_LONG).show();
            }
            if (metersToTwo < metersToOne && metersToTwo < metersToThree) {
                Toast.makeText(getBaseContext(), "Closest mission by is 2nd mission " + metersToTwo + " meters away!", Toast.LENGTH_LONG).show();
            }
            if (metersToThree < metersToTwo && metersToThree < metersToOne) {
                Toast.makeText(getBaseContext(), "Closest mission by is 3rd mission " + metersToThree + " meters away!", Toast.LENGTH_LONG).show();
            }
        }

        LatLng camlatLng = new LatLng(location.getLatitude(),location.getLongitude());
            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(camlatLng, 17));

        //stop location updates
      /*  if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
        */
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
    /*
    This method is used for drawing a marker on the map with a little red circle. The marker is added to the google map and is assigned apropriate variable
    as well as an icon
     */
    private void drawMarkerWithCircle(LatLng position,int type){
        double radiusInMeters = 5.0;
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill

        CircleOptions circleOptions = new CircleOptions().center(position).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);

       if (type==0) {
           mCircle = mGoogleMap.addCircle(circleOptions);
           MarkerOptions markerOptions = new MarkerOptions().position(position).title("Objective").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo));
           firstObjective = mGoogleMap.addMarker(markerOptions);
       }
        if (type==1) {
            mCircle2 = mGoogleMap.addCircle(circleOptions);
            MarkerOptions markerOptions = new MarkerOptions().position(position).title("Objective").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo));
            secondObjective = mGoogleMap.addMarker(markerOptions);
        }
        if (type==2) {
            mCircle3 = mGoogleMap.addCircle(circleOptions);
            MarkerOptions markerOptions = new MarkerOptions().position(position).title("Objective").icon(BitmapDescriptorFactory.fromResource(R.drawable.logo));
            thirdObjective = mGoogleMap.addMarker(markerOptions);
        }

    }

    private PendingIntent getActivityDetectionPendingIntent() {
        Intent intent = new Intent(this, DetectedActivitiesIntentService.class);

        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // requestActivityUpdates() and removeActivityUpdates().
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private SharedPreferences getSharedPreferencesInstance() {
        return getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
    }

    private boolean getUpdatesRequestedState() {
        return getSharedPreferencesInstance()
                .getBoolean(Constants.ACTIVITY_UPDATES_REQUESTED_KEY, false);
    }

    private void setUpdatesRequestedState(boolean requestingUpdates) {
        getSharedPreferencesInstance()
                .edit()
                .putBoolean(Constants.ACTIVITY_UPDATES_REQUESTED_KEY, requestingUpdates)
                .commit();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(Constants.DETECTED_ACTIVITIES, mDetectedActivities);
        super.onSaveInstanceState(savedInstanceState);
    }

    protected void updateDetectedActivitiesList(ArrayList<DetectedActivity> detectedActivities) {
        mAdapter.updateActivities(detectedActivities);
    }

    public void onResume(){
       super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                new IntentFilter(Constants.BROADCAST_ACTION));
    }
    @Override
    public void onResult(@NonNull Status status) {

    }

    public class ActivityDetectionBroadcastReceiver extends BroadcastReceiver {
        protected static final String TAG = "activity-detection-response-receiver";

        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<DetectedActivity> updatedActivities =
                    intent.getParcelableArrayListExtra(Constants.ACTIVITY_EXTRA);
            updateDetectedActivitiesList(updatedActivities);
        }
    }


}
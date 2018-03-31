package com.example.panorama.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.panorama.Mediator;
import com.example.panorama.R;
import com.example.panorama.model.GPSTracker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by IvanGlez on 14/03/2018.
 */

public class TagsActivity extends AppCompatActivity implements ITagsActivity, SensorEventListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> datos;
    private FloatingActionButton addTagBtn;
    private VrPanoramaView mVrPanoramaView;
    private Toolbar toolbar;
    private Mediator mediator;
    private ImageView uploadBtn;
    private FusedLocationProviderClient mFusedLocationClient;
    private SensorManager mSensorManager;
    private Sensor mPressure;
    private Sensor mTemperature;
    private Sensor mLight;
    private Integer count1;
    private Integer count2;
    private Integer count3;
    private ArrayList<String> sensorTags;
    private GPSTracker gps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        mediator = Mediator.getInstance();
        mediator.setTagsActivity(this);

        mediator.getPresenter().getWeatherInfo();

        mRecyclerView = (RecyclerView) findViewById(R.id.lista);

        datos = new ArrayList<>();

        addTagBtn = (FloatingActionButton) findViewById(R.id.floatingAddButton);
        addTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTagAlert();
            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        uploadBtn = findViewById(R.id.upload_icon);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> aux = new ArrayList<>();
                for(int i = 0; i < sensorTags.size(); i++) {
                    String substr = sensorTags.get(i).substring(0,7);
                    if(substr.equals("Latitud")){
                        aux.add(sensorTags.get(i));
                    }
                }
                if(aux.isEmpty()){

                    gps = new GPSTracker(TagsActivity.this);
                    if(gps.canGetLocation()) {

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        String latitudeStr = String.valueOf(latitude);
                        String longitudeStr = String.valueOf(longitude);

                        sensorTags.add("Latitud: " + latitudeStr);
                        sensorTags.add("Longitud: " + longitudeStr);
                    }
                }

                for(int i = 0; i < sensorTags.size(); i++) {
                    Log.d("SensorData: ", sensorTags.get(i));
                }
            }
        });


        mVrPanoramaView = (VrPanoramaView) findViewById(R.id.pano_view);

        loadPhotoSphere();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Adapter(datos);
        mRecyclerView.setAdapter(mAdapter);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        /*if(mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null){
            mPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        }

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            mTemperature = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        }*/

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null){
            mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        count1 = 1;
        count2 = 1;
        count3 = 1;

        sensorTags = new ArrayList<>();

        // create class object
        gps = new GPSTracker(TagsActivity.this);

        if(!gps.canGetLocation()){

            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            showSettingsAlert();
        }

        setSensorTags();

    }

    public void showAddTagAlert() {
        /*AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Add a new tag");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Tag name");
        alert.setView(input);
        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputText = input.getText().toString();
                datos.add(inputText);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAdapter.notifyDataSetChanged();
            }
        });
        alert.show();*/

        AddTagDialog cdd = new AddTagDialog(TagsActivity.this);
        cdd.show();
    }

    public void showSettingsAlert(){
        GPSSettingsDialog cdd = new GPSSettingsDialog(TagsActivity.this);
        cdd.show();
    }

    public void addNewTag(String tag) {
        datos.add(tag);
        mAdapter.notifyDataSetChanged();
    }

    private void loadPhotoSphere() {
        //This could take a while. Should do on a background thread, but fine for current example
        VrPanoramaView.Options options = new VrPanoramaView.Options();


        Intent i = getIntent();
        String fileName = i.getStringExtra("fileName");
        File imgFile = new File(fileName);

        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());


            options.inputType = VrPanoramaView.Options.TYPE_MONO;
            mVrPanoramaView.loadImageFromBitmap(myBitmap, options);
        } else {
            Log.d("Error", "La imagen no existe");
        }
    }

    public void setSensorTags() {


        //GPS

        // check if GPS enabled
        if(gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            String latitudeStr = String.valueOf(latitude);
            String longitudeStr = String.valueOf(longitude);

            sensorTags.add("Latitud: " + latitudeStr);
            sensorTags.add("Longitud: " + longitudeStr);

            Geocoder gcd = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null && addresses.size() > 0) {
                String locality = addresses.get(0).getLocality();
                sensorTags.add("Lugar: " + locality);
            }
        }

        //DATE & TIME

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
        String date = "Date: " + df.format(Calendar.getInstance().getTime());
        sensorTags.add(date);

        //AMBIENT SENSORS


    }

    @Override
    protected void onPause() {
        mVrPanoramaView.pauseRendering();
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVrPanoramaView.resumeRendering();
        /*mSensorManager.registerListener(this, mPressure, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mTemperature, SensorManager.SENSOR_DELAY_NORMAL);*/
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        mVrPanoramaView.shutdown();
        super.onDestroy();
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {

        if (event.sensor.equals(mPressure) && count1 == 1) {
            count1 = 0;
            float millibars_of_pressure = event.values[0];
            String pressure = "Pressure: " + String.valueOf(millibars_of_pressure);
            sensorTags.add(pressure);
        } else if (event.sensor.equals(mTemperature) && count2 == 1) {
            count2 = 0;
            float temperature = event.values[0];
            String temperatureStr = "Ambient Temperature: " + String.valueOf(temperature) + "ºC";
            sensorTags.add(temperatureStr);
        } else if (event.sensor.equals(mLight) && count3 == 1) {
            count3 = 0;
            float lx = event.values[0];
            String lxStr = "Light : " + String.valueOf(lx) + "lx";
            sensorTags.add(lxStr);
        }

        mSensorManager.unregisterListener(this);

    }

    public void setWeatherTags(ArrayList<String> data){
        sensorTags.add("Ciudad: " + data.get(0));
        sensorTags.add("Temperatura: " + data.get(1));
        sensorTags.add("Concicion: " + data.get(4));
    }


}





package com.example.panorama.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.panorama.Mediator;
import com.example.panorama.R;
import com.example.panorama.model.PanoramicImage;

import java.util.ArrayList;

/**
 * Created by IvanGlez on 01/04/2018.
 */

public class UploadDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button upload, cancel;
    public TextView path, zone, latitude, longitude, date, weather, temperature, pressure, humidity, windSpeed, windDir, ilumination;

    public UploadDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_upload);

        upload = (Button) findViewById(R.id.upload_btn);
        cancel = (Button) findViewById(R.id.cancel_btn);
        upload.setOnClickListener(this);
        cancel.setOnClickListener(this);

        path = findViewById(R.id.path);
        zone = findViewById(R.id.zone);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        date = findViewById(R.id.date);
        weather = findViewById(R.id.weather);
        temperature = findViewById(R.id.temperature);
        pressure = findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);
        windSpeed = findViewById(R.id.winSpeed);
        windDir = findViewById(R.id.winDir);
        ilumination = findViewById(R.id.ilumination);

        setTextViews();

    }

    public void setTextViews(){
        PanoramicImage image = Mediator.getInstance().getTagsActivity().getImage();
        path.setText(image.getPath().toString());
        zone.setText(image.getZone().toString());
        latitude.setText(image.getLatitude().toString());
        longitude.setText(image.getLongitude().toString());
        date.setText(image.getDate().toString());
        weather.setText(image.getCondition().toString());
        temperature.setText(image.getTemperature().toString() + " ºC");
        pressure.setText(image.getPressure().toString() + " hPa");
        humidity.setText(image.getHumidity().toString() + " %");
        windSpeed.setText(image.getWindVel().toString() + " m/s");
        windDir.setText(image.getWindDir().toString() + " degrees");
        ilumination.setText(image.getIlumination().toString() + " lx");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_btn:
                dismiss();
                break;
            case R.id.cancel_btn:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}

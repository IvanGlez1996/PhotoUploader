package com.example.panorama.view.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panorama.Mediator;
import com.example.panorama.R;
import com.example.panorama.model.database.CustomTag;
import com.example.panorama.model.database.PanoramicImage;
import com.example.panorama.model.low_level.UploadConstants;
import com.example.panorama.view.ActivityCamera;
import com.example.panorama.view.adapters.AdapterUploadDialog;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by IvanGlez on 01/04/2018.
 */

public class DialogUploadImage extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button upload, cancel;
    public TextView path, zone, latitude, longitude, date, weather, temperature, pressure, humidity, windSpeed, windDir, ilumination;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Mediator mediator;
    private PanoramicImage image;
    private String imagePath;
    private ProgressDialog ringProgressDialog;
    private List<String> customTags = new ArrayList<>();

    public DialogUploadImage(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_upload);

        mediator = Mediator.getInstance();

        upload = findViewById(R.id.upload_btn);
        cancel = findViewById(R.id.cancel_btn);
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
        mRecyclerView = findViewById(R.id.lista);

        image = mediator.getPresenterUploadImage().getImage(mediator.getActivityUploadImage().getImagePath());

        imagePath = image.getPath();

        List<CustomTag> customTagsObj = mediator.getPresenterUploadImage().getImageTagsFromDatabase(imagePath);

        for(int i = 0; i<customTagsObj.size(); i++){
            customTags.add(customTagsObj.get(i).getName());
        }

        setTextViews();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        List<CustomTag> customTags = mediator.getPresenterUploadImage().getImageTagsFromDatabase(image.getPath());
        mAdapter = new AdapterUploadDialog(customTags);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void setTextViews(){
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

    /*
     * This is the method responsible for image upload
     * We need the full image path and the name for the image in this method
     * */
    public void uploadMultipart() {
        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            MultipartUploadRequest myMPur = new MultipartUploadRequest(c.getApplicationContext(), uploadId, UploadConstants.UPLOAD_URL);
                    myMPur.addFileToUpload(imagePath, "image") //Adding file
                    .addParameter("name", image.getPath()) //Adding text parameter to the request
                    .addParameter("zone", image.getZone()) //Adding text parameter to the request
                    .addParameter("latitude", image.getLatitude()) //Adding text parameter to the request
                    .addParameter("longitude", image.getLongitude()) //Adding text parameter to the request
                    .addParameter("date", image.getDate()) //Adding text parameter to the request
                    .addParameter("weather", image.getCondition()) //Adding text parameter to the request
                    .addParameter("temperature", image.getTemperature()) //Adding text parameter to the request
                    .addParameter("pressure", image.getPressure()) //Adding text parameter to the request
                    .addParameter("humidity", image.getHumidity()) //Adding text parameter to the request
                    .addParameter("windSpeed", image.getWindVel()) //Adding text parameter to the request
                    .addParameter("windDir", image.getWindDir()) //Adding text parameter to the request
                    .addParameter("illumination", image.getIlumination());//Adding text parameter to the request;

                    if(customTags.size() == 0) {
                        myMPur.addParameter("customTags" , "null"); //Adding text parameter to the request
                    } else {
                        for(int i = 0; i<customTags.size(); i++) {
                            myMPur.addParameter("customTags" + "[" + i + "]", customTags.get(i)); //Adding text parameter to the request
                        }
                    }

                    myMPur.setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {
                            // your code here
                            showProcessingDialog();
                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse,
                                            Exception exception) {
                            Log.e("Server error: ", exception.getMessage());
                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {
                            // your code here
                            // if you have mapped your server response to a POJO, you can easily get it:
                            // YourClass obj = new Gson().fromJson(serverResponse.getBodyAsString(), YourClass.class);
                            Log.d("Server response: ", serverResponse.getBodyAsString());
                            if(!serverResponse.getBodyAsString().equals(" imageExists")) {
                                closeProcessingDialog();
                                Toast.makeText(c.getApplicationContext(), "Image successfully uploaded!", Toast.LENGTH_SHORT).show();
                                c.finish();
                            }else{
                                Toast.makeText(c.getApplicationContext(), "Upload cancelled. Image already exists in the server", Toast.LENGTH_LONG).show();
                                closeProcessingDialog();
                                c.finish();
                            }

                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {
                            // your code here
                        }
                    })
                    .startUpload();

        } catch (Exception exc) {
            Log.e("AndroidUploadService", exc.getMessage(), exc);
        }
    }

    public void showProcessingDialog(){
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ringProgressDialog = ProgressDialog.show(c, "",	"Uploading image", true);
                ringProgressDialog.setCancelable(false);
            }
        });
    }


    public void closeProcessingDialog(){
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ringProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_btn:
                uploadMultipart();
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

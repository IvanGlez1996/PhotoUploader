package com.example.panorama.view;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by IvanGlez on 01/04/2018.
 */

public interface IActivityPanoramaPreview {

    void showImage(Bitmap myBitmap);

    void showSettingsAlert();

    void unregisterSensorListener();

    void registerSensorListener();

    void saveImageIntoDatabase(ArrayList<String> data);

    String getFilename();

    void finishActivity();
}

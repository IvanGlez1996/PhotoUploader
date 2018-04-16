package com.example.panorama.view;

import android.graphics.Bitmap;


/**
 * Created by IvanGlez on 01/04/2018.
 */

public interface IActivityImagePreview {

    void showImage(Bitmap myBitmap);

    void showSettingsAlert();

    void unregisterSensorListener();

    void registerSensorListener();

    String getImagePath();

    void finishActivity();
}

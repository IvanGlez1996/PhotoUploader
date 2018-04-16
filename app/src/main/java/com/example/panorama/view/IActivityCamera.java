package com.example.panorama.view;

import android.hardware.Camera;

public interface IActivityCamera {


    void showProcessingDialog();

    void closeProcessingDialog();

    Camera.Size getBestPreviewSize(Camera.Parameters parameters);

    void showSettingsAlert();
}

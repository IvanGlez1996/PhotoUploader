package com.example.panorama.presenter;

import java.util.ArrayList;

public interface IPresenterImagePreview {

    void saveImageIntoDatabase(ArrayList<String> data);

    void getWeatherInfo();

    void loadPhotoSphere(String filename);


    void registerSensorListener();

    void unregisterSensorListener();

    void saveImage();

    void goToActivityUploadImage();

    boolean canGetLocation();

    void initGPS();

    void initSensors();

    void setTagValues();
}


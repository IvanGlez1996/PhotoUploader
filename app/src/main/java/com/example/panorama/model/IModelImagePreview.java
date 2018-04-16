package com.example.panorama.model;

import android.graphics.Bitmap;

import com.example.panorama.model.database.CustomTag;
import com.example.panorama.model.database.PanoramicImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IvanGlez on 13/03/2018.
 */

public interface IModelImagePreview {

    void saveImageIntoDatabase(ArrayList<String> data);

    Bitmap getPhotoSphere(String filename);

    boolean getImageFileExists();

    void setImageFileExists(boolean imgFileExists);

    void registerSensorListener();

    void unregisterSensorListener();

    void setTagsValues();

    void setWeatherTags(ArrayList<String> data);

    void saveImage();

    void initSensorsService();

    void initGPSService();

    boolean canGetLocation();
}

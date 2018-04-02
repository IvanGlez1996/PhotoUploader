package com.example.panorama.model;

import java.util.ArrayList;

/**
 * Created by IvanGlez on 13/03/2018.
 */

public interface IModel {
    void saveImageIntoDatabase(ArrayList<String> data);

    PanoramicImage getImage(String path);
}

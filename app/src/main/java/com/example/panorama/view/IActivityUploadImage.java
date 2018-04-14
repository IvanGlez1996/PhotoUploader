package com.example.panorama.view;

import com.example.panorama.model.database.PanoramicImage;

/**
 * Created by IvanGlez on 15/03/2018.
 */

public interface IActivityUploadImage {

    void addNewTag(String tag);

    PanoramicImage getImage();
}

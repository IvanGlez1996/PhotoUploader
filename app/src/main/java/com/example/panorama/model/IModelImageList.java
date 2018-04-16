package com.example.panorama.model;

import com.example.panorama.model.database.CustomTag;
import com.example.panorama.model.database.PanoramicImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IvanGlez on 13/03/2018.
 */

public interface IModelImageList {

    List<PanoramicImage> getImages();

    void deleteImageFromDatabase(String path);

}

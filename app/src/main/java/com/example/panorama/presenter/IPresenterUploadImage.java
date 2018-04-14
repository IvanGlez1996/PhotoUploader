package com.example.panorama.presenter;

import com.example.panorama.model.database.CustomTag;
import com.example.panorama.model.database.PanoramicImage;

import java.util.ArrayList;
import java.util.List;

public interface IPresenterUploadImage {


    void saveImageIntoDatabase(ArrayList<String> data);

    PanoramicImage getImage(String path);

    void saveTagIntoDatabase(String id, String imagePath, String name);

    void deleteTagFromDatabase(String id);

    List<CustomTag> getImageTagsFromDatabase(String imagePath);

    List<PanoramicImage> getImages();

    void deleteImageFromDatabase(String path);



}

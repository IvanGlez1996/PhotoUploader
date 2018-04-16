package com.example.panorama.presenter;

import com.example.panorama.model.database.CustomTag;
import com.example.panorama.model.database.PanoramicImage;

import java.util.List;

public interface IPresenterUploadImage {

    PanoramicImage getImage(String path);

    void saveTagIntoDatabase(String id, String imagePath, String name);

    void deleteTagFromDatabase(String id);

    List<CustomTag> getImageTagsFromDatabase(String imagePath);

    void loadPhotoSphere(String filename);

    void showCustomTags(String imagePath);

    void addNewTag(String tagText, String imagePath);
}


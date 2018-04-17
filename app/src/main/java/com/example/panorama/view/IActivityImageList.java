package com.example.panorama.view;

import com.example.panorama.model.database.PanoramicImage;

import java.util.List;

public interface IActivityImageList {

    void goToTagsActivity(String imagePath);

    void setAdapter(List<PanoramicImage> images);

    void showImages();

    void setInfoTextVisible(boolean visible);
}

package com.example.panorama.presenter;

import com.example.panorama.model.database.CustomTag;
import com.example.panorama.model.database.PanoramicImage;

import java.util.ArrayList;
import java.util.List;

public interface IPresenterImageList {

    List<PanoramicImage> getImages();

    void deleteImageFromDatabase(String path);

    void showImages();



}


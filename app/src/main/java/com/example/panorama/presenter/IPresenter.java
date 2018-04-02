package com.example.panorama.presenter;

import com.example.panorama.model.PanoramicImage;

import java.util.ArrayList;

public interface IPresenter {

    void getWeatherInfo();

    void saveImageIntoDatabase(ArrayList<String> data);

    PanoramicImage getImage(String path);
}


package com.example.panorama.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.panorama.Mediator;
import com.example.panorama.model.database.CustomTag;
import com.example.panorama.model.IModelPanoramaPreview;
import com.example.panorama.model.ModelPanoramaPreview;
import com.example.panorama.model.database.PanoramicImage;
import com.example.panorama.view.ActivityPanoramaPreview;

import java.util.ArrayList;
import java.util.List;


public class PresenterUploadImage implements IPresenterUploadImage {

    private Mediator mediator;
    private ActivityPanoramaPreview panoramaPreviewActivity;
    private IModelPanoramaPreview model;


    @Override
    public void saveImageIntoDatabase(ArrayList<String> data){
        model.saveImageIntoDatabase(data);
    }

    @Override
    public PanoramicImage getImage(String path) {
        return model.getImage(path);
    }

    @Override
    public void saveTagIntoDatabase(String id, String imagePath, String name) {
        model.saveTagIntoDatabase(id, imagePath, name);
    }

    @Override
    public void deleteTagFromDatabase(String id) {
        model.deleteTagFromDatabase(id);
    }

    @Override
    public List<CustomTag> getImageTagsFromDatabase(String imagePath) {
        return model.getImageTagsFromDatabase(imagePath);
    }

    @Override
    public List<PanoramicImage> getImages() {
        return model.getImages();
    }

    @Override
    public void deleteImageFromDatabase(String path) {
        model.deleteImageFromDatabase(path);
    }


    public PresenterUploadImage() {
        mediator = Mediator.getInstance();
        panoramaPreviewActivity = (ActivityPanoramaPreview) mediator.getActivityPanoramaPreview();
        model = ModelPanoramaPreview.getInstance();
    }



}

package com.example.panorama.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.panorama.Mediator;
import com.example.panorama.model.IModelUploadImage;
import com.example.panorama.model.ModelUploadImage;
import com.example.panorama.model.database.CustomTag;
import com.example.panorama.model.database.PanoramicImage;
import com.example.panorama.view.ActivityUploadImage;

import java.util.List;


public class PresenterUploadImage implements IPresenterUploadImage {

    private Mediator mediator;
    private ActivityUploadImage activityUploadImage;
    private IModelUploadImage model;

    public PresenterUploadImage() {
        mediator = Mediator.getInstance();
        activityUploadImage = (ActivityUploadImage) mediator.getActivityUploadImage();
        model = ModelUploadImage.getInstance();
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
    public void loadPhotoSphere(String filename) {
        Bitmap myBitmap = model.getPhotoSphere(filename);
        if (model.getImageFileExists()) {
            activityUploadImage.showImage(myBitmap);
        } else {
            Log.d("Error", "The image doesn't exist");
        }
    }

    @Override
    public void showCustomTags(String imagePath){
        List<CustomTag> data = getImageTagsFromDatabase(imagePath);
        activityUploadImage.setAdapter(data);
    }

    @Override
     public void addNewTag(String tagText, String imagePath){
        CustomTag customTag = new CustomTag(tagText, imagePath);
        saveTagIntoDatabase(customTag.getCustomTagId(), imagePath, tagText);
        activityUploadImage.notifyAdapterDataSetChanged();
    }


}

package com.example.panorama.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.panorama.Mediator;
import com.example.panorama.model.database.CustomTag;
import com.example.panorama.model.database.DatabaseFacade;
import com.example.panorama.model.database.PanoramicImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ModelUploadImage implements IModelUploadImage {

    private static ModelUploadImage singleton = null;
    private DatabaseFacade database;
    private boolean imgFileExists = false;


    private ModelUploadImage() {
        database = DatabaseFacade.getInstance();
    }

    public static ModelUploadImage getInstance() {
        if (singleton == null)
            singleton = new ModelUploadImage();
        return singleton;
    }

    @Override
    public PanoramicImage getImage(String path) {
        return database.getImageFromDatabase(path);
    }

    @Override
    public void saveTagIntoDatabase(String id, String imagePath, String name) {
        database.addCustomTagToDatabase(id, imagePath, name);
    }

    @Override
    public void deleteTagFromDatabase(String id) {
        database.deleteCustomTagFromDatabase(id);
    }

    @Override
    public List<CustomTag> getImageTagsFromDatabase(String imagePath) {
        return database.getImageCustomTagsFromDatabase(imagePath);
    }

    @Override
    public Bitmap getPhotoSphere(String filename) {
        Bitmap result;
        File imgFile = new File(filename);

        if (imgFile.exists()) {
            setImageFileExists(true);
            String path = imgFile.getAbsolutePath();
            Bitmap myBitmap = BitmapFactory.decodeFile(path);

            result = myBitmap;
        } else {
            Log.d("Error", "The image doesn't exist");
            result = null;
        }
        return result;
    }

    @Override
    public boolean getImageFileExists(){
        return imgFileExists;
    }

    @Override
    public void setImageFileExists(boolean imgFileExists){
        this.imgFileExists = imgFileExists;
    }
}

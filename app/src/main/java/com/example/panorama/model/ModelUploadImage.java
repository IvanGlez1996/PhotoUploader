package com.example.panorama.model;

import com.example.panorama.Mediator;
import com.example.panorama.model.database.CustomTag;
import com.example.panorama.model.database.DatabaseFacade;
import com.example.panorama.model.database.PanoramicImage;

import java.util.ArrayList;
import java.util.List;


public class ModelUploadImage implements IModelUploadImage {

    private static ModelUploadImage singleton = null;
    private Mediator appMediador;
    private DatabaseFacade database;

    private ModelUploadImage() {
        appMediador = Mediator.getInstance();
        database = DatabaseFacade.getInstance();
    }

    public static ModelUploadImage getInstance() {
        if (singleton == null)
            singleton = new ModelUploadImage();
        return singleton;
    }

    public void saveImageIntoDatabase(ArrayList<String> data){
        database.addPanoramicImageToDatabase(data.get(0), data.get(3), data.get(4), data.get(1), data.get(2), data.get(5), data.get(6), data.get(7), data.get(8), data.get(9), data.get(10), data.get(11));
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
    public List<PanoramicImage> getImages() {
        return database.getImagesFromDatabase();
    }

    @Override
    public void deleteImageFromDatabase(String path) {
        database.deleteImageFromDatabase(path);
    }
}

package com.example.panorama.model;

import com.example.panorama.Mediator;
import com.example.panorama.model.database.CustomTag;
import com.example.panorama.model.database.DatabaseFacade;
import com.example.panorama.model.database.PanoramicImage;

import java.util.ArrayList;
import java.util.List;


public class ModelImageList implements IModelImageList {

    private static ModelImageList singleton = null;
    private Mediator mediator;
    private DatabaseFacade database;

    private ModelImageList() {
        mediator = Mediator.getInstance();
        database = DatabaseFacade.getInstance();
    }

    public static ModelImageList getInstance() {
        if (singleton == null)
            singleton = new ModelImageList();
        return singleton;
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

package com.example.panorama.model;

import com.example.panorama.Mediator;

import java.util.ArrayList;


public class Model implements IModel {

    private static Model singleton = null;
    private Mediator appMediador;
    private DatabaseFacade database;

    private Model() {
        appMediador = Mediator.getInstance();
        database = DatabaseFacade.getInstance();
    }

    public static Model getInstance() {
        if (singleton == null)
            singleton = new Model();
        return singleton;
    }

    public void saveImageIntoDatabase(ArrayList<String> data){
        database.addPanoramicImageToDatabase(data.get(0), data.get(3), data.get(4), data.get(1), data.get(2), data.get(5), data.get(6), data.get(7), data.get(8), data.get(9), data.get(10), data.get(11));
    }

    @Override
    public PanoramicImage getImage(String path) {
        return database.getImageFromDatabase(path);
    }
}

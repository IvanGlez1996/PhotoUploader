package com.example.panorama.model;

import com.example.panorama.Mediator;


public class Model implements IModel {

    private static Model singleton = null;
    private Mediator appMediador;

    private Model() {
        appMediador = Mediator.getInstance();
    }

    public static Model getInstance() {
        if (singleton == null)
            singleton = new Model();
        return singleton;
    }


}

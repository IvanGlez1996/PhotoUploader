package com.example.panorama.model;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by IvanGlez on 31/03/2018.
 */

public class DatabaseFacade {
    private Realm realmDatabase;
    private static DatabaseFacade databaseFacade;

    public static synchronized DatabaseFacade getInstance() {
        if (databaseFacade == null) {
            databaseFacade = new DatabaseFacade();
        }
        return databaseFacade;
    }

    private DatabaseFacade() {
        realmDatabase = Realm.getDefaultInstance();

    }


    /////////////////////////////////////////////////////////////////////////////////////

    public void addPanoramicImageToDatabase(String path, String zone, String date, String latitude, String longitude,
    String ilumination, String temperature, String pressure, String humidity, String windVel, String windDir) {
        realmDatabase.beginTransaction();
        PanoramicImage image = realmDatabase.createObject(PanoramicImage.class, UUID.randomUUID().toString());
        image.setPath(path);
        image.setZone(zone);
        image.setDate(date);
        image.setLatitude(latitude);
        image.setLongitude(longitude);
        image.setIlumination(ilumination);
        image.setTemperature(temperature);
        image.setPressure(pressure);
        image.setHumidity(humidity);
        image.setWindVel(windVel);
        image.setWindDir(windDir);
        realmDatabase.commitTransaction();
    }

    public List<PanoramicImage> getImagesFromDatabase(){
        return realmDatabase.where(PanoramicImage.class).findAll();
    }

    public PanoramicImage getImageFromDatabase(String imageId){
        return realmDatabase.where(PanoramicImage.class).equalTo("imageId", imageId).findFirst();
    }

    public void addCustomTagToDatabase(String imageId, String name){
        realmDatabase.beginTransaction();
        CustomTag customTag = realmDatabase.createObject(CustomTag.class, UUID.randomUUID().toString());
        customTag.setImage(imageId);
        customTag.setName(name);

    }

    public List<CustomTag> getImageCustomTagsromDatabase(String imageId){
        return realmDatabase.where(CustomTag.class).equalTo("image", imageId).findAll();
    }




}

package com.example.panorama.model;

import android.util.Log;

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
    String ilumination, String temperature, String pressure, String humidity, String condition, String windVel, String windDir) {
        realmDatabase.beginTransaction();
        PanoramicImage image = realmDatabase.createObject(PanoramicImage.class, path);
        image.setZone(zone);
        image.setDate(date);
        image.setLatitude(latitude);
        image.setLongitude(longitude);
        image.setIlumination(ilumination);
        image.setTemperature(temperature);
        image.setPressure(pressure);
        image.setHumidity(humidity);
        image.setCondition(condition);
        image.setWindVel(windVel);
        image.setWindDir(windDir);
        realmDatabase.commitTransaction();

        Log.d("Database", "Adición exitosa");
    }

    public List<PanoramicImage> getImagesFromDatabase(){
        return realmDatabase.where(PanoramicImage.class).findAll();
    }

    public PanoramicImage getImageFromDatabase(String path){
        PanoramicImage image = realmDatabase.where(PanoramicImage.class).equalTo("path", path).findFirst();
        String weather = image.getCondition();
        return image;
    }

    public void addCustomTagToDatabase(String imagePath, String name){
        realmDatabase.beginTransaction();
        CustomTag customTag = realmDatabase.createObject(CustomTag.class, UUID.randomUUID().toString());
        customTag.setImage(imagePath);
        customTag.setName(name);

    }

    public List<CustomTag> getImageCustomTagsromDatabase(String imageId){
        return realmDatabase.where(CustomTag.class).equalTo("image", imageId).findAll();
    }




}

package com.example.panorama.model.database;


import java.util.List;
import io.realm.Realm;

/**
 * Class to handle the connection with the database
 * @author Ivan González Hernández
 * @version 09/04/2018
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

    public void deleteDatabase(){
        realmDatabase.beginTransaction();
        realmDatabase.deleteAll();
        realmDatabase.commitTransaction();
    }



    ////////////////////////////////////PANORAMIC IMAGES METHODS/////////////////////////////////////////////////////////////////////////////

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
    }

    public void deleteImageFromDatabase(final String imagePath) {
        realmDatabase.beginTransaction();
        realmDatabase.where(PanoramicImage.class).equalTo("path", imagePath).findAll().deleteAllFromRealm();
        realmDatabase.commitTransaction();
    }

    public List<PanoramicImage> getImagesFromDatabase(){
        List<PanoramicImage> list = realmDatabase.where(PanoramicImage.class).findAll();

        //The returned List is copied into another, because we need a List with pure PanoramicImage objects in order to be sorted later,
        //and in the first List, these objects are RealmObjects that doesn't allow us to do that.
        List<PanoramicImage> newList = realmDatabase.copyFromRealm(list);
        return newList;
    }

    public PanoramicImage getImageFromDatabase(String path){
        PanoramicImage image = realmDatabase.where(PanoramicImage.class).equalTo("path", path).findFirst();
        return image;
    }



    //////////////////////////////////////CUSTOMTAGS METHODS////////////////////////////////////////////////////////////////////////////

    public void addCustomTagToDatabase(String id, String imagePath, String name){
        realmDatabase.beginTransaction();
        CustomTag customTag = realmDatabase.createObject(CustomTag.class, id);
        customTag.setImage(imagePath);
        customTag.setName(name);

        realmDatabase.commitTransaction();

    }

    public void deleteCustomTagFromDatabase(final String tagId) {
        realmDatabase.beginTransaction();
        realmDatabase.where(CustomTag.class).equalTo("customTagId", tagId).findAll().deleteAllFromRealm();
        realmDatabase.commitTransaction();
    }

    public List<CustomTag> getImageCustomTagsFromDatabase(String imagePath){
        List<CustomTag> result = realmDatabase.where(CustomTag.class).equalTo("imagePath", imagePath).findAll();
        return result;
    }

}

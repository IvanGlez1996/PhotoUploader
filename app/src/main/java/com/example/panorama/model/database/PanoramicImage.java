package com.example.panorama.model.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by IvanGlez on 31/03/2018.
 */

public class PanoramicImage extends RealmObject {

    @PrimaryKey
    private String path;

    private String zone;
    private String date;
    private String latitude;
    private String longitude;
    private String ilumination;
    private String temperature;
    private String pressure;
    private String humidity;
    private String windVel;
    private String windDir;
    private String condition;

    public PanoramicImage(String path, String zone, String date, String latitude, String longitude,
                          String ilumination, String temperature, String pressure, String humidity,
                          String windVel, String windDir, String condition){
        this.path = path;
        this.zone = zone;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ilumination = ilumination;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windVel = windVel;
        this.windDir = windDir;
        this.condition = condition;
    }

    public PanoramicImage(){

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIlumination() {
        return ilumination;
    }

    public void setIlumination(String ilumination) {
        this.ilumination = ilumination;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindVel() {
        return windVel;
    }

    public void setWindVel(String windVel) {
        this.windVel = windVel;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}

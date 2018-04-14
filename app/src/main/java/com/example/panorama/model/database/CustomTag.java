package com.example.panorama.model.database;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by IvanGlez on 31/03/2018.
 */

public class CustomTag extends RealmObject{

    @PrimaryKey
    private String customTagId;

    private String name;
    private String imagePath;

    public CustomTag(String name, String image){
        this.customTagId = UUID.randomUUID().toString();
        this.name = name;
        this.imagePath = image;
    }

    public CustomTag(){

    }

    public String getCustomTagId() {
        return customTagId;
    }

    public void setCustomTagId(String customTagId) {
        this.customTagId = customTagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImage(String imagePath) {
        this.imagePath = imagePath;
    }

}

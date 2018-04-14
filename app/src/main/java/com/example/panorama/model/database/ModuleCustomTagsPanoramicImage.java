package com.example.panorama.model.database;

import com.example.panorama.model.database.CustomTag;
import com.example.panorama.model.database.PanoramicImage;

import io.realm.annotations.RealmModule;

/**
 * Created by IvanGlez on 31/03/2018.
 */

@RealmModule(classes = {CustomTag.class, PanoramicImage.class})
public class ModuleCustomTagsPanoramicImage {
}

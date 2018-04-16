package com.example.panorama.view;

import android.graphics.Bitmap;

import com.example.panorama.model.database.CustomTag;

import java.util.List;

/**
 * Created by IvanGlez on 15/03/2018.
 */

public interface IActivityUploadImage {

    void showAddTagAlert();

    void showUploadDialog();

    void addNewTag(String tag);

    void notifyAdapterDataSetChanged();

    String getImagePath();

    void setAdapter(List<CustomTag> tags);

    void showCustomTags(String imagePath);

    void showImage(Bitmap myBitmap);
}

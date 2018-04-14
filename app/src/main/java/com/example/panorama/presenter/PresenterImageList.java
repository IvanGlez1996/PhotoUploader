package com.example.panorama.presenter;

import com.example.panorama.Mediator;
import com.example.panorama.model.IModelPanoramaPreview;
import com.example.panorama.model.ModelPanoramaPreview;
import com.example.panorama.model.database.PanoramicImage;
import com.example.panorama.view.ActivityImageList;
import com.example.panorama.view.IActivityImageList;

import java.io.File;
import java.util.Collections;
import java.util.List;


public class PresenterImageList implements IPresenterImageList {

    private Mediator mediator;
    private IActivityImageList imageListActivity;
    private IModelPanoramaPreview model;

    public PresenterImageList() {
        mediator = Mediator.getInstance();
        imageListActivity = mediator.getActivityImageList();
        model = ModelPanoramaPreview.getInstance();
    }

    @Override
    public List<PanoramicImage> getImages() {
        return model.getImages();
    }

    @Override
    public void deleteImageFromDatabase(String path) {
        model.deleteImageFromDatabase(path);
    }

    @Override
    public void showImages() {
        List<PanoramicImage> data = getImages();
        for(int i = 0; i < data.size(); i++) {
            String path = data.get(i).getPath();
            File file = new File(path);
            if(!file.exists()){
                deleteImageFromDatabase(path);
                data.remove(i);
            }
        }
        Collections.reverse(data);
        imageListActivity.setAdapter(data);
    }

}

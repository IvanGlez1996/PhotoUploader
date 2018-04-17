package com.example.panorama.presenter;

import com.example.panorama.Mediator;
import com.example.panorama.model.IModelImageList;
import com.example.panorama.model.ModelImageList;
import com.example.panorama.model.database.PanoramicImage;
import com.example.panorama.view.IActivityImageList;

import java.io.File;
import java.util.Collections;
import java.util.List;


public class PresenterImageList implements IPresenterImageList {

    private Mediator mediator;
    private IActivityImageList imageListActivity;
    private IModelImageList model;

    public PresenterImageList() {
        mediator = Mediator.getInstance();
        imageListActivity = mediator.getActivityImageList();
        model = ModelImageList.getInstance();
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
        if(data.size() == 0){
            imageListActivity.setInfoTextVisible(true);
        } else {
            imageListActivity.setInfoTextVisible(false);
        }
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

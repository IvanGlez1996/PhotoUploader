package com.example.panorama.presenter;

import com.example.panorama.Mediator;
import com.example.panorama.model.IModelImagePreview;
import com.example.panorama.model.ModelImagePreview;
import com.example.panorama.view.ActivityImagePreview;


public class PresenterCamera implements IPresenterCamera {

    private Mediator mediator;
    private ActivityImagePreview panoramaPreviewActivity;
    private IModelImagePreview model;


    public PresenterCamera() {
        mediator = Mediator.getInstance();
        panoramaPreviewActivity = (ActivityImagePreview) mediator.getActivityImagePreview();
        model = ModelImagePreview.getInstance();
    }



}

package com.example.panorama.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.panorama.Mediator;
import com.example.panorama.model.database.CustomTag;
import com.example.panorama.model.IModelPanoramaPreview;
import com.example.panorama.model.ModelPanoramaPreview;
import com.example.panorama.model.database.PanoramicImage;
import com.example.panorama.view.ActivityPanoramaPreview;

import java.util.ArrayList;
import java.util.List;


public class PresenterMainActivity implements IPresenterMainActivity {

    private Mediator mediator;
    private ActivityPanoramaPreview panoramaPreviewActivity;
    private IModelPanoramaPreview model;


    public PresenterMainActivity() {
        mediator = Mediator.getInstance();
        panoramaPreviewActivity = (ActivityPanoramaPreview) mediator.getActivityPanoramaPreview();
        model = ModelPanoramaPreview.getInstance();
    }



}

package com.example.panorama.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.panorama.Mediator;
import com.example.panorama.R;
import com.example.panorama.model.database.PanoramicImage;
import com.example.panorama.view.adapters.AdapterImages;

import java.io.File;
import java.util.Collections;
import java.util.List;


/**
 * Created by IvanGlez on 14/03/2018.
 */

public class ActivityImageList extends AppCompatActivity implements IActivityImageList {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton cameraBtn;
    private Toolbar toolbar;
    private Mediator mediator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        mediator = Mediator.getInstance();
        mediator.setImageListActivity(this);

        mRecyclerView = findViewById(R.id.lista);

        cameraBtn = findViewById(R.id.floatingCameraBtn);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediator.launchActivity(ActivityMain.class, ActivityImageList.this, null);
            }
        });


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        showImages();
    }

    @Override
    public void goToTagsActivity(String imagePath){
        Bundle extras = new Bundle();
        extras.putString("imagePath", imagePath);
        mediator.launchActivity(ActivityUploadImage.class, ActivityImageList.this, extras);
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAdapter != null) {
            showImages();
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        if(mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showImages(){
        mediator.getPresenterImageList().showImages();
    }

    @Override
    public void setAdapter(List<PanoramicImage> images){
        mAdapter = new AdapterImages(images);
        mRecyclerView.setAdapter(mAdapter);
    }


}





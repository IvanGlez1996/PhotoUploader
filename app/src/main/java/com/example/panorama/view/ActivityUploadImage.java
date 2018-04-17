package com.example.panorama.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.panorama.Mediator;
import com.example.panorama.R;
import com.example.panorama.model.database.CustomTag;
import com.example.panorama.presenter.IPresenterUploadImage;
import com.example.panorama.view.adapters.AdapterTags;
import com.example.panorama.view.dialogs.DialogAddTag;
import com.example.panorama.view.dialogs.DialogUploadImage;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.util.List;


/**
 * Created by IvanGlez on 14/03/2018.
 */

public class ActivityUploadImage extends AppCompatActivity implements IActivityUploadImage {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton addTagBtn;
    private VrPanoramaView mVrPanoramaView;
    private Toolbar toolbar;
    private Mediator mediator;
    private ImageView uploadBtn;
    private TextView infoText;
    private String imagePath;

    private IPresenterUploadImage presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        mediator = Mediator.getInstance();
        mediator.setActivityUploadImage(this);

        presenter = mediator.getPresenterUploadImage();

        infoText = findViewById(R.id.infoText);

        Intent i = getIntent();
        imagePath = i.getStringExtra("imagePath");

        mRecyclerView = findViewById(R.id.lista);

        addTagBtn = findViewById(R.id.floatingAddButton);
        addTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTagAlert();
            }
        });


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        uploadBtn = findViewById(R.id.upload_icon);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUploadDialog();
            }
        });


        mVrPanoramaView = findViewById(R.id.pano_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        presenter.loadPhotoSphere(imagePath);

        showCustomTags(imagePath);

    }

    @Override
    public void showAddTagAlert() {
        DialogAddTag cdd = new DialogAddTag(ActivityUploadImage.this);
        cdd.show();
    }

    @Override
    public void showUploadDialog(){
        DialogUploadImage dialog = new DialogUploadImage(ActivityUploadImage.this);
        dialog.show();
    }

    @Override
    public void addNewTag(String tagText) {
        presenter.addNewTag(tagText, imagePath);
    }

    @Override
    public void notifyAdapterDataSetChanged(){
        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onPause() {
        mVrPanoramaView.pauseRendering();
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mVrPanoramaView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVrPanoramaView.shutdown();
        mediator.removePresenterUploadImage();
    }

    @Override
    public String getImagePath(){
        return imagePath;
    }

    @Override
    public void setAdapter(List<CustomTag> tags){
        mAdapter = new AdapterTags(tags);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showCustomTags(String imagePath){
        presenter.showCustomTags(imagePath);
    }

    @Override
    public void showImage(Bitmap myBitmap){
        VrPanoramaView.Options options = new VrPanoramaView.Options();
        options.inputType = VrPanoramaView.Options.TYPE_MONO;
        mVrPanoramaView.loadImageFromBitmap(myBitmap, options);
    }

    @Override
    public void setInfoTextVisible(boolean visible){
        if(visible == true){
            infoText.setVisibility(View.VISIBLE);
        } else {
            infoText.setVisibility(View.GONE);
        }
    }

}





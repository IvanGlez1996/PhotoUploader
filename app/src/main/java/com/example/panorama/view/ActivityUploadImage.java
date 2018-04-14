package com.example.panorama.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.panorama.Mediator;
import com.example.panorama.R;
import com.example.panorama.model.database.CustomTag;
import com.example.panorama.model.database.PanoramicImage;
import com.example.panorama.view.adapters.Adapter;
import com.example.panorama.view.dialogs.DialogAddTag;
import com.example.panorama.view.dialogs.DialogUploadImage;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.File;
import java.util.List;


/**
 * Created by IvanGlez on 14/03/2018.
 */

public class ActivityUploadImage extends AppCompatActivity implements IActivityUploadImage {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<CustomTag> datos;
    private FloatingActionButton addTagBtn;
    private VrPanoramaView mVrPanoramaView;
    private Toolbar toolbar;
    private Mediator mediator;
    private ImageView uploadBtn;
    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        mediator = Mediator.getInstance();
        mediator.setActivityUploadImage(this);

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

        datos = getImageTagsFromDatabase(imagePath);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Adapter(datos);
        mRecyclerView.setAdapter(mAdapter);

        loadPhotoSphere();
    }

    public List<CustomTag> getImageTagsFromDatabase(String imagePath) {
        return mediator.getPresenterUploadImage().getImageTagsFromDatabase(imagePath);
    }

    public void showAddTagAlert() {
        DialogAddTag cdd = new DialogAddTag(ActivityUploadImage.this);
        cdd.show();
    }

    public void showUploadDialog(){
        DialogUploadImage dialog = new DialogUploadImage(ActivityUploadImage.this);
        dialog.show();
    }

    public void addNewTag(String tag) {
        CustomTag customTag = new CustomTag(tag, imagePath);
        mediator.getPresenterUploadImage().saveTagIntoDatabase(customTag.getCustomTagId(), imagePath, tag);
        mAdapter.notifyDataSetChanged();

    }

    private void loadPhotoSphere() {
        //This could take a while. Should do on a background thread, but fine for current example
        VrPanoramaView.Options options = new VrPanoramaView.Options();

        File imgFile = new File(imagePath);

        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());


            options.inputType = VrPanoramaView.Options.TYPE_MONO;
            mVrPanoramaView.loadImageFromBitmap(myBitmap, options);
        } else {
            Log.d("Error", "La imagen no existe");
        }
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
        mVrPanoramaView.shutdown();
        super.onDestroy();
    }

    public PanoramicImage getImage(){
        return mediator.getPresenterUploadImage().getImage(imagePath);
    }


}





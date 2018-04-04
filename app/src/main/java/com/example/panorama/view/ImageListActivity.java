package com.example.panorama.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.panorama.Mediator;
import com.example.panorama.R;
import com.example.panorama.model.CustomTag;
import com.example.panorama.model.PanoramicImage;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by IvanGlez on 14/03/2018.
 */

public class ImageListActivity extends AppCompatActivity implements IImageListActivity{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<PanoramicImage> datos;
    private FloatingActionButton cameraBtn;
    private Toolbar toolbar;
    private Mediator mediator;
    private ImageView uploadBtn;
    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        mediator = Mediator.getInstance();
        mediator.setImageListActivity(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.lista);

        datos = mediator.getPresenter().getImages();

        cameraBtn = (FloatingActionButton) findViewById(R.id.floatingCameraBtn);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ImageListActivity.this, MainActivity.class);
                ImageListActivity.this.startActivity(i);
            }
        });


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        uploadBtn = findViewById(R.id.upload_icon);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUploadDialog();
            }
        });




        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        Collections.reverse(datos);
        mAdapter = new AdapterImages(datos);
        mRecyclerView.setAdapter(mAdapter);
    }


    public void showUploadDialog(){
        UploadDialog dialog = new UploadDialog(ImageListActivity.this);
        dialog.show();
    }

    @Override
    public void goToTagsActivity(String imagePath){
        Bundle extras = new Bundle();
        extras.putString("imagePath", imagePath);
        Intent i = new Intent(ImageListActivity.this, TagsActivity.class);
        if (extras != null)
            i.putExtras(extras);
        ImageListActivity.this.startActivity(i);
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}





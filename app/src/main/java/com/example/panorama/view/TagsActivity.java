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
import com.example.panorama.model.PanoramicImage;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by IvanGlez on 14/03/2018.
 */

public class TagsActivity extends AppCompatActivity implements ITagsActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> datos;
    private FloatingActionButton addTagBtn;
    private VrPanoramaView mVrPanoramaView;
    private Toolbar toolbar;
    private Mediator mediator;
    private ImageView uploadBtn;
    private ArrayList<String> sensorTags;
    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);

        mediator = Mediator.getInstance();
        mediator.setTagsActivity(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.lista);

        datos = new ArrayList<>();

        addTagBtn = (FloatingActionButton) findViewById(R.id.floatingAddButton);
        addTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTagAlert();
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


        mVrPanoramaView = (VrPanoramaView) findViewById(R.id.pano_view);

        loadPhotoSphere();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new Adapter(datos);
        mRecyclerView.setAdapter(mAdapter);
    }





    public void showAddTagAlert() {
        /*AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Add a new tag");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Tag name");
        alert.setView(input);
        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputText = input.getText().toString();
                datos.add(inputText);
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAdapter.notifyDataSetChanged();
            }
        });
        alert.show();*/

        AddTagDialog cdd = new AddTagDialog(TagsActivity.this);
        cdd.show();
    }

    public void showUploadDialog(){
        UploadDialog dialog = new UploadDialog(TagsActivity.this);
        dialog.show();
    }

    public void addNewTag(String tag) {
        datos.add(tag);
        sensorTags.add(tag);
        mAdapter.notifyDataSetChanged();
    }

    private void loadPhotoSphere() {
        //This could take a while. Should do on a background thread, but fine for current example
        VrPanoramaView.Options options = new VrPanoramaView.Options();


        Intent i = getIntent();
        imagePath = i.getStringExtra("imagePath");
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
        return mediator.getPresenter().getImage(imagePath);
    }


}





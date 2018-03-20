package com.example.panorama.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.panorama.R;
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
//    private ImageView imagePreview;
    private VrPanoramaView mVrPanoramaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tags_activty);
        mRecyclerView = (RecyclerView) findViewById(R.id.lista);

        datos = new ArrayList<>();

        addTagBtn = (FloatingActionButton) findViewById(R.id.floatingAddButton);
        addTagBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                showAddTagAlert();
            }
        });

        mVrPanoramaView = (VrPanoramaView) findViewById(R.id.pano_view);

        loadPhotoSphere();

        /*imagePreview = (ImageView) findViewById(R.id.imageView);
        Intent i = getIntent();
        String fileName = i.getStringExtra("fileName");
        File imgFile = new  File(fileName);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            imagePreview.setImageBitmap(myBitmap);

        }*/

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
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
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
        alert.show();
    }

    private void loadPhotoSphere() {
        //This could take a while. Should do on a background thread, but fine for current example
        VrPanoramaView.Options options = new VrPanoramaView.Options();


        Intent i = getIntent();
        String fileName = i.getStringExtra("fileName");
        File imgFile = new  File(fileName);

        if(imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());


                options.inputType = VrPanoramaView.Options.TYPE_MONO;
                mVrPanoramaView.loadImageFromBitmap(myBitmap, options);
        }else{
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
}



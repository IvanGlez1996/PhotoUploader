package com.example.panorama.view.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.panorama.Mediator;
import com.example.panorama.R;
import com.example.panorama.model.database.PanoramicImage;

import java.io.File;
import java.util.List;


/**
 * Created by IvanGlez on 14/03/2018.
 */

public class AdapterImages extends RecyclerView.Adapter<AdapterImages.ViewHolder> {
    private List<PanoramicImage> mDataset;
    private AdapterView.OnItemClickListener mItemClickListener;
    private Mediator mediator;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case


        public TextView location, date;
        public ImageView image;
        public CardView cardView;


        public ViewHolder(View v) {
            super(v);

            cardView = v.findViewById(R.id.cv);
            image = v.findViewById(R.id.imageView);
            location = v.findViewById(R.id.location);
            date = v.findViewById(R.id.date);

            cardView.setOnClickListener(this);
            v.setOnClickListener(this);


            mediator = Mediator.getInstance();
        }

        @Override
        public void onClick(View v) {
            //Log.d("View: ", v.toString());
            //Toast.makeText(v.getContext(), mTextViewTitle.getText() + " position = " + getPosition(), Toast.LENGTH_SHORT).show();
            if (v.equals(cardView)) {
                String imagePath = mDataset.get(getAdapterPosition()).getPath();
                Log.d("ImagePath: ", imagePath);
                mediator.getActivityImageList().goToTagsActivity(imagePath);
            }
        }



    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterImages(List<PanoramicImage> myDataset) {
        mDataset = myDataset;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public AdapterImages.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_imageslist_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder((RelativeLayout) v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        File imgFile = new  File(mDataset.get(position).getPath());

        if(imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            holder.image.setImageBitmap(myBitmap);
        }

        holder.location.setText(mDataset.get(position).getZone());
        holder.date.setText(mDataset.get(position).getDate());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}



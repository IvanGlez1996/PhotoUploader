package com.example.panorama.view.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.panorama.R;

/**
 * Created by IvanGlez on 29/03/2018.
 */

public class DialogGPSSettings extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button settings, cancel;
    public TextView title;

    public DialogGPSSettings(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_gps_settings);
        title = findViewById(R.id.title);
        settings = findViewById(R.id.settings_btn);
        cancel = findViewById(R.id.cancel_btn);
        settings.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_btn:
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                c.startActivity(intent);
                dismiss();
                break;
            case R.id.cancel_btn:
                dismiss();
                c.finish();
                break;
            default:
                break;
        }
        dismiss();
    }
}

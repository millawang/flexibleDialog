package com.fia.mia.flexibledialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.fia.mia.flexibledialog.flexibleviewer.FlexibleDialogBuilder;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button Demobtn = (Button)findViewById(R.id.dialog_demo);
        Demobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog customButton = new FlexibleDialogBuilder(MainActivity.this, R.style.customAlertDialog, FlexibleDialogBuilder.ALERT_DIALOG_TEST);
                customButton.setCancelable(true);
                customButton.show();
            }
        });

    }


}

package com.fia.mia.flexibledialog.flexibleviewer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fia.mia.flexibledialog.R;

/**
 * Created by milla.wang on 8/1/15.
 */
public class DialogDemoView extends LinearLayout implements View.OnClickListener {

    private Context mCxt;
    FlexibleDialogBuilder.AlertDialogListener mListener;

    public DialogDemoView(Context context) {
        super(context);
        mCxt = context;
        init();
    }

    public DialogDemoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCxt = context;
        init();
    }

    public void setAlertDialogListener(FlexibleDialogBuilder.AlertDialogListener listener){
        this.mListener = listener;
    }


    private void init() {
        LayoutInflater li = LayoutInflater.from(getContext());
        li.inflate(R.layout.dialog_demo, this);
        Button btn1 = (Button)findViewById(R.id.close);

        btn1.setOnClickListener(this);
     }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                if(mListener != null){
                    mListener.daligDismiss();
                }
                break;

            default:
                break;
        }
    }
}
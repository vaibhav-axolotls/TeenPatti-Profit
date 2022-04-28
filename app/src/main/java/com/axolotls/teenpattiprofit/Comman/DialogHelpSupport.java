package com.axolotls.teenpattiprofit.Comman;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;

import com.axolotls.teenpattiprofit.R;

public class DialogHelpSupport {

    Context context;
    public DialogHelpSupport(Context context) {
        this.context = context;
    }

    public interface DealerInterface{

        void onClick(int pos);

    }

    public void showHelpDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(context,
                android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        dialog.setContentView(R.layout.support_layout);
        dialog.setTitle("Title...");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ((ImageView)dialog.findViewById(R.id.imgclosetop)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }



}

package com.axolotls.teenpattiprofit.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.axolotls.teenpattiprofit.R;

public class WithdrawRules extends AppCompatActivity {
    ImageView imgclosetop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdrawal_rules_layout);

        imgclosetop = (ImageView) findViewById(R.id.imgclosetop);
        imgclosetop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), RedeemActivity.class);
//                startActivity(i);
                finish();
            }
        });
    }
}

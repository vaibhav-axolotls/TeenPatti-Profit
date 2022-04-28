package com.axolotls.teenpattiprofit.RedeemCoins;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.axolotls.teenpattiprofit.Activity.WithdrawRules;
import com.axolotls.teenpattiprofit.BaseActivity;
import com.axolotls.teenpattiprofit.R;
import com.axolotls.teenpattiprofit.SampleClasses.Const;
import com.axolotls.teenpattiprofit.Utils.Variables;
import com.axolotls.teenpattiprofit.model.ChipsBuyModel;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RedeemActivity extends BaseActivity {
    private static final String MY_PREFS_NAME = "Login_data";
    //ImageView img_back;
    ArrayList<ChipsBuyModel> historyModelArrayList;
    RedeemAdapter historyAdapter;
    RecyclerView rec_history;
    ProgressDialog progressDialog;
    LinearLayout linear_no_history;
    ImageView imgback,imaprofile;
    Context context;
    TextView tvRedeemWallet,txtwallet,txtproname;
    Button btnWithdrawRules;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);
        // img_back=findViewById(R.id.img_back);
        context = this;
        rec_history = findViewById(R.id.rec_history);
        linear_no_history = findViewById(R.id.linear_no_history);
        imgback = findViewById(R.id.imgback);

        txtproname= findViewById(R.id.txtproname);
        txtwallet= findViewById(R.id.txtwallet);
        imaprofile= findViewById(R.id.imaprofile);
        tvRedeemWallet= findViewById(R.id.tvRedeemWallet);

        btnWithdrawRules = (Button) findViewById(R.id.btnWithdrawRules);
        btnWithdrawRules.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), WithdrawRules.class);
                startActivity(i);
//                Toast.makeText(getApplicationContext(),"Button Presses",Toast.LENGTH_SHORT).show();
//                LayoutInflater inflater = LayoutInflater
//                        .from(getApplicationContext());
//                View view = inflater.inflate(R.layout.withdrawal_rules_layout, null);
//                linear_no_history.addView(view);
            }
        });

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        Picasso.with(context).load(Const.IMGAE_PATH + prefs.getString("profile_pic","")).into(imaprofile);
        txtproname.setText(prefs.getString("name",""));
        txtwallet.setText("" + prefs.getString("wallet",""));
        tvRedeemWallet.setText("Amount can be Redeem "+ Variables.CURRENCY_SYMBOL + prefs.getString("winning_wallet",""));

        getChipsList();

        rec_history.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    public void getChipsList() {
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.GET_Redeem_List,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RedeemActivity",""+Const.GET_Redeem_List+" \n"+response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");
                            if (code.equals("200")) {
                                progressDialog.dismiss();
                                JSONArray jsonArray = jsonObject.getJSONArray("List");
                                historyModelArrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    ChipsBuyModel model = new ChipsBuyModel();
                                    model.setId(jsonObject1.getString("id"));
                                    model.title = jsonObject1.getString("title");
                                    model.Image = jsonObject1.getString("img");
                                    model.setProname(jsonObject1.getString("coin"));
                                    model.setAmount(jsonObject1.getString("amount"));
                                    // model.setTicket_id(jsonObject1.getString("desc"));

                                    historyModelArrayList.add(model);
                                }

                                historyAdapter = new RedeemAdapter(RedeemActivity.this, historyModelArrayList);
                                rec_history.setAdapter(historyAdapter);
                            } else {
                                linear_no_history.setVisibility(View.VISIBLE);
//                                 Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("token", Const.TOKEN);
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                HashMap<String, String> params = new HashMap<>();
                params.put("token", prefs.getString("token", ""));
                params.put("user_id", prefs.getString("user_id", ""));
                //params.put("user_id", SharedPref.getVal(HistoryActivity.this,SharedPref.id));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RedeemActivity.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

}
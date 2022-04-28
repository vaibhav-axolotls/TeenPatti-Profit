package com.axolotls.teenpattiprofit.Comman;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.axolotls.teenpattiprofit.Activity.Homepage;
import com.axolotls.teenpattiprofit.R;
import com.axolotls.teenpattiprofit.SampleClasses.Const;
import com.axolotls.teenpattiprofit.Utils.Funtions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DialogReport {

    Context context;
    public DialogReport(Context context) {
        this.context = context;
    }

    public interface DealerInterface{

        void onClick(int pos);

    }

    public void showReportDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_send_report);
        dialog.setTitle("Title...");

        TextView tv_heading = dialog.findViewById(R.id.tv_heading);

        tv_heading.setText("Report isssue!");

        final EditText edtReportDecriction = (EditText) dialog.findViewById(R.id.edtReportDecriction);

        dialog.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Funtions.checkisStringValid(Funtions.getStringFromEdit(edtReportDecriction)))
                {
                    Funtions.showToast(context,"Please enter report description.");
                    return;
                }

                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.bt_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void UserTermsAndCondition(final WebView webview, final Dialog dialog, final String tag) {


        final RelativeLayout rlt_progress = dialog.findViewById(R.id.rlt_progress);
        rlt_progress.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Const.TERMS_CONDITION,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");


                            if (code.equalsIgnoreCase("200")) {


                                JSONObject jsonObject1 = jsonObject.getJSONObject("setting");

                                String data = "";
                            } else {


                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                        rlt_progress.setVisibility(View.GONE);



                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  progressDialog.dismiss();
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                rlt_progress.setVisibility(View.GONE);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences prefs = context.getSharedPreferences(Homepage.MY_PREFS_NAME, MODE_PRIVATE);
                params.put("user_id", prefs.getString("user_id", ""));
                params.put("token", prefs.getString("token", ""));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("token", Const.TOKEN);
                return headers;
            }
        };

        Volley.newRequestQueue(context).add(stringRequest);

    }



}

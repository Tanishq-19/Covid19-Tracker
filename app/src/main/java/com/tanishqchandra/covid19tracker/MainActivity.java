package com.tanishqchandra.covid19tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public String url;
    String confirmed;
    String inc_confirmed;
    String recovered;
    String inc_recovered;
    String deceased;
    String inc_deceased;
    String active;
    String totalTests;
    String totalTestsCopy;
    String oldTests;
    String testsInt;
    String date;
    public static int confirmation = 0;
    private long backPressTime;
    private Toast backToast;
    TextView textView_confirmed, textView_inc_confirmed, textView_recovered, textView_inc_recovered, textView_deceased,textView_inc_deceased, textView_active, textView_inc_active, textView_tested, textView_inc_tested, textView_date, textView_time;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_confirmed = findViewById(R.id.confirmed);
        textView_inc_confirmed = findViewById(R.id.inc_confirmed);
        textView_deceased = findViewById(R.id.deceased);
        textView_inc_deceased = findViewById(R.id.inc_deseased);
        textView_recovered = findViewById(R.id.recovered);
        textView_inc_recovered = findViewById(R.id.inc_recovered);
        textView_active = findViewById(R.id.active);
        textView_inc_active = findViewById(R.id.inc_active);
        textView_tested = findViewById(R.id.tested);
        textView_inc_tested = findViewById(R.id.inc_tested);
        textView_date = findViewById(R.id.date);
        textView_time = findViewById(R.id.time);

        //textView_confirmed.setText("0000");
        showProgressDialog();
        fetchData();


    }

    public void fetchData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.covid19india.org/data.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("statewise");
                    JSONObject statewise = jsonArray.getJSONObject(0);
                    confirmed = statewise.getString("confirmed");
                    active = statewise.getString("active");
                    date = statewise.getString("lastupdatedtime");
                    recovered = statewise.getString("recovered");
                    deceased = statewise.getString("deaths");
                    inc_confirmed = statewise.getString("deltaconfirmed");
                    inc_deceased = statewise.getString("deltadeaths");
                    inc_recovered = statewise.getString("deltarecovered");

                    if (!date.isEmpty()) {
                        Runnable progressRunnable = new Runnable() {

                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run() {
                                progressDialog.cancel();

                                int confirmedInt = Integer.parseInt(confirmed);
                                confirmed = NumberFormat.getInstance().format(confirmedInt);
                                textView_confirmed.setText(confirmed);
                                int newConfirmedInt = Integer.parseInt(inc_confirmed);
                                inc_confirmed = NumberFormat.getInstance().format(newConfirmedInt);
                                textView_inc_confirmed.setText("+" + inc_confirmed);
                                textView_deceased.setText(deceased);
                                textView_inc_deceased.setText("+" + inc_deceased);
                                textView_recovered.setText(recovered);
                                textView_inc_recovered.setText("+" + inc_recovered);
                                textView_active.setText(active);



                                String dateFormat = formatDate(date, 1);
                                textView_date.setText(dateFormat);

                                String timeFormat = formatDate(date, 2);
                                textView_time.setText(timeFormat);

                                fetchTests();
                            }
                        };

                        Handler pdCanceller = new Handler();
                        pdCanceller.postDelayed(progressRunnable, 1000);
                        confirmation = 1;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void fetchTests() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiUrl = "https://api.covid19india.org/data.json";
        JsonObjectRequest jsonObjectRequestTests = new JsonObjectRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("tested");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject statewise = jsonArray.getJSONObject(i);
                        totalTests = statewise.getString("totalsamplestested");
                    }

                    for (int i = 0; i < jsonArray.length() - 1; i++) {
                        JSONObject statewise = jsonArray.getJSONObject(i);
                        oldTests = statewise.getString("totalsamplestested");
                    }
                    if (totalTests.isEmpty()) {
                        for (int i = 0; i < jsonArray.length() - 1; i++) {
                            JSONObject statewise = jsonArray.getJSONObject(i);
                            totalTests = statewise.getString("totalsamplestested");
                        }
                        totalTestsCopy = totalTests;
                        //testsInt = Integer.parseInt(totalTests);
                        totalTests = NumberFormat.getInstance().format(testsInt);
                        textView_tested.setText(totalTests);


                        for (int i = 0; i < jsonArray.length() - 2; i++) {
                            JSONObject statewise = jsonArray.getJSONObject(i);
                            oldTests = statewise.getString("totalsamplestested");
                        }
                        int testsNew = (Integer.parseInt(totalTestsCopy)) - (Integer.parseInt(oldTests));
                        textView_inc_tested.setText("[+" + NumberFormat.getInstance().format(testsNew) + "]");

                    } else {
                        totalTestsCopy = totalTests;
                        //testsInt = Integer.parseInt(totalTests);
                        //totalTests = NumberFormat.getInstance().format(testsInt);
                        textView_tested.setText(totalTests);

                        if (oldTests.isEmpty()) {
                            for (int i = 0; i < jsonArray.length() - 2; i++) {
                                JSONObject statewise = jsonArray.getJSONObject(i);
                                oldTests = statewise.getString("totalsamplestested");
                            }
                        }
                        long testsNew = (Integer.parseInt(totalTestsCopy)) - (Integer.parseInt(oldTests));
                        textView_inc_tested.setText("+" + NumberFormat.getInstance().format(testsNew));


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequestTests);
    }

    public String formatDate(String date, int testCase) {
        Date mDate = null;
        String dateFormat;
        try {
            mDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US).parse(date);
            if (testCase == 0) {
                dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.US).format(mDate);
                return dateFormat;
            } else if (testCase == 1) {
                dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US).format(mDate);
                return dateFormat;
            } else if (testCase == 2) {
                dateFormat = new SimpleDateFormat("hh:mm a", Locale.US).format(mDate);
                return dateFormat;
            } else {
                Log.d("error", "Wrong input! Choose from 0 to 2");
                return "Error";
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.info) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void openStatewise(View view){
        Intent intent = new Intent(this, Statewise.class);
        startActivity(intent);
    }

    public void openWorldwise(View view){
        Intent intent = new Intent(this, Worldwise.class);
        startActivity(intent);
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                if (confirmation != 1) {
                    progressDialog.cancel();
                    Toast.makeText(MainActivity.this, "Internet slow/not available", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 8000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (backPressTime + 800 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressTime = System.currentTimeMillis();
    }
}
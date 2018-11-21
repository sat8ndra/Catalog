package com.hanselandpetal.catalog.catalog;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hanselandpetal.catalog.catalog.model.Flower;
import com.hanselandpetal.catalog.catalog.parsers.FlowerJSONParser;


import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ListActivity {
    public static final String PHOTOS_BASE_URL = "http://services.hanselandpetal.com/photos/";
    public static final String ENDPOINT = "http://services.hanselandpetal.com";
    TextView output;
    ProgressBar pb;

    String status;

    List<Flower> flowerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);



    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_do_task) {
            if (isOnline()) {
                requestData("http://services.hanselandpetal.com/secure/flowers.json");
            } else {
                Toast.makeText(this,"Network isn't available",Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    private void requestData(String uri) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .build();
        FlowersAPI api = adapter.create(FlowersAPI.class);

        api.getFeed(new Callback<List<Flower>>() {
            @Override
            public void success(List<Flower> flowers, Response response) {
                flowerList = flowers;
                updateDisplay();
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
    }

    protected void updateDisplay() {
        FlowerAdapter adapter = new FlowerAdapter(this, R.layout.item_flower, flowerList);
        setListAdapter(adapter);
    }

    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            if (netInfo.getType() != ConnectivityManager.TYPE_WIFI){
                Toast.makeText(this, "This app doesn't work without wifi", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        } else {
            return false;
        }

    }


}

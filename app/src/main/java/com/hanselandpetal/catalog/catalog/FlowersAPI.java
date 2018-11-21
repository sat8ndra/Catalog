package com.hanselandpetal.catalog.catalog;

import com.hanselandpetal.catalog.catalog.model.Flower;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by satyendra on 2/2/2018.
 */

public interface FlowersAPI {

    @GET("/feeds/flowers.json")
    public void getFeed(Callback<List<Flower>> response);
}

package com.hanselandpetal.catalog.catalog.parsers;

import com.hanselandpetal.catalog.catalog.model.Flower;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by satyendra on 1/18/2018.
 */

public class FlowerJSONParser {

    public static List<Flower> parseFeed(String content){
        try {

            JSONArray ar = new JSONArray(content);
            List<Flower> flowerList = new ArrayList<>();

            for (int i = 0; i < ar.length(); i++) {
                JSONObject obj = ar.getJSONObject(i);
                Flower flower = new Flower();

                flower.setProductId(obj.getInt("productId"));
                flower.setName(obj.getString("name"));
                flower.setCategory(obj.getString("category"));
                flower.setInstructions(obj.getString("instructions"));
                flower.setPhoto(obj.getString("photo"));
                flower.setPrice(obj.getDouble("price"));

                flowerList.add(flower);
            }
            return flowerList;
        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}

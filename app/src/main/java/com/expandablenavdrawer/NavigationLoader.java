package com.expandablenavdrawer;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivek on 17/04/16.
 */
public class NavigationLoader extends AsyncTaskLoader<ArrayList<Categories>> {

    public NavigationLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Categories> loadInBackground() {
        ArrayList<Categories> arrayList = new ArrayList<>();
        String categoriesJson = readJSOnFromAssets(getContext(),  "nav_list.json");
        if (categoriesJson != null && !categoriesJson.isEmpty()) {
            arrayList = convertToObject(categoriesJson, new TypeToken<List<Categories>>() {
            }.getType());
        }
        return arrayList;
    }
    public static String readJSOnFromAssets(Context context, String assetName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(assetName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            return json;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public static <T> T convertToObject(String responseString, Type typeOfT) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(responseString, typeOfT);
    }
}

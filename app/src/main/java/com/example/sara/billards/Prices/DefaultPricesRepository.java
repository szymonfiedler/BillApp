package com.example.sara.billards.Prices;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.example.sara.billards.MyJSONArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sara on 10.04.2018.
 */

public class DefaultPricesRepository implements PricesRepository{
    private static final String TAG ="DefaultPricesRepository" ;
    private static DefaultPricesRepository singletonInstanceForPrices;
    private final RequestQueue requestQueue;
    private final String PricesUrl;


    private DefaultPricesRepository(RequestQueue requestQueue,
                                          String PricesUrl) {
        this.requestQueue = requestQueue;
        this.PricesUrl = PricesUrl;
    }
    public static synchronized void createSingletonInstanceForPrices(RequestQueue requestQueue, String PricesUrl) {
        if (singletonInstanceForPrices == null) {
            Log.i(TAG, "Creating new instance DefaultPricesRepository");
            singletonInstanceForPrices = new DefaultPricesRepository(requestQueue, PricesUrl);
        }
    }
    public static DefaultPricesRepository getInstance() {
        if (singletonInstanceForPrices == null) {
            throw new RuntimeException("Instance of DefaultBookedTablesRepository is not ready.");
        }
        return singletonInstanceForPrices;
    }



    private AllPrices toAllPrices(JSONObject response) {
        try {
            return new AllPrices(
                    response.getInt("id_table"),
                    response.getInt("week"),
                    response.getInt("weekend"),
                    response.getInt("week_aft"));
        } catch (JSONException e) {
            throw new RuntimeException("Unexpected response from server", e);
        }
    }
    @Override
    public void getPrices(Consumer2<Set<AllPrices>> allPricesResponseHandler,
                                Response.ErrorListener errorListener) {
        final MyJSONArrayRequest jsonRequest = new MyJSONArrayRequest(Request.Method
                .GET, PricesUrl,
                new JSONArray(),
                jsonArray -> {
                    Log.i(TAG, "Got all prices: " + jsonArray);
                    Set<AllPrices> allPricess = new HashSet<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject allPricesAsJson = null;
                        try {
                            allPricesAsJson = jsonArray.getJSONObject(i);
                            AllPrices allprices = toAllPrices(allPricesAsJson);
                            allPricess.add(allprices);
                        } catch (JSONException e) {
                            Log.i(TAG, "Could transform json to allPrices: " + e.getMessage());
                        }
                    }
                    allPricesResponseHandler.accept(allPricess);
                },
                error -> Log.e(TAG, "Got error response: " + error));

        Log.i(TAG, "Sending GET request to " + PricesUrl);
        requestQueue.add(jsonRequest);
    }


}

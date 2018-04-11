package com.example.sara.billards.booktable;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sara.billards.MyJSONArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class DefaultBookedTablesRepository implements BookedTablesRepository {
    private static final String TAG = "BookedTablesRepository";
    private static DefaultBookedTablesRepository singletonInstance;
    private final RequestQueue requestQueue;
    private final String targetUrl;

    private DefaultBookedTablesRepository(RequestQueue requestQueue,
                                          String targetUrl) {
        this.requestQueue = requestQueue;
        this.targetUrl = targetUrl;
    }

    public static synchronized void createSingletonInstance(RequestQueue requestQueue, String targetUrl) {
        if (singletonInstance == null) {
            Log.i(TAG, "Creating new instance DefaultBookedTablesRepository");
            singletonInstance = new DefaultBookedTablesRepository(requestQueue, targetUrl);
        }
    }

    public static DefaultBookedTablesRepository getInstance() {
        if (singletonInstance == null) {
            throw new RuntimeException("Instance of DefaultBookedTablesRepository is not ready.");
        }
        return singletonInstance;
    }

    @Override
    public void bookTable(TableOrder tableOrder,
                          final Consumer<BookedTable> bookedTableResponseHandler,
                          Response.ErrorListener errorListener) {
        JSONObject requestAsJson = toJson(tableOrder);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, targetUrl, requestAsJson, response -> {
            Log.i(TAG, "Received booked table event: " + response);
            BookedTable bookedResponse = toBookedTable(response);
            Log.i(TAG, "Trasformed to BookedTable: " + bookedResponse);
            bookedTableResponseHandler.accept(bookedResponse);
        }, errorListener);

        Log.i(TAG, "Sending POST request with payload" + requestAsJson);
        requestQueue.add(request);
    }

    private JSONObject toJson(TableOrder tableOrder) {

        JSONObject sampleObject = null;
        try {
            sampleObject = new JSONObject();
            sampleObject = sampleObject.put("ID_USER", tableOrder.getUserId());
            sampleObject = sampleObject.put("ID_TABLE", tableOrder.getTableId());
            sampleObject = sampleObject.put("CHARGE", tableOrder.getPrice());
            sampleObject = sampleObject.put("HOUR_FROM", tableOrder.getStartHour());
            sampleObject = sampleObject.put("HOUR_TO", tableOrder.getEndHour());
            sampleObject = sampleObject.put("DATE", tableOrder.getDate());
        } catch (JSONException exception) {
            throw new RuntimeException("Problem with creating book table request.");

        }
        return sampleObject;
    }

    private BookedTable toBookedTable(JSONObject response) {
        try {
            return new BookedTable(
                    response.getInt("ID_RES"),
                    response.getInt("ID_USER"),
                    response.getInt("ID_TABLE"),
                    response.getString("DATE"),
                    response.getInt("HOUR_FROM"),
                    response.getInt("HOUR_TO"),
                    response.getInt("CHARGE"));
        } catch (JSONException e) {
            throw new RuntimeException("Unexpected response from server", e);
        }
    }

    @Override
    public void getBookedTables(Consumer<Set<BookedTable>> bookedTablesResponseHandler,
                                Response.ErrorListener errorListener) {
        final MyJSONArrayRequest jsonRequest = new MyJSONArrayRequest(Request.Method
                .GET, targetUrl,
                new JSONArray(),
                jsonArray -> {
                    Log.i(TAG, "Got booked tables: " + jsonArray);
                    Set<BookedTable> bookedTables = new HashSet<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject bookedTableAsJson = null;
                        try {
                            bookedTableAsJson = jsonArray.getJSONObject(i);
                            BookedTable bookedTable = toBookedTable(bookedTableAsJson);
                            bookedTables.add(bookedTable);
                        } catch (JSONException e) {
                            Log.i(TAG, "Could transform json to BookedTable: " + e.getMessage());
                        }
                    }
                    bookedTablesResponseHandler.accept(bookedTables);
                },
                error -> Log.e(TAG, "Got error response: " + error));

        Log.i(TAG, "Sending GET request to " + targetUrl);
        requestQueue.add(jsonRequest);
    }

    public void gettables(Consumer<Set<BookedTable>> bookedTablesResponseHandler,
                          Response.ErrorListener errorListener) {
        final MyJSONArrayRequest jsonRequest = new MyJSONArrayRequest(Request.Method
                .GET, "http://ec2-18-217-215-212.us-east-2.compute.amazonaws.com:8000/testsite/api4/",
                new JSONArray(),
                jsonArray -> {
                    Log.i(TAG, "Got booked tables: " + jsonArray);
                    Set<BookedTable> bookedTables = new HashSet<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject bookedTableAsJson = null;
                        try {
                            bookedTableAsJson = jsonArray.getJSONObject(i);
                            BookedTable bookedTable = toBookedTable(bookedTableAsJson);
                            bookedTables.add(bookedTable);
                        } catch (JSONException e) {
                            Log.i(TAG, "Could transform json to BookedTable: " + e.getMessage());
                        }
                    }
                    bookedTablesResponseHandler.accept(bookedTables);
                },
                error -> Log.e(TAG, "Got error response: " + error));

        Log.i(TAG, "Sending GET request to " + targetUrl);
        requestQueue.add(jsonRequest);
    }
    @Override
    public void getBookedTablesAtDate(String date,
                                      int tableId,
                                      Consumer<Set<BookedTable>> bookedTablesResponseHandler,
                                      Response.ErrorListener errorListener) {
        getBookedTables(
                bookedTables -> bookedTablesResponseHandler.accept(filterBookedTableAtDate(date, tableId, bookedTables)),
                errorListener);
    }

    private Set<BookedTable> filterBookedTableAtDate(String date, int tableId, Set<BookedTable> allBookedTables) {
        Set<BookedTable> bookedTableAtDate = new HashSet<>();
        for (BookedTable bookedTable: allBookedTables) {
            if (bookedTable.getDate().equals(date) && bookedTable.getTableId() == tableId) {
                bookedTableAtDate.add(bookedTable);
            }
        }
        return bookedTableAtDate;
    }
}

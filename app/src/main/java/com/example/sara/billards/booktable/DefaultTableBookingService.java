package com.example.sara.billards.booktable;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class DefaultTableBookingService implements TableBookingService {
    private static final String TAG = "TableBookingService";
    private static DefaultTableBookingService singletonInstance;
    private final RequestQueue requestQueue;
    private final String targetUrl;

    private DefaultTableBookingService(RequestQueue requestQueue,
                                       String targetUrl) {
        this.requestQueue = requestQueue;
        this.targetUrl = targetUrl;
    }

    public static synchronized void createSingletonInstance(RequestQueue requestQueue, String targetUrl) {
        if (singletonInstance == null) {
            Log.i(TAG, "Creating new instance DefaultTableBookingService");
            singletonInstance = new DefaultTableBookingService(requestQueue, targetUrl);
        }
    }

    public static DefaultTableBookingService getInstance() {
        if (singletonInstance == null) {
            throw new RuntimeException("Instance of DefaultTableBookingService is not ready.");
        }
        return singletonInstance;
    }

    @Override
    public void bookTable(BookTableRequest bookTableRequest,
                          final BookedTableResponseHandler bookedTableResponseHandler,
                          Response.ErrorListener errorListener) {
        JSONObject requestAsJson = toJson(bookTableRequest);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, targetUrl, requestAsJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "Received booked table event: " + response);
                TableBookedResponse bookedResponse = toTableBookedResponse(response);
                Log.i(TAG, "Trasformed to TableBookedResponse: " + bookedResponse);
                bookedTableResponseHandler.handle(bookedResponse);
            }
        }, errorListener);

        Log.i(TAG, "Sending POST request with payload" + requestAsJson);
        requestQueue.add(request);
    }

    private JSONObject toJson(BookTableRequest bookTableRequest) {

        JSONObject sampleObject = null;
        try {
            sampleObject = new JSONObject();
            sampleObject = sampleObject.put("ID_USER", bookTableRequest.getUserId());
            sampleObject = sampleObject.put("ID_TABLE", bookTableRequest.getTableId());
            sampleObject = sampleObject.put("CHARGE", bookTableRequest.getPrice());
            sampleObject = sampleObject.put("HOUR_FROM", bookTableRequest.getStartHour());
            sampleObject = sampleObject.put("HOUR_TO", bookTableRequest.getEndHour());
            sampleObject = sampleObject.put("DATE", bookTableRequest.getDate());
        } catch (JSONException exception) {
            throw new RuntimeException("Problem with creating book table request.");

        }
        return sampleObject;
    }

    private TableBookedResponse toTableBookedResponse(JSONObject response) {
        try {
            return new TableBookedResponse(
                    response.getInt("ID_RES"),
                    response.getInt("ID_TABLE"),
                    response.getString("DATE"),
                    response.getInt("HOUR_FROM"),
                    response.getInt("HOUR_TO"),
                    response.getInt("CHARGE"));
        } catch (JSONException e) {
            throw new RuntimeException("Unexpected response from server", e);
        }
    }
}

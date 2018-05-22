package com.example.billard.billards.JSONRequests;

/**
 * Created by sara on 27.03.2018.
 */

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
import java.util.HashMap;
import java.util.Map;

public class MyJSONArrayRequest extends JsonArrayRequest {

    public MyJSONArrayRequest(int method, String url, JSONArray jsonRequest,
                              Response.Listener<JSONArray> listener,
                              Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        // here you can write a custom retry policy
        return super.getRetryPolicy();
    }
}


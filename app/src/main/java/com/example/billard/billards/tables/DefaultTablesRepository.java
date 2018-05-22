package com.example.billard.billards.tables;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.example.billard.billards.JSONRequests.MyJSONArrayRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashSet;
import java.util.Set;

public class DefaultTablesRepository implements TablesRepository {
    public static int[] num_of_seats;
    public static String[] id_type;
    public static int sizee;
    private static final String TAG = "DefaultTablesRepository";
    private static com.example.billard.billards.tables.DefaultTablesRepository singletonInstance;
    private final RequestQueue requestQueue;
    private final String targetUrl;

    private DefaultTablesRepository(RequestQueue requestQueue,
                                    String targetUrl) {
        this.requestQueue = requestQueue;
        this.targetUrl = targetUrl;
    }

    public static synchronized void createSingletonInstance(RequestQueue requestQueue, String targetUrl) {
        if (singletonInstance == null) {
            Log.i(TAG, "Creating new instance DefaultTablesRepository");
            singletonInstance = new com.example.billard.billards.tables.DefaultTablesRepository(requestQueue, targetUrl);
        }
    }

    public static com.example.billard.billards.tables.DefaultTablesRepository getInstance() {
        if (singletonInstance == null) {
            throw new RuntimeException("Instance of DefaultTablesRepository is not ready.");
        }
        return singletonInstance;
    }




    private Tables toTables(JSONObject response) {
        try {
            return new Tables(
                    response.getInt("ID_TABLE"),
                    response.getInt("NUM_OF_SEATS"),
                    response.getInt("ID_TYPE"));
        } catch (JSONException e) {
            throw new RuntimeException("Unexpected response from server", e);
        }
    }

    @Override
    public void getTables(com.example.billard.billards.tables.Consumer<Set<Tables>> TablesResponseHandler, Response.ErrorListener errorListener) {
        final MyJSONArrayRequest jsonRequest = new MyJSONArrayRequest(Request.Method
                .GET, targetUrl,
                new JSONArray(),
                jsonArray -> {
                    Log.i(TAG, "Got tables: " + jsonArray);
                    Set<Tables> tables = new HashSet<>();
                    num_of_seats = new int[jsonArray.length()];
                    id_type = new String[jsonArray.length()];
                    sizee = jsonArray.length();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject tablesAsJson = null;
                        try {
                            tablesAsJson = jsonArray.getJSONObject(i);
                            num_of_seats[i] = tablesAsJson.getInt("NUM_OF_SEATS");
                            if (tablesAsJson.getInt("ID_TYPE") == 1) {
                                id_type[i] = "Snooker";
                            } else if (tablesAsJson.getInt("ID_TYPE") == 2) {
                                id_type[i] = "Pool";
                            } else if (tablesAsJson.getInt("ID_TYPE") == 3) {
                                id_type[i] = "Karambol";
                            }


                            Tables table = toTables(tablesAsJson);
                            tables.add(table);
                        } catch (JSONException e) {
                            Log.i(TAG, "Could transform json to Table: " + e.getMessage());
                        }
                    }
                    TablesResponseHandler.accept(tables);
                },
                error -> Log.e(TAG, "Got error response: " + error));

        Log.i(TAG, "Sending GET request to " + targetUrl);
        requestQueue.add(jsonRequest);
    }
}

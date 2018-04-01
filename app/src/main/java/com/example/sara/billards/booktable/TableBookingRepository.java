package com.example.sara.billards.booktable;

import com.android.volley.Response;

import java.util.Set;

public interface TableBookingRepository {
    void bookTable(BookTableRequest bookTableRequest,
                   Consumer<TableBookedResponse> bookedTableResponseHandler,
                   Response.ErrorListener errorListener);

    void getBookedTables(String date,
                         Consumer<Set<TableBookedResponse>> bookedTablesResponseHandler,
                         Response.ErrorListener errorListener);
}

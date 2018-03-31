package com.example.sara.billards.booktable;

import com.android.volley.Response;

public interface TableBookingService {
    void bookTable(BookTableRequest bookTableRequest,
                   BookedTableResponseHandler bookedTableResponseHandler,
                   Response.ErrorListener errorListener);
}

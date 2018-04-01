package com.example.sara.billards.booktable;

import com.android.volley.Response;

import java.util.Set;

public interface BookedTablesRepository {
    void bookTable(TableOrder tableOrder,
                   Consumer<BookedTable> bookedTableResponseHandler,
                   Response.ErrorListener errorListener);

    void getBookedTables(Consumer<Set<BookedTable>> bookedTablesResponseHandler,
                         Response.ErrorListener errorListener);

    void getBookedTablesAtDate(String date,
                               int tableId,
                               Consumer<Set<BookedTable>> bookedTablesResponseHandler,
                               Response.ErrorListener errorListener);
}

package com.example.sara.billards.tables;


import com.android.volley.Response;
import com.example.sara.billards.tables.Tables;
import com.example.sara.billards.tables.Consumer;


import java.util.Set;

public interface TablesRepository {
    void getTables(Consumer<Set<Tables>> TablesResponseHandler,
                   Response.ErrorListener errorListener);


}

package com.example.billard.billards.tables;


import com.android.volley.Response;
import com.example.billard.billards.tables.Tables;
import com.example.billard.billards.tables.Consumer;


import java.util.Set;

public interface TablesRepository {
    void getTables(Consumer<Set<Tables>> TablesResponseHandler,
                   Response.ErrorListener errorListener);


}

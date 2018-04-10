package com.example.sara.billards.Prices;

import com.android.volley.Response;
import com.example.sara.billards.booktable.BookedTable;
import com.example.sara.billards.booktable.Consumer;

import java.util.Set;

/**
 * Created by sara on 10.04.2018.
 */

public interface PricesRepository {

    void getPrices(Consumer2<Set<AllPrices>> allPricesResponseHandler,
                   Response.ErrorListener errorListener);
}

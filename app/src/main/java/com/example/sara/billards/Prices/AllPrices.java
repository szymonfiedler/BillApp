package com.example.sara.billards.Prices;

import com.android.volley.Response;
import com.example.sara.billards.booktable.BookedTable;
import com.example.sara.billards.booktable.Consumer;

import java.util.Objects;
import java.util.Set;

/**
 * Created by sara on 10.04.2018.
 */

public final class AllPrices  {
    private final int id_table;
    private final int week;
    private final int weekend;
    private final int week_aft;

    public AllPrices(int id_table, int week, int weekend, int week_aft)
    {
        this.id_table=id_table;
        this.week=week;
        this.weekend=weekend;
        this.week_aft=week_aft;
    }
    public int getId_table()
    {
        return id_table;
    }
    public int getWeekend() {
        return weekend;
    }
    public int getWeek() {
        return week;
    }

    public int getWeek_aft() {
        return week_aft;
    }
    @Override
    public String toString() {
        return "Prices {" +
                "id_table=" + id_table +
                ", week=" + week +
                ", weekend=" + weekend +
                ", week_aft=" + week_aft +
                '}';
    }


    @Override
    public int hashCode() {
        return Objects.hash(id_table, week, weekend, week_aft);
    }
}

package com.example.sara.billards.tables;

import java.util.Objects;

public final class Tables {
    private final int id_table;
    private final int num_of_seats;
    private final int id_type;


    public Tables(int id_table,
                  int num_of_seats,
                  int id_type) {

        this.id_table = id_table;
        this.num_of_seats = num_of_seats;
        this.id_type = id_type;

    }

    public int id_table() {
        return id_table;
    }

    public int getTableId() {
        return num_of_seats;
    }

    public int id_type() {
        return id_type;
    }


    @Override
    public String toString() {
        return "Table{" +
                "id_table=" + id_table +
                ", num_of_seats=" + num_of_seats +
                ", id_type=" + id_type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com.example.sara.billards.tables.Tables that = (com.example.sara.billards.tables.Tables) o;
        return id_table == that.id_table &&
                num_of_seats == that.num_of_seats &&
                id_type == that.id_type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_table, num_of_seats, id_type);
    }
}
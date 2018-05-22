package com.example.billard.billards.tables;

import static java.util.Objects.*;

public final class TableOrd {
    private final int id_table;
    private final int num_of_seats;
    private final int id_type;


    public TableOrd(int id_table,
                    int num_of_seats,
                    int id_type) {

        this.id_table = id_table;
        this.num_of_seats = num_of_seats;
        this.id_type = id_type;

    }

    public static Builder builder() {
        return new Builder();
    }

    public int getId_table() {
        return id_table;
    }

    public int getNum_of_seats() {
        return num_of_seats;
    }

    public int getId_type() {
        return id_type;
    }


    @Override
    public String toString() {
        return "TableOrd{" +
                "Id_table=" + id_table +
                ", Num_of_seats=" + num_of_seats +
                ", Id_type=" + id_type +
                '}';
    }

    public static class Builder {
        private Integer id_table;
        private Integer num_of_seats;
        private Integer id_type;

        private Builder() {
        }

        public Builder withId_table(int id_table) {
            this.id_table = id_table;
            return this;
        }

        public Builder withNum_of_seat(int num_of_seat) {
            this.num_of_seats = num_of_seat;
            return this;
        }

        public Builder withId_type(int id_type) {
            this.id_type = id_type;
            return this;
        }


        public TableOrd build() {
            requireNonNull(id_table, "Id_table cannot be null");
            requireNonNull(num_of_seats, "num_of_seats cannot be null");
            requireNonNull(id_type, "id_type cannot be null");

            return new TableOrd(
                    id_table,
                    num_of_seats,
                    id_type
            );
        }
    }
}

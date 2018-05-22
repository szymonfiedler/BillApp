package com.example.billard.billards.booktable;

import static java.util.Objects.*;

public final class TableOrder {
    private final int tableId;
    private final int userId;
    private final Double price;
    private final String startHour;
    private final String endHour;
    private final String date;

    private TableOrder(int tableId, int userId, Double price, String startHour, String endHour, String date) {
        this.tableId = tableId;
        this.userId = userId;
        this.price = price;
        this.startHour = startHour;
        this.endHour = endHour;
        this.date = date;
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getTableId() {
        return tableId;
    }

    public int getUserId() {
        return userId;
    }

    public Double getPrice() {
        return price;
    }

    public String getStartHour() {
        return startHour;
    }

    public String getDate() {
        return date;
    }

    public String getEndHour() {
        return endHour;
    }

    @Override
    public String toString() {
        return "TableOrder{" +
                "tableId=" + tableId +
                ", userId=" + userId +
                ", price=" + price +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", date='" + date + '\'' +
                '}';
    }

    public static class Builder {
        private Integer tableId;
        private Integer userId;
        private Double price;
        private String startHour;
        private String endHour;
        private String date;

        private Builder() {
        }

        public Builder withTableId(int tableId) {
            this.tableId = tableId;
            return this;
        }

        public Builder withUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder withPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder withStartHour(String startHour) {
            this.startHour = startHour;
            return this;
        }

        public Builder withEndHour(String endHour) {
            this.endHour = endHour;
            return this;
        }

        public Builder withDate(String date) {
            this.date = date;
            return this;
        }

        public TableOrder build() {
            requireNonNull(tableId, "tableId cannot be null");
            requireNonNull(userId, "userId cannot be null");
            requireNonNull(price, "price cannot be null");
            requireNonNull(startHour, "startHour cannot be null");
            requireNonNull(date, "date cannot be null");

            return new TableOrder(
                    tableId,
                    userId,
                    price,
                    startHour,
                    endHour,
                    date
            );
        }
    }
}

package com.example.sara.billards.booktable;


import java.util.Objects;

public final class BookedTable {

    private final int reservationId;
    private final int userId;
    private final int tableId;
    private final String date;
    private final int startHour;
    private final int endHour;
    private final int charge;

    public BookedTable(int reservationId,
                       int userId,
                       int tableId,
                       String date,
                       int startHour,
                       int endHour,
                       int charge) {


        this.reservationId = reservationId;
        this.userId = userId;
        this.tableId = tableId;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.charge = charge;
    }


    public int getReservationId() {
        return reservationId;
    }

    public int getUserId() {
        return userId;
    }
    public int getTableId() {
        return tableId;
    }

    public String getDate() {
        return date;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getCharge() {
        return charge;
    }

    @Override
    public String toString() {
        return "BookedTable{" +
                "reservationId=" + reservationId +
                "userId=" + userId +
                ", tableId=" + tableId +
                ", date='" + date + '\'' +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                ", charge=" + charge +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookedTable that = (BookedTable) o;
        return reservationId == that.reservationId &&
                userId == that.userId &&
                tableId == that.tableId &&
                startHour == that.startHour &&
                endHour == that.endHour &&
                charge == that.charge &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservationId, userId, tableId, date, startHour, endHour, charge);
    }
}

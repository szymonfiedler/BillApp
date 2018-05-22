package com.example.billard.billards.tables;

public interface Consumer<T> {
    void accept(T bookedResponses);
}


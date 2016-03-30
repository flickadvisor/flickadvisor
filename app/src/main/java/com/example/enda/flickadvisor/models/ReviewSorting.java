package com.example.enda.flickadvisor.models;

/**
 * Created by enda on 30/03/16.
 */
// http://stackoverflow.com/a/7996474/2324937
public enum ReviewSorting {
    NewestFirst(0),
    TopRatedFirst(1);

    int value;

    ReviewSorting(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ReviewSorting fromInt(int i) {
        for (ReviewSorting r: ReviewSorting.values()) {
            if (r.getValue() == i) {
                return r;
            }
        }
        return null;
    }
}

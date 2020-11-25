package ru.mospolytech.lab1;

import java.util.HashMap;

public class Cities {
    public String id;
    public String city;

    public Cities(String id, String city) {
        this.city = city;
        this.id = id;
    }

    @Override
    public String toString() {
        return city;
    }
}
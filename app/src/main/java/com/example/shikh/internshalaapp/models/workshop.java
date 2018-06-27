package com.example.shikh.internshalaapp.models;

/**
 * Created by shikh on 27-06-2018.
 */

public class workshop {
    int id;
    String name;

    public workshop(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


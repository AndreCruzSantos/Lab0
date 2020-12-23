package com.pa.proj2020.adts;

public class Interests {
    private int id;
    private String name;

    public Interests(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

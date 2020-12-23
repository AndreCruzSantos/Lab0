package com.pa.proj2020.adts.graph;

public class User {
    private int id;
    private String name;

    public User(int id,String name){
        this.id=id;
        this.name=name;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return "ID: " + getId() + " Name: " + getName();
    }
}

package com.pa.proj2020.adts;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents an User
 * @author André Santos, João Bandeira, Vítor Nunes
 */
public class User {
    private int id;
    private String name;
    private List<Interests> list;

    /**
     * Constructor of the Class User
     * @param id
     * @param name
     */
    public User(int id,String name){
        this.id=id;
        this.name=name;
        list = new ArrayList<>();
    }

    /**
     * Returns the user's id
     * @return id
     */
    public int getId(){
        return id;
    }

    /**
     * Returns the user's name
     * @return name
     */
    public String getName(){
        return name;
    }

    public void addInterests(Interests i){
        if(i != null){
            list.add(i);
        }
    }

    /**
     * Returns the user's information in a String
     * @return String
     */
    @Override
    public String toString(){
        return "ID: " + getId() + " Name: " + getName();
    }

}

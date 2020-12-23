package com.pa.proj2020.adts;

import java.util.Collection;

public class MainTest {
    public static void main(String[] args){
        DigraphList dl = new DigraphList();
        User u = new User(1, "Vítor Nunes");
        dl.insertVertex(u);
        System.out.println(dl.toString());
    }
}

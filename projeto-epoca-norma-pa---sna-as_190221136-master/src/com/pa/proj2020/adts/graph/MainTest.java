package com.pa.proj2020.adts.graph;

import java.util.ArrayList;
import java.util.Collection;

public class MainTest {
    public static void main(String[] args){
        SocialNetwork sn = new SocialNetwork();
        sn.insertUser("inputFiles//user_names.csv");
        sn.insertRelationship("inputFiles//relationships.csv");
        Collection<Edge<Relationship,User>> list = sn.getDigraph().edges();
        for(Edge<Relationship,User> e : list){
            Vertex<User>[] v = e.vertices();
            System.out.println(v[0] + "    " + v[1]);
        }
    }
}

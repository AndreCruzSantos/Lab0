package com.pa.proj2020.adts.graph;

import java.util.List;
import java.util.Map;

public class SocialNetwork {
    private DigraphList<User,Relationship> digraph;

    public SocialNetwork(){
        digraph = new DigraphList<>();
    }

    public DigraphList<User,Relationship> getDigraph() {
        return digraph;
    }

    public void insertUser(String file){
        Map<Integer,String> map = ReadFiles.readUser(file);
        for(Integer i : map.keySet()){
            digraph.insertVertex(new User(i,map.get(i)));
        }
    }

    public void insertRelationship(String file){
        Map<Integer, List<Integer>> map = ReadFiles.readRelationship(file);
        for(Integer i : map.keySet()){
            for(Integer j : map.get(i)){
                digraph.insertEdge(getUser(i),getUser(j),new Relationship());
            }
        }
    }

    public User getUser(int i){
        for(Vertex<User> u : digraph.vertices()){
            if(u.element().getId() == i){
                return u.element();
            }
        }
        return null;
    }
}

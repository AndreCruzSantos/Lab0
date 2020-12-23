package com.pa.proj2020.adts;

import java.util.List;
import java.util.Map;

/**
 * Class that represents the social network
 * @author André Santos, João Bandeira, Vítor Nunes
 */
public class SocialNetwork {
    private DigraphList<User,Relationship> digraph;

    /**
     * Constructor of the Class SocialNetwork
     */
    public SocialNetwork(){
        digraph = new DigraphList<>();
    }

    /**
     * Returns the digraph
     * @return digraph
     */
    public DigraphList<User,Relationship> getDigraph() {
        return digraph;
    }

    /**
     * Reads a file and inserts the users in the digraph
     * @param file
     */
    public void insertUser(String file){
        Map<Integer,String> map = ReadFiles.readNames(file);
        for(Integer i : map.keySet()){
            digraph.insertVertex(new User(i,map.get(i)));
        }
    }

    /**
     * Reads a file and inserts the relationships in the digraph
     * @param file
     */
    public void insertRelationship(String file){
        Map<Integer, List<Integer>> map = ReadFiles.readRelations(file);
        for(Integer i : map.keySet()){
            for(Integer j : map.get(i)){
                digraph.insertEdge(getUser(i),getUser(j),new Relationship());
            }
        }
    }

    /**
     * Receives an id and returns the user related to it
     * @param i
     * @return User
     */
    public User getUser(int i){
        for(Vertex<User> u : digraph.vertices()){
            if(u.element().getId() == i){
                return u.element();
            }
        }
        return null;
    }

    public void insertInterests(String file){
        Map<Integer,String> map = ReadFiles.readNames(file);
        for(Integer i : map.keySet()){

        }
    }
}

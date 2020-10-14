package com.pa;

public class Lab0Main {
    public static void main(String[] args) {
        Person p = new Person(1,"Andre");
        Person p1 = new Person(2,"Diogo");
        Person p2 = new Person(3,"Tiago");
        Person p3 = new Person(4,"Bernardo");

        Group g = new Group(p);
        g.addMember(p1);
        g.addMember(p2);
        g.addMember(p3);
        g.addMember(p3);
        System.out.println(g.toString());

        g.deleteMember(p);
    }
}

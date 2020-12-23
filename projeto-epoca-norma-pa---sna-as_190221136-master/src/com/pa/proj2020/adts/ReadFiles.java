package com.pa.proj2020.adts;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ReadFiles {

    public static Map<Integer,String> readNames(String file){
        Map<Integer,String> map = new HashMap<>();
        try{
            File f = new File(file);
            Scanner scanner = new Scanner(f);
            while(scanner.hasNextLine()){
                String[] s = scanner.nextLine().split(";");
                map.put(Integer.parseInt(s[0]),s[1]);
            }
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        return map;
    }

    public static Map<Integer,List<Integer>> readRelations(String file){
        Map<Integer,List<Integer>> map = new HashMap<>();
        try{
            File f = new File(file);
            Scanner scanner = new Scanner(f);
            while(scanner.hasNextLine()){
                String[] s = scanner.nextLine().split(";");
                List<Integer> list = new ArrayList<>();
                for(int i=1 ; i<s.length ;i++){
                    list.add(Integer.parseInt(s[i]));
                }
                map.put(Integer.parseInt(s[0]),list);
            }
        } catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        return map;
    }
}
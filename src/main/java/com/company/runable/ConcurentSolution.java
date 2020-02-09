package com.company.runable;

import java.util.*;


public class ConcurentSolution{
    private static Map<Object,Object> concurentMap = new HashMap <>();


    private static boolean canStart = true;

    public static void setCanStart(boolean canStart) {
        ConcurentSolution.canStart = canStart;
    }

    public static boolean canStart(Object key, Object value){
        return concurentMap.get(key)!=value && canStart;
    }

    public static void start(Object key, Object value){
        if (!concurentMap.keySet().contains(key) && canStart) {
            concurentMap.put(key,value);
        }
    }

    public static void remove(Object key){
        concurentMap.remove(key);
    }

}

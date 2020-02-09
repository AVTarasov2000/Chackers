package com.company.game;


import com.company.checkers.Checker;
import com.company.field.Cell;
import com.company.field.Field;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

@lombok.Data
public class Player{

    @JsonProperty(value = "name")
    private String name;
    @JsonIgnore
    private ArrayList<Checker> checkers;
    @JsonIgnore
    private Player next;
    @JsonProperty(value = "directionsToGo")
    private ArrayList<Integer> directionsToGo = new ArrayList <>();

    public Player(String name, ArrayList <Checker> checkers, Integer... directions){
        checkers.forEach(checker -> checker.setPlayer(this));
        this.name=name;
        this.checkers=checkers;
        directionsToGo.addAll(Arrays.asList(directions));
    }
    public Player (String name, ArrayList <Checker> checkers, ArrayList directions){
        checkers.forEach(checker -> checker.setPlayer(this));
        this.name=name;
        this.checkers=checkers;
        directionsToGo = directions;
    }


    public ArrayList <Integer> getDirectionsToGo() {
        return directionsToGo;
    }

    public Player getNext() {
        return next;
    }

    public void setNext(Player next) {
        this.next = next;
    }

    public String getName(){
        return name;
    }

    public void addChecker(Checker checker){
        checkers.add(checker);
    }

    public boolean contains(Checker checker){
        return checkers.contains(checker);
    }

    public void remove(Checker checker){
        checkers.remove(checker);
    }

    public boolean canGo(int i){
        return directionsToGo.contains(i);
    }



    ArrayList<Checker> cantMove(){
        int count = 0;
        ArrayList<Checker> result = new ArrayList <>();

        for (Checker c :
                checkers) {
            if (!c.canBitAnything()){
                result.add(c);
                count++;
            }
        }
        if (count==checkers.size()){
            result = new ArrayList <>();
        }
        return result;
    }

    public ArrayList<Checker> canMove(){
        ArrayList<Checker> result = new ArrayList <>();
        if(!canOnlyBit().isEmpty()){
            return result;
        }
        for (Checker ch :
                checkers) {
            if (ch.canGoAnywhere() || ch.canBitAnything()){
                result.add(ch);
            }
        }

        return result;
    }

    public ArrayList<Checker> canOnlyBit(){
        ArrayList<Checker> result = new ArrayList <>();

        for (Checker c :
                checkers) {
            if (c.canBitAnything()){
                result.add(c);
            }
        }
        return result;
    }

    private List<String> step;
    @JsonIgnore
    public List<String> getStep(){
        List list = step;
        step = null;
        return list;
    }
    @JsonIgnore
    public void setStep(String from, String to){
        if(from==null || to == null){
            step = null;
        }
        List<String> list = new LinkedList <>();
        list.add(from);
        list.add(to);
        step = list;
    }

    @JsonIgnore
    public void setStep(){
        step = null;
    }



    static ArrayList<Checker> setPlayer(boolean isPlayerWhite, Field field){
        int size = field.getSize();
        ArrayList <Checker> arr = new ArrayList <>();

        Cell cell;
        int from,to;
        if (isPlayerWhite){
            from=0;
            to=3;
        }else {
            from=size-3;
            to=size;
        }
        for (int i = 0; i < size; i++) {
            for (int j = from; j < to; j++) {
                if ((i + j) % 2 != 0) {
                    cell = field.getField().get(Field.stringKey(i, j));
                    Checker checker = new Checker(cell, isPlayerWhite);
                    cell.setChecker(checker);
                    arr.add(checker);
                }
            }
        }
        return arr;
    }

    public static ArrayList<Checker> setPlayerWithOneChecker(boolean isPlayerWhite, Field field){
        ArrayList <Checker> arr = new ArrayList <>();

        Checker checker;
        if (isPlayerWhite) {
            checker = new Checker(field.getField().get(Field.stringKey(4, 1)), true);
        }
        else {
            checker = new Checker(field.getField().get(Field.stringKey(2, 1)), false);
        }
        arr.add(checker);
        return arr;
    }

    boolean isLose(){
        if (checkers.size()==0){
            return true;
        }

        for (Checker checker:
             checkers) {
            if (checker.canGoAnywhere() || checker.canBitAnything()){
                return false;
            }
        }
        return true;
    }

    public boolean isTransformCellForThisPlayer(Cell cell){
        for (int dir :
                directionsToGo) {
            if (cell.getCellFromDirection(dir) != null) {
                return false;
            }
        }
        return true;
    }

    public int countCheckers(){
        return checkers.size();
    }

    public Player() {
    }
}
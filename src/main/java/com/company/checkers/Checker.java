package com.company.checkers;


import com.company.field.Cell;
import com.company.game.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
public class Checker {

    @JsonIgnore
    private Cell cell;
    private boolean isWhite;
    @JsonIgnore
    Player player;


    public Checker(Cell cell, boolean isWhite){
        this.isWhite=isWhite;
        this.cell=cell;
        cell.setChecker(this);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public boolean isWhite() {
        return isWhite;
    }


    public Cell getCell() {
        return cell;
    }

    void setCell(Cell cell) {
        this.cell = cell;
    }

    public void goTo(Cell cell){
        this.cell.setChecker(null);
        this.cell = cell;
        cell.setChecker(this);
    }

    public boolean canGoTo(Cell cell){
        if(canBitAnything()){
            return false;
        }
        int dir = this.cell.getDirection(cell);
        return this.cell.getCellFromDirection(dir)==cell && player.canGo(dir) && cell.isFree();
    }

    public List<Cell> canGoTo(){

        List<Cell> cells = new ArrayList<>();
        for (int i :
                player.getDirectionsToGo()) {
            Cell cell = this.cell.getCellFromDirection(i);
            if (cell != null
                    && cell.isFree()) {
                cells.add(cell);
            }
        }
        return cells;
    }

    public List<Cell> canBit(){
        List<Cell> result = new ArrayList <>();
        for (int i = 0; i < cell.getCells().size(); i++) {
            ArrayList<Cell> list = cell.getAllByDirection(i);
            if (list.size()>2 && list.get(0)!=null && list.get(1)!=null){
//                Checker checker = list.get(0).getChecker();
//                if(!list.get(0).isFree() && list.get(1).isFree() && !player.contains(checker) && checker!=null){
//                    result.add(checker);
//                }
                if(canBit(list.get(1))){
                    result.add(list.get(0));
                }
            }
        }
       return result;
    }

    public boolean canGoAnywhere(){
            for (int i :
                    player.getDirectionsToGo()) {
                if (cell.getCellFromDirection(i) != null
                        && cell.getCellFromDirection(i).isFree()) {
                    return true;
                }
            }
        return false;
    }

    public boolean canBitAnything(){
        for (int i = 0; i < cell.getCells().size(); i++) {
            ArrayList<Cell> list = cell.getAllByDirection(i);

            if (list.size()>2 && list.get(0)!=null && list.get(1)!=null){
                if(canBit(list.get(1))){
                    return true;
                }
            }
        }
        return false;
    }




    public int directionToBit(Cell cell){
        return this.cell.getDirection(cell);
    }

    public boolean canBit(Cell cell){
        if(cell==null){
            return false;
        }
        int dir = directionToBit(cell);
        return dir >= 0
                && cell.isFree()
                && this.cell.getAllByDirection(dir).indexOf(cell)==1
                && !cell.getCellFromDirection(Cell.reversDirection(dir)).isFree()
                && !player.contains(cell.getCellFromDirection(Cell.reversDirection(dir)).getChecker());
    }


    public Checker getCheckerToBit(Cell cell){
        int dir = this.cell.getDirection(cell);
        return cell.getCellFromDirection(Cell.reversDirection(dir)).getChecker();
    }

    public void bit(Cell cell){
        Checker prev = getCheckerToBit(cell);

        if(cell.isFree() && prev!=null && !player.contains(prev)){
            cell.setChecker(this);
            this.cell.setChecker(null);
            this.cell=cell;
            prev.remove();
        }

    }

    public Player getPlayer() {
        return player;
    }

    public void remove(){
        player.remove(this);
        cell.setChecker(null);
//        cell=null;

    }






    public Queen transformToQueen(){
        Queen queen = new Queen(isWhite, cell);
        player.remove(this);
        player.addChecker(queen);
        queen.setPlayer(player);
        return queen;

    }

    @Override
    public String toString() {
        return player.getName()+":"+cell.toString();
    }

    public Checker() {
    }
}

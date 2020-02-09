package com.company.ui;

import com.company.field.Field;
import com.company.game.Checkers;
import com.company.game.Player;
import com.company.gameSaver.GameSaver;

import java.awt.*;

public class Controller{


    private int width;
    private Checkers checkers;
    private Point from;
    private String winner;

    public void tap(Point e){
        if(from==null){
            from = getCell(e);
            if(!checkers.getTurn().getPlayer().contains(checkers.getField().getChecker(Field.stringKey(from.x, from.y)))){
                from=null;
            }
        }else {
            Point second = getCell(e);
            checkers.getTurn().getPlayer().setStep(Field.stringKey(from.x,from.y), Field.stringKey(second.x,second.y));
            checkers.doStep();
            if(from.x!=second.x && from.y!=second.y) {
                from = null;
            }
        }
        winner = checkers.checkEnd();
    }


    public Point getFrom() {
        return from;
    }

    public Player getTurn(){
        return checkers.getTurn().getPlayer();
    }

    private Point getCell(Point point){
        return new Point(point.x / width, point.y / width);
    }

    public Controller(String firstPlayer,boolean isFirstAI, String secondPlayer, boolean isSecondAI, int width) {
        this.checkers = new Checkers(firstPlayer, isFirstAI,secondPlayer, isSecondAI);
        this.width = width;
    }



    public Field getField(){
        return checkers.getField();
    }

    public Checkers getCheckers() {
        return checkers;
    }

    public Controller(GameSaver gameSaver, int width) {
        checkers=gameSaver.getGame();
        this.width = width;
    }
}

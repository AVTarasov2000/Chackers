
package com.company.game;


import com.company.gameSaver.GameSaver;
import com.company.checkers.Checker;
import com.company.field.Cell;
import com.company.field.Field;
import com.company.gameSaver.Step;
import com.company.runable.RunnablePlayer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.IOException;
import java.util.List;

@lombok.Data
public class Checkers {


    private Field field = new Field();

    private Player blackPlayer;
    private Player whitePlayer;

    @JsonIgnore
    RunnablePlayer rp1;
    @JsonIgnore
    RunnablePlayer rp2;

    private Turn turn;
    @JsonIgnore
    private GameSaver gameSaver;
    private String winner;


    public void saveGame(String path){
        try {
            gameSaver.writeFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startFromSave(String name){
        GameSaver tmp = GameSaver.getFromSave(name);
        field = tmp.getGame().field;
        blackPlayer = tmp.getGame().blackPlayer;
        whitePlayer = tmp.getGame().whitePlayer;
        turn = tmp.getGame().turn;
        gameSaver = tmp.getGame().gameSaver;
    }

    public void previous(){
        Step step = gameSaver.prev();
        if(step==null){
            return;
        }
        Checker checker = step.getBited();
        if(checker!=null){
            checker.getCell().setChecker(checker);
            step.getBited().getPlayer().addChecker(step.getBited());
        }
        step.getTo().getChecker().goTo(step.getFrom());
        turn.switchTurn();
    }

    public void next(){

        Step step = gameSaver.next();
        if(step==null){
            return;
        }
        Checker checker = step.getBited();
        if(checker!=null){
            checker.getCell().setChecker(null);
        }
        step.getFrom().getChecker().goTo(step.getTo());
//        step.getBited().getPlayer().addChecker(step.getBited());
        turn.switchTurn();
    }


    public Checkers(String firstPlayer, String secondPlayer){

        whitePlayer = new Player(firstPlayer,Player.setPlayer(true, field),Cell.UP_LEFT, Cell.UP_RIGHT);
        blackPlayer = new Player(secondPlayer,Player.setPlayer(false, field), Cell.DOWN_LEFT, Cell.DOWN_RIGHT);
        turn = new Turn(whitePlayer,blackPlayer);

        gameSaver = new GameSaver(this);
    }

    public Checkers(String firstPlayer, String secondPlayer, boolean forOneChecker){//для проверки работы
        if (forOneChecker) {

            whitePlayer = new Player(firstPlayer, Player.setPlayerWithOneChecker(true, field), Cell.UP_LEFT, Cell.UP_RIGHT);
            blackPlayer = new Player(secondPlayer, Player.setPlayerWithOneChecker(false, field), Cell.DOWN_LEFT, Cell.DOWN_RIGHT);
            turn = new Turn(whitePlayer, blackPlayer);

            gameSaver = new GameSaver(this);
        }
    }

    public Checkers(String firstPlayer, boolean isFirstAI, String secondPlayer, boolean isSecondAI){//для проверки работы
        if(isFirstAI) {
            whitePlayer = new AIPlayer(firstPlayer, Player.setPlayer(true, field), Cell.UP_LEFT, Cell.UP_RIGHT);
        }else {
            whitePlayer = new Player(firstPlayer, Player.setPlayer(true, field), Cell.UP_LEFT, Cell.UP_RIGHT);
        }
        if(isSecondAI) {
            blackPlayer = new AIPlayer(secondPlayer, Player.setPlayer(false, field), Cell.DOWN_LEFT, Cell.DOWN_RIGHT);
        }else {
            blackPlayer = new Player(secondPlayer, Player.setPlayer(false, field), Cell.DOWN_LEFT, Cell.DOWN_RIGHT);
        }
        turn = new Turn(whitePlayer,blackPlayer);

        gameSaver = new GameSaver(this);

        rp1 = new RunnablePlayer(whitePlayer,this);
        Thread thread1 = new Thread(rp1);
        thread1.start();
        rp2 = new RunnablePlayer(blackPlayer,this);
        Thread thread2 = new Thread(rp2);
        thread2.start();
    }

    public boolean doStep(){
        if(winner !=null){
            return true;
        }
        List<String> step = turn.getPlayer().getStep();
        if (step==null){
            return false;
        }
        doStep(step.get(0),step.get(1));
        winner = checkEnd();
        return false;
    }

    public boolean canDoStep(){
        return turn.getPlayer().getStep()!=null;
    }

    public boolean doStep(String from, String to){


        Cell cellFrom = field.getCell(from);
        Cell cellTo = field.getCell(to);
        if (cellFrom==null || cellTo==null){
            return false;
        }
        Checker checker1 = cellFrom.getChecker();

        if (checker1==null){
            return false;
        }

        if(checker1.canBit(cellTo)){
            Checker toBit = checker1.getCheckerToBit(cellTo);
            gameSaver.add(new Step(cellFrom,cellTo,toBit));
            checker1.bit(cellTo);
            if(!checker1.canBitAnything()) {
                turn.switchTurn();
            }
        } else if (checker1.canGoTo(cellTo) && cellTo.isFree() && turn.getPlayer().canOnlyBit().isEmpty()){
            if(!turn.getPlayer().contains(checker1) || turn.getPlayer().cantMove().contains(checker1)){
                return false;
            }
            gameSaver.add(new Step(cellFrom,cellTo));
            checker1.goTo(cellTo);
            turn.switchTurn();
        }
        return true;
    }


    public String checkEnd(){
        if(turn.getPlayer().isLose()) {
            if (rp1.getPlayer() == turn.getPlayer()) {
                rp1.stop();
            } else {
                rp2.stop();
            }
            turn.removePlayer();
        }
        if(turn.getWinner()!=null){
            try {
                gameSaver.writeFile(whitePlayer.getName()+" "+blackPlayer.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
            rp1.stop();
            rp2.stop();
            return "winner is: "+turn.getPlayer().getName();
        }
        return null;
    }

    public void stop(){
        rp1.stop();
        rp2.stop();
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Turn getTurn() {
        return turn;
    }

    public GameSaver getGameSaver() {
        return gameSaver;
    }

    public Field getField() {
        return field;
    }

    public String getWinner() {
        return winner;
    }

    public Checkers() {
    }
}

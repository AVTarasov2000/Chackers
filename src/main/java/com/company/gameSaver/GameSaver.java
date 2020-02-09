package com.company.gameSaver;

import com.company.game.AIPlayer;
import com.company.game.Checkers;
import com.company.runable.ConcurentSolution;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.io.IOException;
import java.util.*;

@lombok.Data
public class GameSaver {

    @JsonProperty
    private Checkers game;
    @JsonProperty
    private List<Step> steps = new LinkedList<>();
    @JsonIgnore
    private Step now;
    @JsonIgnore
    private int index = 0;
    @JsonIgnore
    private static ObjectMapper mapper = new ObjectMapper();
    @JsonProperty
    private boolean aiFirstPlayer = false;
    @JsonProperty
    private boolean aiSecondPlayer = false;

    public GameSaver() {
    }

    public GameSaver(Checkers game){
        this.game = game;
        aiFirstPlayer = game.getWhitePlayer() instanceof AIPlayer;
        aiSecondPlayer = game.getBlackPlayer() instanceof AIPlayer;
    }



    public static GameSaver getFromSave(String name){
        File file = new File("src/main/resources/"+name+".json");
        GameSaver gs = null;
        try {
            gs = mapper.readValue(file,GameSaver.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Checkers game = new Checkers(gs.game.getWhitePlayer().getName(),gs.isAiFirstPlayer(), gs.game.getBlackPlayer().getName(), gs.isAiSecondPlayer());
        ConcurentSolution.start(game.getField(), game);
        List<Step> steps = gs.steps;

        for (Step step :
                steps) {
            game.doStep(step.from.getName(), step.to.getName());
        }
        ConcurentSolution.remove(game.getField());

        return game.getGameSaver();
    }



    public void writeFile(String name) throws IOException {

        File file = new File("src/main/resources/"+name+".json");
        mapper.writeValue(file,this);

    }

    public void add(Step data){
        for (int i = steps.size()-1; i >=index; i--) {
            steps.remove(i);
        }
        steps.add(data);
        index++;
        now = data;
    }

    public Step prev(){
        if (index<=0){
            return null;
        }
        now = steps.get(index-1);
        index-=1;
        return now;
    }
    public Step next(){
        if (index>=steps.size()){
            return null;
        }
        index+=1;
        now = steps.get(index-1);
        return now;
    }



    public Checkers getGame() {
        return game;
    }


    public boolean isAiFirstPlayer() {
        return aiFirstPlayer;
    }

    public void setAiFirstPlayer(boolean aiFirstPlayer) {
        this.aiFirstPlayer = aiFirstPlayer;
    }

    public boolean isAiSecondPlayer() {
        return aiSecondPlayer;
    }

    public void setAiSecondPlayer(boolean aiSecondPlayer) {
        this.aiSecondPlayer = aiSecondPlayer;
    }

}

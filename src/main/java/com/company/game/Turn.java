package com.company.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@lombok.Data
public class Turn {

    @JsonProperty
    private Player player;
    @JsonProperty
    private ArrayList<Player> players = new ArrayList <>();

    public Player getPlayer() {
        return player;
    }

    void switchTurn(){
       player = player.getNext();
    }

    Turn(Player... players) {
        player = players[0];
        for (int i = 0; i < players.length; i++) {
            this.players.add(players[i]);
            players[i].setNext(players[(i+1)%players.length]);
        }
    }

    public ArrayList <Player> getPlayers() {
        return players;
    }

    void removePlayer(){
        players.remove(player);
        int index = players.indexOf(player)-1;
        if(index==-1){
            index=players.size()-1;
        }
        player=player.getNext();
        players.get(index).setNext(player);
    }

    Player getWinner(){
        if(players.size()==1){
            return player;
        }else {
            return null;
        }
    }

    public Turn() {
    }
}

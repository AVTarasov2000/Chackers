package com.company.gameSaver;

import com.company.checkers.Checker;
import com.company.field.Cell;
import com.company.game.Player;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"from","to","bited"})
public class Step{
    Cell from;
    Cell to;
    Checker bited;
    Player player;

    public Step(Cell from, Cell to, Checker bited) {
        this.from = from;
        this.to = to;
        this.bited = bited;
    }

    public Step(Cell from, Cell to, Checker bited, Player p) {
        this.from = from;
        this.to = to;
        this.bited = bited;
        this.player = p;
    }

    public Step(Cell from, Cell to) {
        this.from = from;
        this.to = to;
    }

    public Cell getFrom() {
        return from;
    }

    public Cell getTo() {
        return to;
    }

    @Override
    public String toString() {
        if(bited!=null) {
            return from.toString() + "-(" + bited.toString()+")->"+to.toString();
        }else {
            return from.toString() + "->" + to;
        }
    }

    public Step() {
    }

    public Checker getBited() {
        return bited;
    }

    public Player getPlayer() {
        return player;
    }
}

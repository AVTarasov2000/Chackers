package com.company.runable;

import com.company.game.Checkers;
import com.company.game.Player;

public class RunnablePlayer implements Runnable{

    private Player player;
    private Checkers game;

    public RunnablePlayer(Player player, Checkers game) {
        this.player = player;
        this.game = game;
    }

    private boolean check;

    @Override
    public void run() {
        check = true;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (check){
            if(game.getTurn().getPlayer() == player && ConcurentSolution.canStart(game.getField(), player)){
                ConcurentSolution.start(game.getField(), player);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                player.setStep();
                check = !game.doStep();
                ConcurentSolution.remove(game.getField());
            }else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop(){
        check=false;
    }

    public Player getPlayer() {
        return player;
    }
}

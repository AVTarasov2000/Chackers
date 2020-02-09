package com.company.runable;

import com.company.game.Checkers;

import java.util.ConcurrentModificationException;

public class RunnableGame implements Runnable{

    private Checkers checkers;

    public RunnableGame(Checkers checkers) {
        this.checkers = checkers;
    }

    boolean check = true;

    @Override
    public void run() {
        check = true;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
            check=false;
        }
        while (check) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                check=false;
            }
            try {
                if (checkers.canDoStep()) {
                    checkers.doStep();
                }
            }catch (ConcurrentModificationException e){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    check=false;
                }
            }
        }
    }

    public void stop(){
        check=false;
    }
}

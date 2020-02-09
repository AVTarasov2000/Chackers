package com.company.runable;

import com.company.ui.FXApp;


public class RunnableField implements Runnable{

    private FXApp app;

    public RunnableField(FXApp app) {
    this.app = app;
    }

    boolean check = true;

    @Override
    public void run() {
        check = true;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (check) {

            if(ConcurentSolution.canStart(app.getControllerField(), app)) {
                ConcurentSolution.start(app.getControllerField(), app);
                app.initField();
                ConcurentSolution.remove(app.getControllerField());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    check = false;
                }
            }else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    check = false;
                }
            }
        }
    }

    public void stop(){
        check=false;
    }

}

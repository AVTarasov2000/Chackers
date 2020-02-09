package com.company;


import com.company.game.Checkers;
import com.company.field.Field;

public class Main{


    // TODO: 12/10/2019  сделать искутвенный интелект √
    // TODO: 12/10/2019  журналирование с возможностью отмены хода √
    // TODO: 12/10/2019  графика




    public static void main(String[] args) {


//        AIPlay();
//        queenGame();
        Checkers sh = new Checkers("mario",true,"boris",true);
        sh.getField().print();
        while (sh.checkEnd()==null){
            sh.doStep();
            sh.getField().print();
            System.out.println();
        }
        System.out.println(sh.checkEnd());


    }

    public static void AIPlay(){

        Checkers sh = new Checkers("mario",true,"boris", true);
        Field field = sh.getField();

        System.out.println(sh.checkEnd());
        System.out.println();
        field.print();
        System.out.println();


        System.out.println(sh.checkEnd());
        sh.doStep();
        System.out.println();
        field.print();
        System.out.println();

        sh.saveGame("lastGame");
        sh.startFromSave("lastGame");
        field=sh.getField();

        System.out.println(sh.checkEnd());
        sh.doStep();
        System.out.println();
        field.print();
        System.out.println();

        System.out.println(sh.checkEnd());
        sh.doStep();
        System.out.println();
        field.print();
        System.out.println();

        System.out.println(sh.checkEnd());
        sh.doStep();
        System.out.println();
        field.print();
        System.out.println();

        System.out.println(sh.checkEnd());
        sh.doStep();
        System.out.println();
        field.print();
        System.out.println();

        System.out.println(sh.checkEnd());
        sh.doStep();
        System.out.println();
        field.print();
        System.out.println();
    }

    public static void queenGame(){



        Checkers sh = new Checkers("mario","boris", true);
        Field field = sh.getField();


        int i = 0;
        System.out.println(sh.checkEnd());
        System.out.println();
        field.print();
        System.out.println();


        System.out.println(i++);//0
        System.out.println(sh.checkEnd());
        sh.doStep("4 1","5 2");
        System.out.println();
        field.print();
        System.out.println();

        System.out.println(i++);//1
        System.out.println(sh.checkEnd());
        sh.doStep("2 1","3 0");
        System.out.println();
        field.print();
        System.out.println();

        System.out.println(i++);//2
        System.out.println(sh.checkEnd());
        sh.doStep("5 2","4 3");
        System.out.println();
        field.print();
        System.out.println();

        System.out.println(i++);//3
        System.out.println(sh.checkEnd());
        sh.doStep("3 0","6 3");
        System.out.println();
        field.print();
        System.out.println();

        System.out.println(i++);//4
        System.out.println(sh.checkEnd());
        sh.doStep("4 3","3 4");
        System.out.println();
        field.print();
        System.out.println();

        sh.previous();
        System.out.println();
        field.print();
        System.out.println();

        sh.previous();
        System.out.println();
        field.print();
        System.out.println();

        sh.previous();
        System.out.println();
        field.print();
        System.out.println();

        sh.next();
        System.out.println();
        field.print();
        System.out.println();

        sh.next();
        System.out.println();
        field.print();
        System.out.println();

        sh.next();
        System.out.println();
        field.print();
        System.out.println();

        System.out.println(i++);//5
        System.out.println(sh.checkEnd());
        sh.doStep("6 3","3 0");
        System.out.println();
        field.print();
        System.out.println();



        sh.saveGame("lastGame");
        sh.startFromSave("lastGame");
        field=sh.getField();


        System.out.println(i++);//6
        System.out.println(sh.checkEnd());
        sh.doStep("3 4","2 5");
        System.out.println();
        field.print();
        System.out.println();

        System.out.println(i++);//7
        System.out.println(sh.checkEnd());
        sh.doStep("3 0","0 3");
        System.out.println();
        field.print();
        System.out.println();

        System.out.println(i++);//8
        System.out.println(sh.checkEnd());
        sh.doStep("2 5","3 6");
        System.out.println();
        field.print();
        System.out.println();

        System.out.println(i++);//9
        System.out.println(sh.checkEnd());
        sh.doStep("0 3","1 2");
        System.out.println();
        field.print();
        System.out.println();

        System.out.println(i);//10
        System.out.println(sh.checkEnd());
        sh.doStep("0 3","4 7");
        System.out.println();
        field.print();
        System.out.println();

        sh.previous();
        System.out.println();
        field.print();
        System.out.println();



        System.out.println(sh.checkEnd());

    }


}
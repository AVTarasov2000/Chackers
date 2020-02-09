package com.company.field;

import com.company.checkers.Checker;
import com.company.checkers.Queen;

public class TransformCell extends Cell{

    @Override
    public void setChecker(Checker checker){
        if (checker instanceof Queen){
            super.setChecker(checker);
        }

        if(checker!=null && checker.getPlayer()!=null && checker.getPlayer().isTransformCellForThisPlayer(this)){
            super.setChecker(checker.transformToQueen());
        }else {
            super.setChecker(checker);
        }
    }
}
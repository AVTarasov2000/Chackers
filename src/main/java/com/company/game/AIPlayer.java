package com.company.game;

import com.company.checkers.Checker;
import com.company.field.Cell;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
public class AIPlayer extends Player{

//    @Override
//    public List<String> getStep() {
//        List<String> steps = new ArrayList <>();
//        List<Checker> canOnlyBit = this.canOnlyBit();
//        if(!canOnlyBit.isEmpty()){
//            Cell cell = canOnlyBit.get(0).getCell();
//            steps.add(cell.getName());
//            Cell cell1 = cell.getChecker().canBit().get(0).getCell();
//            steps.add(cell1.getCellFromDirection(cell.getDirection(cell1)).getName());
//        }else{
//            List<Checker> canGo = this.canMove();
//            if(canGo.isEmpty() || !canGo.get(0).canGoAnywhere()){
//                return null;
//            }
//            steps.add(canGo.get(0).getCell().getName());
//            steps.add(canGo.get(0).canGoTo().get(0).getName());
//        }
//        return steps;
//    }

    @Override
    public void setStep() {
        List<Checker> canOnlyBit = this.canOnlyBit();
        if(!canOnlyBit.isEmpty()){
            Cell cell = canOnlyBit.get(0).getCell();
            Cell cell1 = cell.getChecker().canBit().get(0);//.getCell();
            super.setStep(cell.getName(),cell1.getCellFromDirection(cell.getDirection(cell1)).getName());
        }else{
            List<Checker> canGo = this.canMove();
            if(canGo.isEmpty() || !canGo.get(0).canGoAnywhere()){
                super.setStep(null, null);
            }
            super.setStep(canGo.get(0).getCell().getName(),canGo.get(0).canGoTo().get(0).getName());
        }

    }



    public AIPlayer(String name, ArrayList <Checker> checkers, Integer... directions) {
        super(name, checkers, directions);
    }


}

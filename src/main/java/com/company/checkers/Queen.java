package com.company.checkers;
import com.company.field.Cell;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Checker {

    Queen(boolean isWhite, Cell cell) {
        super(new Cell(), isWhite);
        setCell(cell);
    }

    @Override
    public void goTo(Cell cell){
        getCell().setChecker(null);
        setCell(cell);
        cell.setChecker(this);
    }

    @Override
    public boolean canGoTo(Cell cell){
        int dir = getCell().getDirection(cell);
        ArrayList <Cell> list = getCell().getAllByDirection(dir);

        for (Cell aList : list) {
            if (!aList.isFree()) {
                return false;
            }
            if (aList == cell) {
                return true;
            }
        }
        return false;
    }



    private Cell getNearestCellWithCheckerByDirection(int direction){
        ArrayList <Cell> list = getCell().getAllByDirection(direction);

        for (Cell aList : list) {
            if (!aList.isFree()) {
                return aList;
            }
        }
        return null;
    }

    @Override
    public boolean canGoAnywhere(){
        for (Cell cell :
                getCell().getCells()) {
            if (cell!= null && cell.isFree()) {
                return true;
            }
        }
        return false;
    }

    public List<Cell> canBit(){
        List<Cell> result = new ArrayList <>();
        for (int i = 0; i < getCell().getCells().size(); i++) {
            Cell cell = getNearestCellWithCheckerByDirection(i);
            if(cell!=null && cell.getCellFromDirection(i).isFree() && !player.contains(cell.getChecker())){
                result.add(cell);
            }
        }
        return result;
    }

    @Override
    public boolean canBitAnything(){
        for (int i = 0; i < getCell().getCells().size(); i++) {
//            Cell cell = getNearestCellWithCheckerByDirection(i);
//            if(cell!=null && cell.getCellFromDirection(i)!=null && cell.getCellFromDirection(i).isFree() && !player.contains(cell.getChecker())){
//                return true;
//            }
            for (Cell c :
                    super.getCell().getAllByDirection(i)) {
                if(canBit(c)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int directionToBit(Cell cell){
        int dir = this.getCell().getDirection(cell);

        Cell cellTo = getNearestCellWithCheckerByDirection(dir);
        if (cellTo!=null && cell == cellTo.getCellFromDirection(dir)){
            return dir;
        }
        return -1;
    }

    @Override
    public boolean canBit(Cell cell){
        int dir = directionToBit(cell);
        return directionToBit(cell)>=0
                && cell.isFree()
                && !cell.getCellFromDirection(Cell.reversDirection(dir)).isFree()
                && !player.contains(cell.getCellFromDirection(Cell.reversDirection(dir)).getChecker());
    }

    public List<Cell> canGoTo(){
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Cell cell = this.getCell().getCellFromDirection(i);
            if (cell != null
                    && cell.isFree()) {
                cells.add(cell);
            }
        }
        return cells;
    }

    @Override
    public void bit(Cell cell){
        int direction = directionToBit(cell);

        if (direction==-1){
            return;
        }

        Checker prev = cell.getCellFromDirection(Cell.reversDirection(direction)).getChecker();
        cell.setChecker(this);

        prev.remove();

        getCell().setChecker(null);
        setCell(cell);
    }

}

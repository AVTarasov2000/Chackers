package com.company.field;

import com.company.checkers.Checker;
import com.company.checkers.Queen;
import com.company.game.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

@lombok.Data
public class Field {

    @JsonIgnore
    private Map<String,Cell> field = new HashMap <>();
    @JsonProperty(value = "size")
    private int size = 8;


    public static String stringKey(int x, int y){
        return x+" "+y;
    }

    public Map <String, Cell> getField() {
        return field;
    }

    public Field(){
        setField();
    }

    public Field(int size){
        this.size = size;
        setField();
    }

    public int getSize() {
        return size;
    }

    public Checker getChecker(String cellName){
        if(cellName==null || field.get(cellName)==null){
            return null;
        }
        return field.get(cellName).getChecker();
    }

    public Cell getCell(String cellName){
        return field.get(cellName);
    }

    private void setField(){
        for (int i = 0; i < size; i++) { // создаются все клетки
            for (int j = 0; j < size; j++){
                if((i+j)%2!=0) {
                    if(j==0 || j==size-1){
                        field.put(stringKey(i,j), new TransformCell());
                    }else {
                        field.put(stringKey(i,j), new Cell());
                    }
                }
            }
        }
        Cell cell;
        for (int i = 0; i < size; i++) { // задаются связи в клетках
            for (int j = 0; j < size; j++) {
                if((i+j)%2!=0) {
                    cell = field.get(stringKey(i,j));
                    cell.setName(stringKey(i,j));
                    cell.setAll(field.getOrDefault(stringKey(i-1,j-1), null),//downLeft
                                field.getOrDefault(stringKey(i+1,j-1), null),//downRight
                                field.getOrDefault(stringKey(i-1,j+1), null),//upLeft
                                field.getOrDefault(stringKey(i+1,j+1), null));//upRight
                }
            }
        }
    }

    public void print(){
        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print("."+i);
        }
        System.out.println();
        for (int j = size-1; j >=0; j--) {
            System.out.print(j+": ");
            for (int i = 0; i < size; i++) {
                if((i+j)%2!=0) {
                    int ad = (field.get(stringKey(i,j)).getChecker() instanceof Queen)?2:0;
                    if (j % 2 == 0) {
                        System.out.print(0 + " " + (field.get(stringKey(i,j)).getChecker()
                                !=null?(field.get(i + " " + j).getChecker().isWhite()?1+ad:2+ad):0) + " ");

                    } else {
                        System.out.print((field.get(stringKey(i,j)).getChecker()
                                !=null?(field.get(stringKey(i,j)).getChecker().isWhite()?1+ad:2+ad):0) + " " + 0 + " ");
                    }
                }
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int j = size-1; j >=0; j--) {
            for (int i = 0; i < size; i++) {
                if((i+j)%2!=0) {
                    int ad = (field.get(stringKey(i,j)).getChecker() instanceof Queen)?2:0;
                    if (j % 2 == 0) {
                        sb.append(0 + " ").append(field.get(stringKey(i, j)).getChecker()
                                != null ? (field.get(i + " " + j).getChecker().isWhite() ? 1 + ad : 2 + ad) : 0).append(" ");

                    } else {
                        sb.append(field.get(stringKey(i, j)).getChecker()
                                != null ? (field.get(stringKey(i, j)).getChecker().isWhite() ? 1 + ad : 2 + ad) : 0).append(" ").append(0).append(" ");
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }


}

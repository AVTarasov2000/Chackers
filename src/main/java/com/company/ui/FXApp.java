package com.company.ui;

import com.company.checkers.Checker;
import com.company.checkers.Queen;
import com.company.field.Cell;
import com.company.field.Field;
import com.company.gameSaver.GameSaver;
import com.company.runable.ConcurentSolution;
import com.company.runable.RunnableField;
import com.company.runable.RunnableGame;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.awt.*;

// TODO: 30/11/2019 откат двух убитых фишек сразу
// TODO: 30/11/2019


public class FXApp extends Application {

    Controller controller;


    private int width = 80;
    private GridPane root = new GridPane();
    int size = 640;
    private Canvas field = new Canvas(size,size);
    private Button startButton;
    private TextField text = new TextField();
    private CheckBox isAI = new CheckBox();
    private Button prev = new Button("prev");
    private Button next = new Button("next");
    private TextField firstPlayer = new TextField("First Player");
    private TextField secondPlayer = new TextField("Second Player");
    private Button startFromSaveButton = new Button("start from save");
    private TextField fromSave = new TextField("lastGame");
    private Button saveGameButton = new Button("save");
    private RunnableField runnableField;
    private RunnableGame runnableGame;
    private Button stopGameButton = new Button("stop");

    @Override
    public void start(Stage primaryStage) throws Exception {


        initStartButton();
        field.setOnMouseClicked(mouseEvent -> {
            controller.tap(new Point((int) mouseEvent.getX(),(int) mouseEvent.getY()));
            controller.getCheckers().stop();
            initField();
        });
        VBox box = new VBox();
        box.getChildren().add(startButton);
        box.getChildren().add(startFromSaveButton);
        box.getChildren().add(fromSave);
        box.getChildren().add(text);
        box.getChildren().add(isAI);
        box.getChildren().add(prev);
        box.getChildren().add(next);
        box.getChildren().add(firstPlayer);
        box.getChildren().add(secondPlayer);
        box.getChildren().add(saveGameButton);
        box.getChildren().add(stopGameButton);
        text.setVisible(false);
        prev.setVisible(false);
        next.setVisible(false);
        saveGameButton.setVisible(false);


        root.add(field, 1,0);
        root.add(box,0,0);

        primaryStage.setOnCloseRequest(new EventHandler <WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(runnableField!=null){
                    runnableField.stop();
                }
            }
        });


        primaryStage.setTitle("Checkers");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();



    }



    public void initEnd(){
        GraphicsContext gc = this.field.getGraphicsContext2D();
        String winner = controller.getCheckers().getWinner();
        gc.setFill(Color.WHITE);
        for (int i = 0; i < 500; i++) {
            gc.fillRect(0,300,i,40);
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        }
        gc.setFill(Color.BLACK);
        gc.fillText("winner is"+winner, 100,size >> 1);

    }

    public void initField(){
        Field field = controller.getField();
        text.setText(controller.getTurn().getName());
        GraphicsContext gc = this.field.getGraphicsContext2D();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if((i+j)%2!=0) {
                    Cell fieldCell = field.getCell(Field.stringKey(i,j));
                    Checker checkerFrom = null;
                    if(controller.getFrom()!=null) {
                        checkerFrom = field.getChecker(Field.stringKey(controller.getFrom().x, controller.getFrom().y));
                    }
                    if(controller.getFrom()!=null && checkerFrom!=null){
                        if(((controller.getFrom().y==j && controller.getFrom().x == i))) {
                            gc.setFill(Color.GREEN);
                        }else if(checkerFrom.canGoTo(fieldCell) && !checkerFrom.canBitAnything() && checkerFrom.getPlayer().canOnlyBit().isEmpty()){
                            gc.setFill(Color.GREEN);
                        }else if( checkerFrom.canBit(fieldCell)){
                            gc.setFill(Color.GREEN);
                        }else {
                            gc.setFill(Color.BROWN);
                        }
                    }else {
                        gc.setFill(Color.BROWN);
                    }

                    gc.fillRect(i * width, j * width, width, width);
                    if(fieldCell != null && fieldCell.getChecker()!=null){
                        if(fieldCell.getChecker().isWhite()){
                            gc.setFill(Color.WHITE);
                        }else {
                            gc.setFill(Color.BLACK);
                        }
                        gc.fillOval(i * width+3, j * width+3, width-6,width-6);
                        if(fieldCell.getChecker() instanceof Queen){
                            gc.setFill(Color.GOLD);
                            gc.fillOval(i * width+10,j * width+10, width-20,width-20);
                        }
                    }
                }
            }
        }
        if(controller.getCheckers().getWinner()!=null){
            initEnd();
            runnableField.stop();
            if (runnableGame!=null){
                runnableGame.stop();
            }

        }
    }

    boolean startStop = true;

    private void initStartButton(){

        startButton = new Button("start");
        startButton.setOnMouseClicked(mouseEvent -> {
            GraphicsContext gc = this.field.getGraphicsContext2D();
            gc.setFill(Color.WHITE);
            gc.fillRect(0,0,size,size);
            if(runnableField!=null){
                runnableField.stop();
            }
            controller = new Controller(firstPlayer.getText(),isAI.isSelected(),secondPlayer.getText(),isAI.isSelected(), width);
            initField();
            text.setVisible(true);
            prev.setVisible(true);
            next.setVisible(true);
            saveGameButton.setVisible(true);

                runnableField = new RunnableField(this);
                Thread thread = new Thread(runnableField);
                thread.start();

        });
        stopGameButton.setOnMouseClicked(mouseEvent -> {
            if(startStop){
                ConcurentSolution.setCanStart(false);
                startStop=false;
                stopGameButton.setText("continue");
            }else{
                ConcurentSolution.setCanStart(true);
                startStop=true;
                stopGameButton.setText("stop");
            }

        });

        prev.setOnMouseClicked(mouseEvent -> {
            controller.getCheckers().previous();
            initField();
        });

        next.setOnMouseClicked(mouseEvent -> {
            controller.getCheckers().next();
            initField();
        });
        startFromSaveButton.setOnMouseClicked(mouseEvent -> {
            GraphicsContext gc = this.field.getGraphicsContext2D();
            gc.setFill(Color.WHITE);
            gc.fillRect(0,0,size,size);
            if(runnableField !=null) {
                runnableField.stop();
            }
            controller = new Controller(GameSaver.getFromSave(fromSave.getText()), width);
            runnableField = new RunnableField(this);
            Thread thread = new Thread(runnableField);
            thread.start();
            initField();
            text.setVisible(true);
            prev.setVisible(true);
            next.setVisible(true);
            saveGameButton.setVisible(true);
//            isAI.setVisible(false);
        });
        saveGameButton.setOnMouseClicked(mouseEvent -> {
            controller.getCheckers().saveGame(fromSave.getText());
        });
    }

    public Field getControllerField() {
        return controller.getField();
    }
}

package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.maps.ColorIntMap;
import it.polimi.ingsw.model.maps.IntColorMap;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardController extends ViewObservable implements GenericSceneController {

    @FXML
    private GridPane island1, island2, island3, island4, island5, island6, island7, island8, island9, island10, island11, island12;
    @FXML
    private GridPane cloud1, cloud2, cloud3pane;
    @FXML
    private GridPane redStudents, greenStudents, yellowStudents, blueStudents, pinkStudents;
    @FXML
    private ImageView character1;
    @FXML
    private ImageView character2;
    @FXML
    private ImageView character3;
    @FXML
    private ImageView cloud3;
    @FXML
    private GridPane deck;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label turnInfo;
    @FXML
    private Button turnAction;
    @FXML
    private Label player3;
    @FXML
    private Button button;
    @FXML
    private ImageView playerBoard;
    @FXML
    private Label coins;
    @FXML
    private GridPane playerBoardEntrance;

    private ReducedModel reducedModel;

    private List<GridPane> islands;

    private List<Node> assistantCards;

    private List<Node> islandComponents;

    private IntColorMap intColorMap = new IntColorMap();
    private Map<Integer, Student> getStudentFromInt = intColorMap.getMap();
    private Map<Student, String> studentsImages = new HashMap<>();
    private Map<Student, String> professorImages = new HashMap<>();


    @FXML
    public void initialize() {
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onButtonClick);
    }


    @FXML
    private void onButtonClick(Event event) {
        showCharacters();
        islands = new ArrayList<>();
        islands.add(island1);
        islands.add(island2);
        islands.add(island3);
        islands.add(island4);
        islands.add(island5);
        islands.add(island6);
        islands.add(island7);
        islands.add(island8);
        islands.add(island9);
        islands.add(island10);
        islands.add(island11);
        islands.add(island12);

        initStudentMap();
        initIslands();
        showDeck();
        showPlayerBoard();
    }

    private void showPlayerBoard() {
        int i = 0, j = 0;

        for(Student student : reducedModel.getReducedBoard().getPlayerBoard().getStudents()){
            if(i != 0 && j != 0) playerBoardEntrance.add(new ImageView(new Image(studentsImages.get(student))), j, i);

            if(j == 2) i++;
            j++;
        }
    }

    private void onPlayerBoardClick(Event event){
    }

    public void initIslands(){
        int i = 0;

        for(GridPane island : islands ){
            List<Student> students = reducedModel.getReducedBoard().getIslands().get(i).getStudents();

            for(Student student : students){
                Image image = new Image(studentsImages.get(student));
                island.add(new ImageView(image), 2, 2);
            }

            i++;
        }

        if(reducedModel.getReducedBoard().getClouds().length == 3) cloud3.setImage(new Image(String.valueOf(getClass().getResource("/assets/custom/cloud.png"))));

        for(Cloud cloud : reducedModel.getReducedBoard().getClouds()){
            List<Student> students = cloud.getStudents();


            for(Student student : students){

            }
        }
    }

    public void initStudentMap(){
        studentsImages.put(Student.RED, String.valueOf(getClass().getResource("/assets/custom/redStud.png")));
        studentsImages.put(Student.YELLOW, String.valueOf(getClass().getResource("/assets/custom/yellowStud.png")));
        studentsImages.put(Student.PINK, String.valueOf(getClass().getResource("/assets/custom/pinkStud.png")));
        studentsImages.put(Student.GREEN, String.valueOf(getClass().getResource("/assets/custom/greenStud.png")));
        studentsImages.put(Student.BLUE, String.valueOf(getClass().getResource("/assets/custom/blueStud.png")));
    }

    public void setReducedModel(ReducedModel reducedModel){
        this.reducedModel = reducedModel;
    }

    public void showCharacters(){
        int id = reducedModel.getReducedCharacters()[0].getEffectId();
        character1.setImage(new Image(String.valueOf(getClass().getResource("/assets/personaggi/character" + id +".jpg"))));

        id = reducedModel.getReducedCharacters()[1].getEffectId();
        character2.setImage(new Image(String.valueOf(getClass().getResource("/assets/personaggi/character" + id +".jpg"))));

        id = reducedModel.getReducedCharacters()[2].getEffectId();
        character3.setImage(new Image(String.valueOf(getClass().getResource("/assets/personaggi/character" + id +".jpg"))));
    }

    public void showDeck() {
        for(int i = 0; i < 10; i++){
            deck.add(new ImageView(new Image(String.valueOf(getClass().getResource("/assets/assistenti/Assistente" + (i + 1) + ".png")))),i ,0);
        }
    }
}

package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.maps.ColorIntMap;
import it.polimi.ingsw.model.maps.IntColorMap;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardController extends ViewObservable implements GenericSceneController {

    @FXML
    private ImageView character1;
    @FXML
    private ImageView character2;
    @FXML
    private ImageView character3;
    @FXML
    private GridPane deck;
    @FXML
    private Button button;

    private ReducedModel reducedModel;

    private List<GridPane> islands;

    private List<ImageView> islandComponents;

    @FXML
    private GridPane islandsGridPane;

    private ColorIntMap intColorMap = new ColorIntMap();
    private Map<Student, Integer> getStudentFromInt = intColorMap.getMap();
    private Map<Student, String> studentsImages = new HashMap<>();
    private Map<Student, String> professorImages = new HashMap<>();


    @FXML
    public void initialize() {
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onButtonClick);
    }


    @FXML
    private void onButtonClick(Event event) {
        showCharacters();
        initIslands();
        showDeck();

    }

    public void initIslands(){
        for(int i = 0; i < 5; i ++){
            islandsGridPane.add(new ImageView(new Image(String.valueOf(getClass().getResource("/assets/custom/island1.png")))),i ,0);
        }

        islandsGridPane.add(new ImageView(new Image(String.valueOf(getClass().getResource("/assets/custom/island1.png")))),0 ,1);

        islandsGridPane.add(new ImageView(new Image(String.valueOf(getClass().getResource("/assets/custom/island1.png")))),4 ,1);


        for(int i = 0; i < 5; i ++){
            islandsGridPane.add(new ImageView(new Image(String.valueOf(getClass().getResource("/assets/custom/island1.png")))),i ,2);
        }
    }

    public void initStudentMap(){
        studentsImages.put(Student.RED, "/assets/custom/redStud.png");
        studentsImages.put(Student.YELLOW, "/assets/custom/yellowStud.png");
        studentsImages.put(Student.PINK, "/assets/custom/pinkStud.png");
        studentsImages.put(Student.GREEN, "/assets/custom/greenStud.png");
        studentsImages.put(Student.BLUE, "/assets/custom/blueStud.png");
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

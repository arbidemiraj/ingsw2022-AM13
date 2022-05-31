package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.maps.IntColorMap;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

import java.util.*;

public class BoardController extends ViewObservable implements GenericSceneController {

    @FXML
    private GridPane island1, island2, island3, island4, island5, island6, island7, island8, island9, island10, island11, island12;
    @FXML
    private TilePane cloud1, cloud2;
    @FXML
    private TilePane greenStudents, redStudents, yellowStudents, blueStudents, pinkStudents;
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
    private TilePane test;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label turnInfo;
    @FXML
    private Button turnAction;
    @FXML
    private TilePane playerTowers;
    @FXML
    private Label characters;
    @FXML
    private Label coinText;
    @FXML
    private Label coins;
    @FXML
    private ImageView coinImage;
    @FXML
    private Label player3;
    @FXML
    private Button button;
    @FXML
    private ImageView playerBoard;
    @FXML
    private GridPane playerBoardEntrance;

    private ReducedModel reducedModel;

    private List<GridPane> islands;

    private List<TilePane> cloudsPane;

    private List<ImageView> cards;

    private List<Node> assistantCards;

    private List<Node> islandComponents;

    private IntColorMap intColorMap = new IntColorMap();
    private Map<Integer, Student> getStudentFromInt = intColorMap.getMap();
    private Map<Student, String> studentsImages = new HashMap<>();
    private Map<TowerColor, String> towerImages = new HashMap<>();
    private Map<Student, String> professorImages = new HashMap<>();


    @FXML
    public void initialize() {
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onButtonClick);

        if(!reducedModel.isExpertMode()){
            coinText.setText("");
            characters.setText("");
            coins.setText("");
        }else{
            characters.setText("");
            coins.setText(String.valueOf(reducedModel.getNumCoins()));
            coinImage.setImage(new Image(String.valueOf(getClass().getResource("/assets/custom/coin.png"))));
            showCharacters();
        }

        cloudsPane = new ArrayList<>();

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


        cloudsPane.add(cloud1);
        cloudsPane.add(cloud2);

        initMaps();
        initIslands();
        showDeck();
        showPlayerBoard();

        player1.setText(reducedModel.getUsername().get(0));
        player2.setText(reducedModel.getUsername().get(1));

        if(reducedModel.getUsername().size() == 3) player3.setText(reducedModel.getUsername().get(2));
        else player3.setText("");

        cards = new ArrayList<>();

        button.setDisable(true);

        deck.setOnMouseClicked(e -> {
            Node node = (Node) e.getTarget();
            int column = GridPane.getColumnIndex(node);
            playCard(column);
        });

    }

    private void playCard(int id) {
        new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateCard((id + 1)))).start();
    }


    @FXML
    private void onButtonClick(Event event) {
    }

    private void showPlayerBoard() {
        int i = 0, j = 1;

        for(Student student : reducedModel.getReducedBoard().getPlayerBoard().getStudents()){
            ImageView imageView = new ImageView(new Image(studentsImages.get(student)));

            imageView.setScaleX(2);
            imageView.setScaleY(2);

            playerBoardEntrance.add(imageView, i, j);

            if(i == 1){
                i = 0;
                j++;
            }
            else{
                i++;
            }
        }

        for(int h = 0; h < reducedModel.getReducedBoard().getPlayerBoard().getNumTowers(); h++){
            ImageView imageView = new ImageView(new Image(towerImages.get(reducedModel.getColor())));

            imageView.setScaleX(0.5);
            imageView.setScaleY(0.5);

            playerTowers.getChildren().add(imageView);
        }
    }


    public void initIslands(){
        int i = 0;

        for(GridPane island : islands ){
            List<Student> students = reducedModel.getReducedBoard().getIslands().get(i).getStudents();

            for(Student student : students){
                Image image = new Image(studentsImages.get(student));
                island.add(new ImageView(image), 2, 2);
            }

            if(reducedModel.getReducedBoard().getIslands().get(i).isMotherNature()) island.add(new ImageView(new Image(String.valueOf(getClass().getResource("/assets/custom/motherNature.png")))), 1, 1);
            i++;
        }

        if(reducedModel.getReducedBoard().getClouds().length == 3) cloud3.setImage(new Image(String.valueOf(getClass().getResource("/assets/custom/cloud.png"))));

        int k = 0;

        for(Cloud cloud : reducedModel.getReducedBoard().getClouds()){

            List<Student> students = cloud.getStudents();

            for(Student student : students){
                cloudsPane.get(k).getChildren().add(new ImageView(new Image(studentsImages.get(student))));
            }

            k++;
        }
    }

    public void initMaps(){
        studentsImages.put(Student.RED, String.valueOf(getClass().getResource("/assets/custom/redStud.png")));
        studentsImages.put(Student.YELLOW, String.valueOf(getClass().getResource("/assets/custom/yellowStud.png")));
        studentsImages.put(Student.PINK, String.valueOf(getClass().getResource("/assets/custom/pinkStud.png")));
        studentsImages.put(Student.GREEN, String.valueOf(getClass().getResource("/assets/custom/greenStud.png")));
        studentsImages.put(Student.BLUE, String.valueOf(getClass().getResource("/assets/custom/blueStud.png")));

        towerImages.put(TowerColor.BLACK, String.valueOf(getClass().getResource("/assets/custom/blackTower.png")));
        towerImages.put(TowerColor.GRAY, String.valueOf(getClass().getResource("/assets/custom/grayTower.png")));
        towerImages.put(TowerColor.WHITE, String.valueOf(getClass().getResource("/assets/custom/whiteTower.png")));

        professorImages.put(Student.RED, String.valueOf(getClass().getResource("/assets/custom/redProf.png")));
        professorImages.put(Student.YELLOW, String.valueOf(getClass().getResource("/assets/custom/yellowProf.png")));
        professorImages.put(Student.PINK, String.valueOf(getClass().getResource("/assets/custom/pinkProf.png")));
        professorImages.put(Student.GREEN, String.valueOf(getClass().getResource("/assets/custom/greenProf.png")));
        professorImages.put(Student.BLUE, String.valueOf(getClass().getResource("/assets/custom/blueProf.png")));
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
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/assets/assistenti/Assistente" + (i + 1) + ".png"))));
            imageView.getStyleClass().set(0, "clickable");
            deck.add(imageView,i ,0);

        }
    }

    public void showGenericText(String text) {
        turnInfo.setText(text);
    }

    public void setFirstPlayer(String playerUsername, String firstPlayer) {
    }

    private void disableClickable() {

    }

    public void askStudent() {
        turnInfo.setText("Select a student from entrance");

        playerBoardEntrance.setOnMouseClicked(e -> {
            Node node = (Node) e.getTarget();

            ImageView student = (ImageView) node;
            student.getImage().getUrl();
            int row = GridPane.getRowIndex(playerBoardEntrance);
            int column = GridPane.getColumnIndex(playerBoardEntrance);
        });

    }
}

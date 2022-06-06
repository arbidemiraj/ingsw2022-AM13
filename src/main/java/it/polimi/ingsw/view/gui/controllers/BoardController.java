package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.maps.ColorIntMap;
import it.polimi.ingsw.model.maps.IntColorMap;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.awt.*;
import java.util.*;
import java.util.List;

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
    private ImageView estud1, estud2, estud3, estud4, estud5, estud6, estud7, estud8, estud9;
    @FXML
    private Label greenProfOwner, redProfOwner, yellowProfOwner, pinkProfOwner, blueProfOwner;
    @FXML
    private GridPane deck;
    @FXML
    private Button quitButton;
    @FXML
    private AnchorPane boardPane;
    @FXML
    private TilePane test;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label turnInfo;
    @FXML
    private GridPane playerBoardTowers;
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
    private ImageView playerBoard;
    @FXML
    private ImageView lastCard1;
    @FXML
    private ImageView lastCard2;
    @FXML
    private ImageView cloud3;
    @FXML
    private TilePane cloud3pane;
    @FXML
    private Label turnPlayer;
    @FXML
    private ImageView lastCard3;

    private ReducedModel reducedModel;

    private List<GridPane> islands;

    private List<TilePane> cloudsPane;

    private List<Label> professors;

    private List<TilePane> dinnerRoom;

    private List<ImageView> cards;

    private List<Node> assistantCards;

    private List<Node> islandComponents;

    private List<ImageView> entrance;

    private int studentsCount = 0;

    private IntColorMap intColorMap = new IntColorMap();
    private Map<Integer, Student> getStudentFromInt = intColorMap.getMap();
    private ColorIntMap colorIntMap = new ColorIntMap();
    private Map<Student, Integer> getIntFromStudent = colorIntMap.getMap();
    private Map<Student, Integer> positionMap = new HashMap<>();

    private Map<Student, String> studentsImages = new HashMap<>();
    private Map<TowerColor, String> towerImages = new HashMap<>();
    private Map<Student, String> professorImages = new HashMap<>();

    private Map<String, Student> imagesStudent = new HashMap<>();
    private Map<String, TowerColor> imagesTower = new HashMap<>();
    private Map<String, Student> imagesProfessor = new HashMap<>();

    private List<String> moveStudentParameters;

    private Stage stage;

    @FXML
    public void initialize() {
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
        entrance = new ArrayList<>();
        islands = new ArrayList<>();
        dinnerRoom = new ArrayList<>();
        professors = new ArrayList<>();

        moveStudentParameters = new ArrayList<>();


        for(int i = 1; i < 10; i++) {
            try {
                try {
                    entrance.add((ImageView) getClass().getDeclaredField("estud" + i).get(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        professors.add(yellowProfOwner);
        professors.add(blueProfOwner);
        professors.add(greenProfOwner);
        professors.add(pinkProfOwner);
        professors.add(redProfOwner);

        for(int i = 1; i <= 12; i++) {
            try {
                try {
                    islands.add((GridPane) getClass().getDeclaredField("island" + i).get(this));
                } catch (IllegalAccessException e) {
                }
            } catch (NoSuchFieldException e) {
            }
        }

        cloudsPane.add(cloud1);
        cloudsPane.add(cloud2);

        if(reducedModel.getUsername().size() == 3){
            cloudsPane.add(cloud3pane);
        }

        dinnerRoom.add(greenStudents);
        dinnerRoom.add(redStudents);
        dinnerRoom.add(yellowStudents);
        dinnerRoom.add(pinkStudents);
        dinnerRoom.add(blueStudents);

        initMaps();
        initIslands();
        showDeck();
        showPlayerBoard();

        player1.setText(reducedModel.getUsername().get(0));
        player2.setText(reducedModel.getUsername().get(1));

        if(reducedModel.getUsername().size() == 3) player3.setText(reducedModel.getUsername().get(2));
        else player3.setText("");

        cards = new ArrayList<>();
    }

    private void playCard(int id) {
        deck.setOnMouseClicked(e -> {showGenericText("You can't play a card now!");});

        new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateCard((id + 1)))).start();
    }

    private void showPlayerBoard() {
        int i = 0, j = 0;

        for(Student student : reducedModel.getReducedBoard().getPlayerBoard().getStudents()){
            entrance.get(i).setImage(new Image(studentsImages.get(student)));

            i++;
        }

        i = 0;
        j = 0;

        for(int h = 0; h < reducedModel.getReducedBoard().getPlayerBoard().getNumTowers(); h++){
            ImageView imageView = new ImageView(new Image(towerImages.get(reducedModel.getColor())));

            playerBoardTowers.add(imageView, j, i);

            if(j == 1){
                j = 0;
                i++;
            }else{
                j++;
            }
        }
    }


    public void initIslands(){
        int i = 0;



        for(GridPane island : islands ){
            List<Student> students = reducedModel.getReducedBoard().getIslands().get(i).getStudents();
            int numStudents[] = reducedModel.getReducedBoard().getIslands().get(i).getNumStudents();
            int j = 0;

            for(Student student : Student.values()){
                Image image = new Image(studentsImages.get(student));
                island.add(new ImageView(image), 2, getIntFromStudent.get(student));
                island.add(new Label(" : " + numStudents[j]), 3, getIntFromStudent.get(student));
                j++;
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
        //map var to image
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

        //map image to var
        imagesStudent.put(String.valueOf(getClass().getResource("/assets/custom/redStud.png")), Student.RED);
        imagesStudent.put(String.valueOf(getClass().getResource("/assets/custom/yellowStud.png")), Student.YELLOW);
        imagesStudent.put(String.valueOf(getClass().getResource("/assets/custom/pinkStud.png")), Student.PINK);
        imagesStudent.put(String.valueOf(getClass().getResource("/assets/custom/greenStud.png")), Student.GREEN);
        imagesStudent.put(String.valueOf(getClass().getResource("/assets/custom/blueStud.png")), Student.BLUE);

        imagesTower.put(String.valueOf(getClass().getResource("/assets/custom/blackTower.png")),TowerColor.BLACK);
        imagesTower.put(String.valueOf(getClass().getResource("/assets/custom/grayTower.png")),TowerColor.GRAY);
        imagesTower.put(String.valueOf(getClass().getResource("/assets/custom/whiteTower.png")),TowerColor.WHITE);

        imagesProfessor.put(String.valueOf(getClass().getResource("/assets/custom/redProf.png")),Student.RED);
        imagesProfessor.put(String.valueOf(getClass().getResource("/assets/custom/yellowStud.png")), Student.YELLOW);
        imagesProfessor.put(String.valueOf(getClass().getResource("/assets/custom/pinkStud.png")), Student.PINK);
        imagesProfessor.put(String.valueOf(getClass().getResource("/assets/custom/greenStud.png")), Student.GREEN);
        imagesProfessor.put(String.valueOf(getClass().getResource("/assets/custom/blueStud.png")), Student.BLUE);

        positionMap.put(Student.GREEN, 0);
        positionMap.put(Student.RED, 1);
        positionMap.put(Student.YELLOW, 2);
        positionMap.put(Student.PINK, 3);
        positionMap.put(Student.BLUE, 4);
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

    private void disableClickable() {

    }

    public void askStudent() {
        turnInfo.setText("Select a student from entrance");

        for (ImageView singleStud : entrance) {
            singleStud.getStyleClass().set(0, "clickable");

            singleStud.setOnMouseClicked(e -> {
                Node node = (Node) e.getTarget();

                ImageView student = (ImageView) node;

                String studentString = String.valueOf(imagesStudent.get(student.getImage().getUrl()));

                moveStudentParameters.add(studentString);

                turnInfo.setText("Select where you want to move the student [ Dinner or Island ]");

                askWhere(student.getImage().getUrl());

                student.setImage(null);
            });

        }
    }

    private void askWhere(String url) {

        for(GridPane island : islands){
            island.setOnMouseClicked(e -> {
                Node source = (Node)e.getSource() ;

                reducedModel.getReducedBoard().getIslands().get(islands.indexOf(island)).addStudent(imagesStudent.get(url));

                updateIsland(island, imagesStudent.get(url));

                studentsCount++;

                if(studentsCount == 3){
                    disableStudents();
                    studentsCount = 0;
                }

                new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateStudent("ENTRANCE", String.valueOf(imagesStudent.get(url)), "ISLAND", islands.indexOf(island)))).start();
            });
        }

        for(TilePane dinner : dinnerRoom){
            dinner.setOnMouseClicked(e -> {
                addStudentToDinner(dinnerRoom.get(positionMap.get(imagesStudent.get(url))), url);

                studentsCount++;

                if(studentsCount == 3) disableStudents();

                new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateStudent("ENTRANCE", String.valueOf(imagesStudent.get(url)), "DINNER", 0))).start();
            });
        }
    }

    private void updateIsland(GridPane island, Student student) {
        int numStudents = reducedModel.getReducedBoard().getIslands().get(islands.indexOf(island)).getNumStudents()[getIntFromStudent.get(student)];

        island.getChildren().remove(3, getIntFromStudent.get(student));
        island.add(new Label(" : " + numStudents), 3, getIntFromStudent.get(student));
    }

    private void disableStudents() {
        for(ImageView singleStud : entrance) {
            singleStud.setOnMouseClicked(e -> {
                singleStud.getStyleClass().set(0, "");
                turnInfo.setText("You can't move a student!");
            });
        }
    }

    private void addStudentToDinner(TilePane dinner, String url) {
        dinner.getChildren().add(new ImageView(new Image(url)));
    }

    public void askCard(){

        turnInfo.setText("It's your turn! Select an assistant card to play");

        if(reducedModel.getCurrentPlayer().equals(reducedModel.getPlayerUsername())){
            showGenericText("You are the first player! Select an assistant card to play");
        }


        turnPlayer.setText("Current Player: " + reducedModel.getCurrentPlayer());

        if(!reducedModel.getTurnCards().isEmpty()){
            int id = reducedModel.getTurnCards().get(0).getValue();

            lastCard2.setImage(new Image(String.valueOf(getClass().getResource("/assets/assistenti/Assistente"+ (id) +".png"))));

            if(reducedModel.getTurnCards().size() == 2){
                lastCard3.setImage(new Image(String.valueOf(getClass().getResource("/assets/assistenti/Assistente"+ (id) +".png"))));
            }
        }

        deck.setOnMouseClicked(e -> {
            Node node = (Node) e.getTarget();
            int id = GridPane.getColumnIndex(node);

            boolean isValid = true;

            for(AssistantCard assistantCard : reducedModel.getTurnCards()){
                if(assistantCard.getValue() == id + 1){
                    isValid = false;
                }
            }

            if(isValid = true){
                ImageView imageView = (ImageView) node;

                imageView.setImage(new Image(String.valueOf(getClass().getResource("/assets/assistenti/retro1.png"))));

                playCard(id);

                lastCard1.setImage(new Image(String.valueOf(getClass().getResource("/assets/assistenti/Assistente"+ (id + 1) +".png"))));
            }
        });
    }

    public void setTurnCards(List<AssistantCard> turnCardsPlayed) {

        if(turnCardsPlayed.size() == 3){
            int id = turnCardsPlayed.get(2).getValue();
            lastCard3.setImage(new Image(String.valueOf(getClass().getResource("/assets/assistenti/Assistente"+ (id) +".png"))));
        }else if(turnCardsPlayed.size() == 2){
            int id = turnCardsPlayed.get(0).getValue();
            lastCard2.setImage(new Image(String.valueOf(getClass().getResource("/assets/assistenti/Assistente"+ (id) +".png"))));

            if(lastCard1.getImage().equals(lastCard2.getImage())){
                id = turnCardsPlayed.get(1).getValue();
                lastCard2.setImage(new Image(String.valueOf(getClass().getResource("/assets/assistenti/Assistente"+ (id) +".png"))));
            }
        }
    }

    public void askMotherNature() {
        turnInfo.setText("Select the island where you want to move mother nature");

        for(GridPane island : islands){
            island.setOnMouseClicked(e -> {

                int steps = islands.indexOf(island) - reducedModel.getReducedBoard().getMotherNature();

                if(steps < (reducedModel).getMaxSteps()) {
                    ObservableList<Node> childrens = islands.get(reducedModel.getReducedBoard().getMotherNature()).getChildren();

                    for (Node node : childrens) {
                        if(island.getRowIndex(node) != null && island.getColumnIndex(node) != null) {
                            if(island.getRowIndex(node) == 1 && island.getColumnIndex(node) == 1) {
                            ImageView image = (ImageView) node;
                            image.setImage(null);
                        }
                        }
                    }

                    island.add(new ImageView(new Image(String.valueOf(getClass().getResource("/assets/custom/motherNature.png")))), 1, 1);

                    new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateMotherNature(steps))).start();
                }
                else {
                    turnInfo.setText("Invalid move! Retry");
                }
            });
        }

    }

    public void askCloud() {
        turnInfo.setText("Choose the cloud you want to get the students from");

        setCloudClickable(true);

        for(TilePane cloud : cloudsPane){
            cloud.setOnMouseClicked(e -> {
                Node node = (Node) e.getTarget();

                new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateCloud(cloudsPane.indexOf(cloud))));

                setCloudClickable(false);
                cloud.getChildren().removeAll();
            });
        }
    }

    private void setCloudClickable(boolean b) {
        if(true) {
            cloud1.getStyleClass().set(0, "clickable");
            cloud2.getStyleClass().set(0, "clickable");

            if(reducedModel.getUsername().size() == 3){
                cloud3.getStyleClass().set(0, "clickable");
            }
        } else {
            cloud1.getStyleClass().set(0, "");
            cloud2.getStyleClass().set(0, "");

            if(reducedModel.getUsername().size() == 3){
                cloud3.getStyleClass().set(0, "");
            }
        }
    }

    public void setProf(String professorOwner, Student color) {
        professors.get(getIntFromStudent.get(color)).setText(" : " + professorOwner);
    }

    public void mergeIslands(int island1, int island2) {
        int numIslands = reducedModel.getReducedBoard().getIslands().get(island1).getNumIslands();

        int numStudents[] = reducedModel.getReducedBoard().getIslands().get(island1).getNumStudents();
        GridPane island = islands.get(island1);
        int j = 0;

        ObservableList<Node> childrens = island.getChildren();

        for (Node node : childrens) {
            if(island.getRowIndex(node) != null && island.getColumnIndex(node) != null) {
                if(island.getRowIndex(node) == 0 && island.getColumnIndex(node) == 1) {
                    ImageView image = (ImageView) node;
                    image.setImage(new Image(String.valueOf(getClass().getResource("/assets/custom/" + numIslands + "islands.png"))));
                }
            }
        }


        for(Student student : Student.values()){
            Image image = new Image(studentsImages.get(student));
            island.add(new ImageView(image), 2, getIntFromStudent.get(student));
            island.add(new Label(" : " + numStudents[j]), 3, getIntFromStudent.get(student));
            j++;
        }
    }

    public void conquerIsland(int island, String color) {
        GridPane islandOwned = islands.get(island);
        int j = 0;

        ObservableList<Node> childrens = islandOwned.getChildren();

        for (Node node : childrens) {
            if(islandOwned.getRowIndex(node) != null && islandOwned.getColumnIndex(node) != null) {
                if(islandOwned.getRowIndex(node) == 0 && islandOwned.getColumnIndex(node) == 4) {
                    ImageView image = (ImageView) node;
                    image.setImage(new Image(String.valueOf(getClass().getResource("/assets/custom/" + color + "Tower.png"))));
                }
            }
        }
    }

    public void quit (ActiveEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("do you want to leave the game?");

        if (alert.showAndWait().get()== ButtonType.OK){
            stage = (Stage) boardPane.getScene().getWindow();
            System.out.println("You have quit the game");
            stage.close();
        }
    }
}

package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.enumerations.PhaseType;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import it.polimi.ingsw.model.maps.ColorIntMap;
import it.polimi.ingsw.model.maps.IntColorMap;
import it.polimi.ingsw.network.client.reducedModel.ReducedModel;
import it.polimi.ingsw.observer.ViewObservable;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The controller that handles the GUI in the game phase
 */
public class BoardController extends ViewObservable implements GenericSceneController {

    @FXML
    private GridPane island1, island2, island3, island4, island5, island6, island7, island8, island9, island10, island11, island12;
    @FXML
    private TilePane cloud1, cloud2;
    @FXML
    private TilePane greenStudents, redStudents, yellowStudents, blueStudents, pinkStudents;
    @FXML
    private TilePane characterPane1, characterPane2, characterPane3;
    @FXML
    private ImageView character1, character2, character3, cloudImage1, cloudImage2;
    @FXML
    private ImageView estud1, estud2, estud3, estud4, estud5, estud6, estud7, estud8, estud9, green, yellow, blue, pink, red;
    @FXML
    private Label greenProfOwner, redProfOwner, yellowProfOwner, pinkProfOwner, blueProfOwner;
    @FXML
    private Label characterOwner1, characterOwner2, characterOwner3;
    @FXML
    private ImageView desc1, desc2, desc3;
    @FXML
    private GridPane deck;
    @FXML
    private Button quitButton;
    @FXML
    private AnchorPane boardPane;
    @FXML
    private TilePane test;
    @FXML
    private Label player1, player2, player3;
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
    private ImageView playerBoard;
    @FXML
    private ImageView lastCard1;
    @FXML
    private ImageView lastCard2;
    @FXML
    private ImageView cloud3;
    @FXML
    private Button confirmBtn;
    @FXML
    private TilePane cloud3pane;
    @FXML
    private Label turnPlayer;
    @FXML
    private ImageView lastCard3;
    @FXML
    private ImageView greenProf,redProf,blueProf,yellowProf,pinkProf;

    private ReducedModel reducedModel;

    private List<GridPane> islands;

    private List<TilePane> cloudsPane;

    private List<Label> professors;

    private List<TilePane> dinnerRoom;

    private List<ImageView> cardsPlayed;

    private List<ImageView> descriptions;

    private List<Node> assistantCards;

    private List<Node> islandComponents;

    private PhaseType currentPhase;

    private List<ImageView> entrance;

    private List<ImageView> colors;

    private List<ImageView> cloudsImage;

    private List<ImageView> charactersImages;

    private List<TilePane> charactersPanes;

    private List<Label> owners;

    private List<ImageView> boardProf;

    private int studentsCount = 0;

    private int disabledIsland = -1;

    private String effectStudentUrl = "";

    private IntColorMap intColorMap = new IntColorMap();
    private Map<Integer, Student> getStudentFromInt = intColorMap.getMap();
    private ColorIntMap colorIntMap = new ColorIntMap();
    private Map<Student, Integer> getIntFromStudent = colorIntMap.getMap();
    private Map<Student, Integer> positionMap = new HashMap<>();
    private Map<String, ImageView> lastCardMap = new HashMap<>();

    private Map<Student, String> studentsImages = new HashMap<>();
    private Map<TowerColor, String> towerImages = new HashMap<>();
    private Map<Student, String> professorImages = new HashMap<>();

    private Map<String, Student> imagesStudent = new HashMap<>();
    private Map<String, TowerColor> imagesTower = new HashMap<>();
    private Map<String, Student> imagesProfessor = new HashMap<>();

    private List<String> moveStudentParameters;

    private Stage stage;

    /**
     * JavaFx initialize method that handles the setup phase of the game
     */
    @FXML
    public void initialize() {
        confirmBtn.setOpacity(0);
        confirmBtn.setDisable(true);

        quitButton.setOnMouseClicked(this::quit);

        turnInfo.setText("Wait... other players are playing their turn");

        cloudsPane = new ArrayList<>();
        entrance = new ArrayList<>();
        islands = new ArrayList<>();
        dinnerRoom = new ArrayList<>();
        professors = new ArrayList<>();
        cloudsImage = new ArrayList<>();

        moveStudentParameters = new ArrayList<>();
        cardsPlayed = new ArrayList<>();
        boardProf = new ArrayList<>();

        colors = new ArrayList<>();

        colors.add(red);
        colors.add(green);
        colors.add(yellow);
        colors.add(pink);
        colors.add(blue);

        boardProf.add(greenProf);
        boardProf.add(redProf);
        boardProf.add(yellowProf);
        boardProf.add(pinkProf);
        boardProf.add(blueProf);


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

        cloudsImage.add(cloudImage1);
        cloudsImage.add(cloudImage2);

        if(reducedModel.getUsername().size() == 3){
            cloudsPane.add(cloud3pane);
            cloud3.setImage(new Image(String.valueOf(getClass().getResource("/assets/custom/cloud.png"))));
            cloudsImage.add(cloud3);
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

        if(reducedModel.getUsername().get(0).equals(reducedModel.getPlayerUsername())) player1.setText("You");
        if(reducedModel.getUsername().get(1).equals(reducedModel.getPlayerUsername())) player2.setText("You");

        if(reducedModel.getUsername().size() == 3){
            player3.setText(reducedModel.getUsername().get(2));
            if(reducedModel.getUsername().get(2).equals(reducedModel.getPlayerUsername())) player3.setText("You");
        }
        else player3.setText("");

        if(!reducedModel.isExpertMode()){
            coinText.setText("");
            characters.setText("");
            coins.setText("");
        }else{
            charactersImages = new ArrayList<>();
            charactersPanes = new ArrayList<>();
            owners = new ArrayList<>();
            descriptions = new ArrayList<>();

            descriptions.add(desc1);
            descriptions.add(desc2);
            descriptions.add(desc3);

            owners.add(characterOwner1);
            owners.add(characterOwner2);
            owners.add(characterOwner3);

            characters.setText("Characters");
            coins.setText(String.valueOf(reducedModel.getNumCoins()));
            coinImage.setImage(new Image(String.valueOf(getClass().getResource("/assets/custom/coin.png"))));

            charactersImages.add(character1);
            charactersImages.add(character2);
            charactersImages.add(character3);

            charactersPanes.add(characterPane1);
            charactersPanes.add(characterPane2);
            charactersPanes.add(characterPane3);

            showCharacters();
        }
    }

    /**
     * Plays the card with the given id and notifies the observer
     * @param id the id of the card to play
     */
    private void playCard(int id) {
        deck.setOnMouseClicked(e -> {showAlert("You can't play a card now!");});
        turnInfo.setText("Wait... other players are playing their turn");

        new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateCard((id + 1)))).start();
    }

    /**
     * Shows the user player board and fills the entrance
     */
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

    /**
     * Initializes the islands and shows the students and mother nature
     */
    public void initIslands(){
        int i = 0;

        for(GridPane island : islands ){
            List<Student> students = reducedModel.getReducedBoard().getIslands().get(i).getStudents();
            int numStudents[] = reducedModel.getReducedBoard().getIslands().get(i).getNumStudents();
            int j = 0;

            for(Student student : Student.values()){
                Image image = new Image(studentsImages.get(student));
                island.add(new ImageView(image), 2, getIntFromStudent.get(student));
                Label label;
                if(numStudents[j] != 0) label = new Label(" : " + numStudents[j]);
                else label = new Label(" : --");

                label.setMinWidth(40);
                label.setMinHeight(30);

                island.add(label , 3, getIntFromStudent.get(student));
                j++;
            }

            if(reducedModel.getReducedBoard().getIslands().get(i).isMotherNature()) island.add(new ImageView(new Image(String.valueOf(getClass().getResource("/assets/custom/motherNature.png")))), 1, 1);
            i++;
        }

        if(reducedModel.getReducedBoard().getClouds().length == 3) cloud3.setImage(new Image(String.valueOf(getClass().getResource("/assets/custom/cloud.png"))));

        fillClouds();
    }

    /**
     * Fills the cloud with the students and shows them
     */
    public void fillClouds() {
        int k = 0;

        for(Cloud cloud : reducedModel.getReducedBoard().getClouds()){

            List<Student> students = cloud.getStudents();

            for(Student student : students){
                cloudsPane.get(k).getChildren().add(new ImageView(new Image(studentsImages.get(student))));
            }
            k++;
        }
    }

    /**
     * Initializes all the maps
     */
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
        imagesProfessor.put(String.valueOf(getClass().getResource("/assets/custom/yellowProf.png")), Student.YELLOW);
        imagesProfessor.put(String.valueOf(getClass().getResource("/assets/custom/pinkProf.png")), Student.PINK);
        imagesProfessor.put(String.valueOf(getClass().getResource("/assets/custom/greenProf.png")), Student.GREEN);
        imagesProfessor.put(String.valueOf(getClass().getResource("/assets/custom/blueProf.png")), Student.BLUE);

        positionMap.put(Student.GREEN, 0);
        positionMap.put(Student.RED, 1);
        positionMap.put(Student.YELLOW, 2);
        positionMap.put(Student.PINK, 3);
        positionMap.put(Student.BLUE, 4);

        towerImages.put(TowerColor.BLACK, String.valueOf(getClass().getResource("/assets/custom/blackTower.png")));
        towerImages.put(TowerColor.GRAY, String.valueOf(getClass().getResource("/assets/custom/grayTower.png")));
        towerImages.put(TowerColor.WHITE, String.valueOf(getClass().getResource("/assets/custom/whiteTower.png")));

        lastCardMap.put(reducedModel.getUsername().get(0), lastCard1);
        lastCardMap.put(reducedModel.getUsername().get(1), lastCard2);
        if(reducedModel.getUsername().size() > 2) lastCardMap.put(reducedModel.getUsername().get(2), lastCard3);
    }

    public void setReducedModel(ReducedModel reducedModel){
        this.reducedModel = reducedModel;
    }

    /**
     * If the game is in expert mode this method is called to show the characters
     */
    public void showCharacters(){
        int id = reducedModel.getReducedCharacters()[0].getEffectId();
        character1.setImage(new Image(String.valueOf(getClass().getResource("/assets/personaggi/character" + id +".jpg"))));

        id = reducedModel.getReducedCharacters()[1].getEffectId();
        character2.setImage(new Image(String.valueOf(getClass().getResource("/assets/personaggi/character" + id +".jpg"))));

        id = reducedModel.getReducedCharacters()[2].getEffectId();
        character3.setImage(new Image(String.valueOf(getClass().getResource("/assets/personaggi/character" + id +".jpg"))));

        for(int i = 0; i < 3; i++){
            if(reducedModel.getReducedCharacters()[i].getEffectId() == 5){
                for(int j = 0; j < 4; j++){
                    ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/assets/deny_small.png"))));

                    charactersPanes.get(i).getChildren().add(imageView);
                }
            }
            if(reducedModel.getReducedCharacters()[i].getStudents() != null){
                for(Student student : reducedModel.getReducedCharacters()[i].getStudents()){
                    ImageView studentImage = new ImageView(new Image(studentsImages.get(student)));

                    studentImage.getStyleClass().set(0, "clickable");
                    studentImage.setX(1.2);
                    studentImage.setY(1.2);

                    charactersPanes.get(i).getChildren().add(studentImage);
                }
            }
        }


        for(ImageView character : charactersImages){
            character.setOnMouseEntered(e -> {
                int index = charactersImages.indexOf(character);
                descriptions.get(index).setOpacity(1);
                descriptions.get(index).setDisable(false);

                String url = "/assets/personaggi/descrizioni/desc" + reducedModel.getReducedCharacters()[index].getEffectId() + ".png";
                descriptions.get(index).setImage(new Image(String.valueOf(getClass().getResource(url))));
            });

            character.setOnMouseExited(e -> {
                int index = charactersImages.indexOf(character);

                descriptions.get(index).setOpacity(0);
                descriptions.get(index).setDisable(true);
            });
        }
    }

    /**
     * Shows the deck to the player with all the assistant cards he has
     */
    public void showDeck() {
        for(int i = 0; i < 10; i++){
            ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("/assets/assistenti/Assistente" + (i + 1) + ".png"))));
            imageView.getStyleClass().set(0, "clickable");
            deck.add(imageView,i ,0);

        }
    }

    /**
     * Shows a generic text in the turn info label
     * @param text
     */
    public void showGenericText(String text) {
        turnInfo.setText(text);
    }

    /**
     * Asks the player for a student and sets the entrance students clickable
     */
    public void askStudent() {
        if(reducedModel.isExpertMode()) setCharacterClickable();

        currentPhase = PhaseType.MOVE_STUDENT;

        turnInfo.setText("Select a student from entrance");

        for (ImageView singleStud : entrance) {
            singleStud.getStyleClass().set(0, "clickable");
            singleStud.setDisable(false);

            singleStud.setOnMouseClicked(e -> {
                Node node = (Node) e.getTarget();

                ImageView student = (ImageView) node;

                String studentString = String.valueOf(imagesStudent.get(student.getImage().getUrl()));

                moveStudentParameters.add(studentString);

                turnInfo.setText("Select where you want to move the student [ Dinner or Island ]");

                askWhere(student.getImage().getUrl());

                student.setImage(null);

                disableStudents();
            });

        }
    }

    /**
     * After selecting an entrance student this method is called to ask where the user wants to move the student
     * @param url the url of the image of the student selected
     */
    private void askWhere(String url) {
        disableCharacterClickable();

        for(GridPane island : islands){
            island.setDisable(false);

            island.setOnMouseClicked(e -> {
                Node source = (Node)e.getSource() ;

                reducedModel.getReducedBoard().getIslands().get(islands.indexOf(island)).addStudent(imagesStudent.get(url));

                updateIsland(island, imagesStudent.get(url));

                studentsCount++;

                if(studentsCount == 3){
                    disableStudents();
                    studentsCount = 0;
                }

                for(GridPane is : islands){
                    is.setDisable(true);
                }

                new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateStudent("ENTRANCE", String.valueOf(imagesStudent.get(url)), "ISLAND", islands.indexOf(island)))).start();
            });
        }

        for(TilePane dinner : dinnerRoom){
            dinner.setDisable(false);

            dinner.setOnMouseClicked(e -> {
                addStudentToDinner(dinnerRoom.get(positionMap.get(imagesStudent.get(url))), url);

                studentsCount++;

                if(studentsCount == 3){
                    disableStudents();
                    studentsCount = 0;
                }

                for(TilePane din : dinnerRoom){
                    din.setDisable(true);
                }

                for(GridPane is : islands){
                    is.setDisable(true);
                }

                if((dinner.getChildren().size() == 3 || dinner.getChildren().size() == 6 || dinner.getChildren().size() == 9) && reducedModel.isExpertMode()){
                    reducedModel.addCoin();
                    coins.setText(String.valueOf(reducedModel.getNumCoins()));
                }

                new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateStudent("ENTRANCE", String.valueOf(imagesStudent.get(url)), "DINNER", 0))).start();
            });
        }

    }

    /**
     * This method is called when a student is moven to an island
     * @param island the island selected
     * @param student the student to move
     */
    private void updateIsland(GridPane island, Student student) {
        int numStudents = reducedModel.getReducedBoard().getIslands().get(islands.indexOf(island)).getNumStudents()[getIntFromStudent.get(student)];

        ObservableList<Node> childrens = island.getChildren();

        for (Node node : childrens) {
            if (island.getRowIndex(node) != null && island.getColumnIndex(node) != null) {
                if (island.getRowIndex(node) == getIntFromStudent.get(student) && island.getColumnIndex(node) == 3) {
                    Label label = (Label) node;

                    if(numStudents != 0) label.setText(" : " + numStudents);
                    else label.setText(" : --");
                }
            }
        }
    }

    /**
     * Disables all the entrance students
     */
    private void disableStudents() {
        for(ImageView singleStud : entrance) {
            singleStud.setOnMouseClicked(e -> {
                singleStud.getStyleClass().set(0, "");
                showAlert("You can't move a student!");
            });
        }
    }

    /**
     * Moves a student to the dinner
     * @param dinner the dinner room row associated to the color
     * @param url the url of the image of the selected student
     */
    private void addStudentToDinner(TilePane dinner, String url) {
        dinner.getChildren().add(new ImageView(new Image(url)));
    }

    /**
     * Asks the player for an assistant card
     */
    public void askCard(){
        currentPhase = PhaseType.CARD;

        turnInfo.setText("It's your turn! Select an assistant card to play");

        if(reducedModel.getCurrentPlayer().equals(reducedModel.getPlayerUsername())){
            showGenericText("You are the first player! Select an assistant card to play");
        }


        turnPlayer.setText("Current Player: " + reducedModel.getCurrentPlayer());

        deck.setOnMouseClicked(e -> {
            Node node = (Node) e.getTarget();

            if(!cardsPlayed.contains(node)) {
                int id = GridPane.getColumnIndex(node);

                boolean isValid = true;

                for (AssistantCard assistantCard : reducedModel.getTurnCards()) {
                    if (assistantCard.getValue() == id + 1) {
                        isValid = false;
                    }
                }

                if (isValid) {
                    ImageView imageView = (ImageView) node;

                    int wizardId = reducedModel.getWizardId();

                    imageView.setImage(new Image(String.valueOf(getClass().getResource("/assets/assistenti/wizard" + wizardId +".png"))));

                    playCard(id);

                    cardsPlayed.add(imageView);

                    disableCharacterClickable();
                    currentPhase = PhaseType.NOT_MYTURN;

                    lastCardMap.get(reducedModel.getPlayerUsername()).setImage(new Image(String.valueOf(getClass().getResource("/assets/assistenti/Assistente" + (id + 1) + ".png"))));
                }
                else{
                    showAlert("Card already played!");
                }
            }


        });

        if(reducedModel.isExpertMode()) setCharacterClickable();
    }

    /**
     * Sets the info board with the card played by each user
     * @param turnCardsPlayed the map containing the username and the id of the played card
     */
    public void setTurnCards(HashMap<String, Integer> turnCardsPlayed) {
        for(String username : reducedModel.getUsername()){
            if(turnCardsPlayed.containsKey(username)) {
                int id = turnCardsPlayed.get(username);
                lastCardMap.get(username).setImage(new Image(String.valueOf(getClass().getResource("/assets/assistenti/Assistente" + (id) + ".png"))));
            }
        }

    }

    /**
     * Asks the user to move mother nature
     */
    public void askMotherNature() {
        currentPhase = PhaseType.MOTHER_NATURE;

        setCharacterClickable();
        turnInfo.setText("Select the island where you want to move mother nature");

        for(GridPane island : islands){
            island.setDisable(false);

            island.setOnMouseClicked(e -> {

                int steps = getSteps(island);

                if(steps <= (reducedModel).getMaxSteps() && steps >= 0) {
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

                    int index = islands.indexOf(island);

                    childrens = island.getChildren();

                    if(reducedModel.getReducedBoard().getIslands().get(index).isNoEntryTile()){
                        for (Node node : childrens) {
                            if(island.getRowIndex(node) != null && island.getColumnIndex(node) != null) {
                                if(island.getRowIndex(node) == 0 && island.getColumnIndex(node) == 0) {
                                    island.getChildren().remove(node);
                                }
                            }
                        }

                        reducedModel.getReducedBoard().getIslands().get(index).setNoEntryTile(false);
                    }

                    new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateMotherNature(steps))).start();

                    disableMotherNatureClickable();
                }
                else {
                    turnInfo.setText("Invalid move! Retry");
                }
            });
        }

    }

    /**
     * Returns the steps made from mother nature after moving it
     * @param island the island where mother nature is now
     * @return the steps made from mother nature after moving it
     */
    private int getSteps(GridPane island) {
        int steps = islands.indexOf(island) - reducedModel.getReducedBoard().getMotherNature();

        if(steps < 0){
            steps += reducedModel.getReducedBoard().getIslands().size();
        }

        return steps;
    }

    /**
     * Disables the islands after moving mother nature
     */
    private void disableMotherNatureClickable() {
        for(GridPane island : islands){
            island.setDisable(true);
        }
    }

    /**
     * Asks the user to select the cloud where he wants to get the students from
     */
    public void askCloud() {
        currentPhase = PhaseType.CLOUD;

        turnInfo.setText("Choose the cloud you want to get the students from");

        setCharacterClickable();
        setCloudClickable(true);

        for(ImageView cloud : cloudsImage){
            cloud.setOnMouseClicked(e -> {
                Node node = (Node) e.getTarget();

                int index = cloudsImage.indexOf((ImageView) node);

                new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateCloud(index))).start();

                setCloudClickable(false);

                ObservableList<Node> childrens = cloudsPane.get(index).getChildren();

                for(Node student : childrens){
                    if( ((ImageView)student).getImage() != null) {
                        String url = ((ImageView) student).getImage().getUrl();
                        refillEntrance(url);
                    }
                }

                cloudsPane.get(index).getChildren().clear();

                disableCharacterClickable();

                currentPhase = PhaseType.NOT_MYTURN;

                turnInfo.setText("Wait... other players are playing their turn");
            });
        }
    }

    /**
     * Refills the entrance with the given student
     * @param url the url of the image associated to the student
     */
    private void refillEntrance(String url) {
        for(int i = 0; i < entrance.size(); i++){
            if(entrance.get(i).getImage() == null){
                entrance.get(i).setImage(new Image(url));

                i = entrance.size();
            }
        }
    }

    /**
     * Sets the cloud clickable to select a cloud to get the student from
     * @param b true if you want to set the clouds clickable or else false
     */
    private void setCloudClickable(boolean b) {
        if(b) {
            cloudImage1.getStyleClass().set(0, "clickable");
            cloudImage2.getStyleClass().set(0, "clickable");

            if(reducedModel.getUsername().size() == 3){
                cloud3.getStyleClass().set(0, "clickable");
            }
        } else {
            cloudImage1.getStyleClass().set(0, "");
            cloudImage2.getStyleClass().set(0, "");

            if(reducedModel.getUsername().size() == 3){
                cloud3.getStyleClass().set(0, "");
            }
        }
    }

    /**
     * Changes the professor owner
     * @param professorOwner the new professor owner
     * @param color the color of the new professor owner
     */
    public void setProf(String professorOwner, Student color) {
        professors.get(getIntFromStudent.get(color)).setText(" : " + professorOwner);
        if (professorOwner.equals(reducedModel.getPlayerUsername())){
            boardProf.get(positionMap.get(color)).setImage(new Image(professorImages.get(color)));
        }
        else {
            boardProf.get(positionMap.get(color)).setImage(null);
        }
    }

    /**
     * Merges the 2 given island and shows the merge
     * @param island1 the first island to merge
     * @param island2 the second island to merge
     */
    public void mergeIslands(int island1, int island2) {
        reducedModel.getReducedBoard().mergeIslands(island1, island2);

        for(GridPane island : islands){
            island.setDisable(false);
        }

        if(island1 >= reducedModel.getReducedBoard().getIslands().size()){
            island1 = island1 - reducedModel.getReducedBoard().getIslands().size();
        }

        int numIslands = reducedModel.getReducedBoard().getIslands().get(island1).getNumIslands();

        GridPane island = islands.get(island1);
        island.getChildren().clear();

        islands.remove(island1);

        Label numIslandsLabel = new Label("Num Islands: " + numIslands);

        numIslandsLabel.setMinWidth(150);
        numIslandsLabel.setMinHeight(35);

        int motherNature = reducedModel.getReducedBoard().getMotherNature();
        islands.get(motherNature).add(numIslandsLabel, 2, 6);

        updateIslands();
    }

    /**
     * Notifies that an island has been conquered
     * @param island the conquered island
     * @param color the tower color of the conquerer
     */
    public void conquerIsland(int island, String color) {
        GridPane islandOwned = islands.get(island);
        int j = 0;

        ObservableList<Node> childrens = islandOwned.getChildren();

        for (Node node : childrens) {
            if(islandOwned.getRowIndex(node) != null && islandOwned.getColumnIndex(node) != null) {
                if(islandOwned.getRowIndex(node) == 0 && islandOwned.getColumnIndex(node) == 4) {
                    islandOwned.getChildren().remove(node);
                }
            }
        }

        if(color.equals(String.valueOf(reducedModel.getColor()))) {
            childrens = playerBoardTowers.getChildren();

            for (int i = 0; i < childrens.size(); i++) {
                Node node = childrens.get(i);
                ImageView imageView = (ImageView) node;

                if (imageView.getImage() != null) {
                    imageView.setImage(null);
                    i = childrens.size();
                }
            }
        }

        islandOwned.add(new ImageView((new Image(towerImages.get(TowerColor.valueOf(color))))), 0, 4);
    }

    /**
     * Called on the mouse click on the quit button
     * @param event
     */
    @FXML
    public void quit (MouseEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setContentText("Do you want to leave the game?");

        if (alert.showAndWait().get()== ButtonType.OK){
            stage = (Stage) boardPane.getScene().getWindow();
            System.out.println("You have quit the game");
            stage.close();

            new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateDisconnect())).start();
        }

    }

    /**
     * Method called when the client received an update islands message and show the updated islands to the user
     */
    public void updateIslands(){
        int i = 0;

        for(GridPane island : islands ) {
            int numStudents[] = reducedModel.getReducedBoard().getIslands().get(i).getNumStudents();
            int j = 0;
            ObservableList<Node> childrens = island.getChildren();

            int index = islands.indexOf(island);

            if(reducedModel.getReducedBoard().getIslands().get(index).isNoEntryTile()){
                island.add(new ImageView(new Image(String.valueOf(getClass().getResource("/assets/deny.png")))), 3, 4);
            }else{
                for (Node node : childrens) {
                    if(island.getRowIndex(node) != null && island.getColumnIndex(node) != null) {
                        if(island.getRowIndex(node) == 3 && island.getColumnIndex(node) == 4) {
                            ImageView image = (ImageView) node;

                            image.setImage(null);
                        }
                    }
                }
            }

            for (Student student : Student.values()) {
                for (Node node : childrens) {
                    if(island.getRowIndex(node) != null && island.getColumnIndex(node) != null) {
                        if(island.getRowIndex(node) == getIntFromStudent.get(student) && island.getColumnIndex(node) == 3) {
                            Label label = (Label) node;
                            label.setMinWidth(40);
                            label.setMinHeight(30);
                            if(numStudents[j] != 0) label.setText(" : " + numStudents[j]);
                            else label.setText(" : --");
                        }
                    }
                }

                j++;
            }

            Iterator<Node> iterator = childrens.iterator();
            final List<Node> removalCandidates = new ArrayList<>();

            while(iterator.hasNext()){
                Node node = iterator.next();

                if(island.getRowIndex(node) != null && island.getColumnIndex(node) != null) {
                    if(island.getRowIndex(node) == 1 && island.getColumnIndex(node) == 1) {
                        ImageView image = (ImageView) node;
                        if(image.getImage() != null) {
                            removalCandidates.add(node);
                        }
                    }
                }
            }

            island.getChildren().removeAll(removalCandidates);

            i++;
        }

        islands.get(reducedModel.getReducedBoard().getMotherNature()).add(new ImageView(new Image(String.valueOf(getClass().getResource("/assets/custom/motherNature.png")))), 1, 1);
    }

    /**
     * Notifies that a cloud has been unfilled
     * @param cloudId the id of the unfilled cloud
     */
    public void updateCloud(int cloudId) {

        ObservableList<Node> childrens =  cloudsPane.get(cloudId).getChildren();
        cloudsPane.get(cloudId).getChildren().clear();
    }

    /**
     * Sets the character clickable if the game is in expert mode
     */
    public void setCharacterClickable(){
        if(reducedModel.isExpertMode()) {
            for (ImageView character : charactersImages) {
                int index = charactersImages.indexOf(character);

                if (!reducedModel.getReducedCharacters()[index].isActive()) {
                    character.setDisable(false);
                    character.getStyleClass().set(0, "clickable");
                    character.setOnMouseClicked(e -> {
                        ImageView node = (ImageView) e.getTarget();

                        int effectId = reducedModel.getReducedCharacters()[charactersImages.indexOf(node)].getEffectId();


                        if (effectId == 4 && reducedModel.getNumCoins() >= reducedModel.getCharacterById(effectId).getCost()) {
                            reducedModel.setMaxSteps(reducedModel.getMaxSteps() + 2);
                        }
                        new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateCharacter(effectId))).start();
                    });
                }
            }
        }
    }

    /**
     * Disables the characters
     */
    public void disableCharacterClickable() {
        if (reducedModel.isExpertMode()) {
            for (ImageView character : charactersImages) {
                character.setDisable(true);
                character.getStyleClass().set(0, "");
            }
        }
    }

    /**
     * Method called when a user tries to activate a character that needs a student in input
     * @param effectId the id of the character card
     */
    public void askStudentEffect(int effectId) {
        int index = Arrays.asList(reducedModel.getReducedCharacters()).indexOf(reducedModel.getCharacterById(effectId));

        switch (effectId){
            case 1 -> {
                turnInfo.setText("Select a student you want to move to an Island");

                for(Node node : charactersPanes.get(index).getChildren()){
                    node.getStyleClass().set(0, "clickable");

                    node.setOnMouseClicked(e -> {
                        ImageView student = (ImageView) e.getTarget();

                        charactersPanes.get(index).getChildren().remove(node);

                        Student selectedStudent = imagesStudent.get(student.getImage().getUrl());
                        effectStudentUrl = student.getImage().getUrl();

                        new Thread (() -> notifyObserver(viewObserver -> viewObserver.onUpdateStudentEffect(String.valueOf(selectedStudent), effectId))).start();
                    });
                }
            }

            case 7 -> {
                AtomicInteger count = new AtomicInteger();
                ArrayList<Student> students = new ArrayList<>();

                turnInfo.setText("Select a student from the card...Switch max 3 students ");

                for(Node node : charactersPanes.get(index).getChildren()){
                    node.getStyleClass().set(0, "clickable");
                    node.setDisable(false);

                    node.setOnMouseClicked(e -> {
                        turnInfo.setText("Select the student from entrance you want to switch");
                        ImageView student = (ImageView) e.getTarget();


                        charactersPanes.get(index).getChildren().remove(node);

                        students.add(imagesStudent.get(student.getImage().getUrl()));

                        charactersPanes.get(index).getChildren().remove(node);

                        for(Node n : charactersPanes.get(index).getChildren()){
                            n.setDisable(true);
                        }

                        for (ImageView singleStud : entrance) {
                            singleStud.getStyleClass().set(0, "clickable");

                            singleStud.setOnMouseClicked(e1 -> {
                                turnInfo.setText("Select a student from the card...Switch max 3 students ");

                                Node entranceNode = (Node) e1.getTarget();

                                ImageView entranceStud = (ImageView) entranceNode;

                                students.add(imagesStudent.get(entranceStud.getImage().getUrl()));

                                String studentString = String.valueOf(imagesStudent.get(entranceStud.getImage().getUrl()));

                                moveStudentParameters.add(studentString);

                                entranceStud.setImage(student.getImage());

                                student.setImage(new Image(entranceStud.getImage().getUrl()));

                                disableStudents();

                                for(Node n : charactersPanes.get(index).getChildren()){
                                    n.setDisable(false);
                                }

                                count.getAndIncrement();

                                if(count.get() == 3){
                                    new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateSwitchStudents(students, 7))).start();

                                    for(Node n : charactersPanes.get(index).getChildren()){
                                        n.setDisable(true);
                                    }

                                    confirmBtn.setDisable(true);
                                    confirmBtn.setOpacity(0);
                                }

                            });
                        }
                    });

                    confirmBtn.setDisable(false);
                    confirmBtn.setOpacity(1);

                    confirmBtn.setOnMouseClicked(e -> {
                        new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateSwitchStudents(students, 7))).start();

                        confirmBtn.setDisable(true);
                        confirmBtn.setOpacity(0);

                        for(Node n : charactersPanes.get(index).getChildren()){
                            n.setDisable(true);
                        }
                    });
                }

            }

            case 9,12 -> {
                turnInfo.setText("Click on a professor, his color will be the selected color");

                for(ImageView color : colors){
                    color.setDisable(false);
                    color.getStyleClass().set(0, "clickable");

                    color.setOnMouseClicked(e -> {

                        String url = color.getImage().getUrl();

                        Student selectedColor = imagesProfessor.get(url);

                        color.setDisable(true);
                        color.getStyleClass().set(0, "");

                        new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateStudentEffect(String.valueOf(selectedColor), effectId))).start();
                    });
                }
            }

            case 11 -> {
                turnInfo.setText("Select a student you want to move to the dinner room");

                for(Node node : charactersPanes.get(index).getChildren()){
                    node.setDisable(false);
                    node.getStyleClass().set(0, "clickable");

                    node.setOnMouseClicked(e -> {

                        ImageView student = (ImageView) e.getTarget();

                        Student selectedStudent = imagesStudent.get(student.getImage().getUrl());

                        dinnerRoom.get(positionMap.get(selectedStudent)).getChildren().add(student);


                        for(Node n : charactersPanes.get(index).getChildren()){
                            n.setDisable(true);
                        }


                        new Thread (() -> notifyObserver(viewObserver -> viewObserver.onUpdateStudentEffect(String.valueOf(selectedStudent), effectId))).start();
                    });
                }
            }

        }
    }

    /**
     * Method called when a user tries to activate a character that needs an island in input
     * @param effectId the id of the character card
     */

    public void askIslandEffect(int effectId) {
        turnInfo.setText("Select the island you want");

        switch(effectId){
            case 1 -> {
                for(GridPane island : islands){
                    island.setDisable(false);

                    island.setOnMouseClicked(e -> {
                        Node source = (Node)e.getSource() ;

                        reducedModel.getReducedBoard().getIslands().get(islands.indexOf(island)).addStudent(imagesStudent.get(effectStudentUrl));

                        updateIsland(island, imagesStudent.get(effectStudentUrl));

                        for(GridPane is : islands){
                            is.setDisable(true);
                        }

                        new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateIslandEffect(islands.indexOf(island), effectId))).start();
                    });
                }
            }
            case 5 -> {
                int index = Arrays.asList(reducedModel.getReducedCharacters()).indexOf(reducedModel.getCharacterById(5));

                charactersPanes.get(index).getChildren().remove(charactersPanes.get(index).getChildren().size() - 1);

                for(GridPane island : islands){
                    island.setDisable(false);

                    island.setOnMouseClicked(e -> {
                        island.add(new ImageView(new Image(String.valueOf(getClass().getResource("/assets/deny.png")))), 0, 0);

                        int index1 = islands.indexOf(island);

                        reducedModel.getReducedBoard().getIslands().get(index1).setNoEntryTile(true);

                        disabledIsland = index1;

                        new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateIslandEffect(islands.indexOf(island), effectId))).start();
                    });
                }
            }
            default -> {
                for(GridPane island : islands){
                    island.setDisable(false);

                    island.setOnMouseClicked(e -> {
                        new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateIslandEffect(islands.indexOf(island), effectId))).start();
                    });
                }
            }
        }
    }

    /**
     * Method called when a user tries to activate a character that needs a list of students to switch in input
     */
    public void askSwitch() {
        boolean test = false;

        for(TilePane dinner : dinnerRoom){
            if(!dinner.getChildren().isEmpty()) test = true;
        }

        if(test){
            turnInfo.setText("Select a student from entrance");

            ArrayList<Student> students = new ArrayList<>();

            AtomicInteger numStudents = new AtomicInteger();

            for (ImageView singleStud : entrance) {
                singleStud.getStyleClass().set(0, "clickable");

                singleStud.setOnMouseClicked(e -> {
                    Node node = (Node) e.getTarget();

                    ImageView student = (ImageView) node;

                    Student selectedStud = imagesStudent.get(student.getImage().getUrl());

                    students.add(selectedStud);

                    String url = student.getImage().getUrl();

                    addStudentToDinner(dinnerRoom.get(positionMap.get(imagesStudent.get(url))), url);

                    if((dinnerRoom.get(positionMap.get(imagesStudent.get(url))).getChildren().size() == 3 ||
                            dinnerRoom.get(positionMap.get(imagesStudent.get(url))).getChildren().size() == 6 ||
                            dinnerRoom.get(positionMap.get(imagesStudent.get(url))).getChildren().size() == 9) && reducedModel.isExpertMode()){
                        reducedModel.addCoin();
                        coins.setText(String.valueOf(reducedModel.getNumCoins()));
                    }

                    turnInfo.setText("Select a dinner");

                    student.setImage(null);

                    numStudents.getAndIncrement();

                    if(numStudents.get() == 2) {
                        disableStudents();
                        numStudents.set(0);
                    }
                    for(TilePane dinner : dinnerRoom){
                        dinner.setDisable(false);


                        dinner.setOnMouseClicked(e1 -> {
                            if(!dinner.getChildren().isEmpty()){
                                dinner.getChildren().remove(dinner.getChildren().size() - 1);

                                for(TilePane din : dinnerRoom){
                                    din.setDisable(true);
                                }

                                new Thread(() -> notifyObserver(viewObserver -> viewObserver.onUpdateSwitchStudents(students, 10))).start();

                            }
                            else{
                                showAlert("Select another dinner that is not empty!");
                            }
                        });
                    }


                });
            }
        }
        else {
            showAlert("Your dinner room is empty!");
        }


    }

    /**
     * Shows the students on the character card
     */
    public void updateCharacterStudents() {
        for(int i = 0; i < 3; i++){
            charactersPanes.get(i).getChildren().clear();
            if(reducedModel.getReducedCharacters()[i].getStudents() != null){
                for(Student student : reducedModel.getReducedCharacters()[i].getStudents()){
                    charactersPanes.get(i).getChildren().add(new ImageView(new Image(studentsImages.get(student))));
                }
            }
        }
    }

    /**
     * Notifies to the user that a card has been activated or is no more active
     * @param effectId the id of the character card
     * @param activated true if the card is active or else false
     * @param owner the username of the player that activated the character
     */
    public void characterIsActivated(int effectId, boolean activated, String owner) {
        if(activated){
            reducedModel.activateCharacter(effectId);

            int index = Arrays.asList(reducedModel.getReducedCharacters()).indexOf(reducedModel.getCharacterById(effectId));

            charactersImages.get(index).setX(1.5);
            charactersImages.get(index).setY(1.5);

            charactersImages.get(index).setStyle("-fx-effect: dropshadow(gaussian, #f2e0d6, 20, 0.3, 0, 0);");

            reducedModel.setNumCoins(reducedModel.getNumCoins() - reducedModel.getCharacterById(effectId).getCost());

            owners.get(index).setText(owner);

            coins.setText(String.valueOf(reducedModel.getNumCoins()));

            reducedModel.getCharacterById(effectId).setCost(reducedModel.getCharacterById(effectId).getCost() + 1);

            if(currentPhase != null && !currentPhase.equals(PhaseType.NOT_MYTURN)) setCharacterClickable();

            resumePhase();
        }
        else {
            if(effectId == 5){

                GridPane island = islands.get(disabledIsland);
                ObservableList<Node> childrens = island.getChildren();

                final List<Node> removalCandidates = new ArrayList<>();

                for(Node node : childrens){
                    if(island.getRowIndex(node) != null && island.getColumnIndex(node) != null) {
                        if(island.getRowIndex(node) == 0 && island.getColumnIndex(node) == 0) {
                            ImageView image = (ImageView) node;
                            if(image.getImage() != null) {
                                removalCandidates.add(node);
                            }
                        }
                    }
                }

                island.getChildren().removeAll(removalCandidates);

                disabledIsland = -1;
            }

            int index = Arrays.asList(reducedModel.getReducedCharacters()).indexOf(reducedModel.getCharacterById(effectId));

            reducedModel.getReducedCharacters()[index].disactivate();

            charactersImages.get(index).setX(1);
            charactersImages.get(index).setY(1);

            charactersImages.get(index).setStyle("");

            owners.get(index).setText("");
        }
    }

    /**
     * Method called after a character has been activated to resume the game
     */
    private void resumePhase() {
        if(currentPhase != null){
            switch (currentPhase){
                case CARD -> turnInfo.setText("It's your turn! Select an assistant card to play");
                case MOVE_STUDENT -> turnInfo.setText("Select a student from entrance");
                case CLOUD -> turnInfo.setText("Choose the cloud you want to get the students from");
                case MOTHER_NATURE -> askMotherNature();
                case NOT_MYTURN -> turnInfo.setText("Wait...other players are playing their turn");
            }
        }

    }

    /**
     * Notifies the character 12 has been activated and removes the students
     * @param color the color of the students to remove
     */
    public void askEffect12Students(Student color) {
        TilePane dinner = dinnerRoom.get(positionMap.get(color));

        int numStudents = dinner.getChildren().size();

        ObservableList<Node> childrens = dinner.getChildren();

        for(int i = childrens.size() - 1; i > childrens.size() - numStudents + 1; i--){
            Node node = childrens.get(i);

            dinner.getChildren().remove(node);
        }

        turnInfo.setText(numStudents + " students have been taken from the dinner! ");
    }

    /**
     * Updates mother nature after a player move
     */
    public void updateMotherNature() {
        int i = 0;

        for(GridPane island : islands ) {
            ObservableList<Node> childrens = island.getChildren();

            Iterator<Node> iterator = childrens.iterator();
            final List<Node> removalCandidates = new ArrayList<>();

            while(iterator.hasNext()){
                Node node = iterator.next();

                if(island.getRowIndex(node) != null && island.getColumnIndex(node) != null) {
                    if(island.getRowIndex(node) == 1 && island.getColumnIndex(node) == 1) {
                        ImageView image = (ImageView) node;
                        if(image.getImage() != null) {
                            removalCandidates.add(node);
                        }
                    }
                }
            }

            island.getChildren().removeAll(removalCandidates);

            i++;
        }

        islands.get(reducedModel.getReducedBoard().getMotherNature()).add(new ImageView(new Image(String.valueOf(getClass().getResource("/assets/custom/motherNature.png")))), 1, 1);
    }

    /**
     * Shows the disconnection message
     * @param username the user that disconnected
     */
    public void showDisconnection(String username) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Disconnection");
        alert.setContentText("Game has ended because user " + username + " disconnected");

        alert.showAndWait();

        Platform.exit();
    }

    /**
     * Shows an error alert with the given message
     * @param message the message to show
     */
    public void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void showWin(String winner) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Win");
        alert.setContentText( winner + " has won! ");

        alert.showAndWait();
    }
}

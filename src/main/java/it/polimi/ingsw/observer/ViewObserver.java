
package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Movable;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;

import java.util.ArrayList;
import java.util.List;


public interface ViewObserver {

    void onUpdateServerInfo(List<String> serverInfo);

    void onUpdateCreateOrJoin(int choice);

    void onUpdateCharacter(int effectId);

    void onUpdateCloud(int cloudId);

    void onUpdateTowerColor(String chosenTowerColor);

    void onUpdateDisconnect();

    void onUpdateIslandEffect(int chosenIsland);

    void onUpdateJoinGame(int gameId);

    void onUpdateLoginMessage(String username);

    void onUpdateMotherNature(int steps);

    void onUpdateStudent(String from, String color, String to, int id);

    void onUpdateNewGame(int maxPlayers, boolean expertMode);

    void onUpdateCard(int assistantCardId);

    void onUpdateReloadGame();

    void onUpdateStudentEffect(String chosenStudent);

    void onUpdateIslandEffect(int chosenIsland, int effectId);

    void onUpdateStudentEffect(String chosenStudent, int effectId);


    void onUpdateSwitchStudents(ArrayList<Student> students);

    void onUpdateSwitchStudents(ArrayList<Student> students, int effectId);

    void backToChoice();
}

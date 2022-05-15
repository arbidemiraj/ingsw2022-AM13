
package it.polimi.ingsw.observer;

import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Movable;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;




public interface ViewObserver {

    void onUpdateCharacter(int effectId);

    void onUpdateCloud(int cloudId);

    void onUpdateTowerColor(TowerColor chosenTowerColor);

    void onUpdateDisconnect();

    void onUpdateIslandEffect(Island chosenIsland);

    void onUpdateJoinGame(int gameId);

    void onUpdateLoginMessage();

    void onUpdateMotherNature(int steps);

    void onUpdateStudent(Movable from, Student color, Movable to);

    void onUpdateNewGame(int maxPlayers, boolean expertMode);

    void onUpdateCard(int assistantcardId);

    void onUpdateReloadGame();

    void onUpdateStudentEffect(Student chosenStudent);


}

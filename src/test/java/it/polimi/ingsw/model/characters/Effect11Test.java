package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Effect11Test {

    @Test
    void apply() {
        Game game = new Game(2, false);
        game.getBoard().prepareGame();
        game.getBoard().addPlayer("FirstPlayer", TowerColor.WHITE);

        game.setCurrentPlayer(0);
        Player player = game.getBoard().getPlayers().get(game.getCurrentPlayer());

        Effect11 effect11 = new Effect11(game.getBoard().extractStudents(4));
        Student chosenStudent = effect11.getStudents().get(0);
        effect11.apply(game, chosenStudent);

        assertTrue(player.getPlayerBoard().getEntrance().contains(chosenStudent));
        assertTrue(effect11.getStudents().size() == 4);
    }
}
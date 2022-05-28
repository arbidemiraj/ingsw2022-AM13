package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.exceptions.EmptyBagException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Effect11Test {

    @Test
    void apply() {
        Game game = new Game(2, false);
        game.getBoard().prepareGame();
        game.addPlayer("FirstPlayer");

        game.setCurrentPlayer(0);
        Player player = game.getPlayers().get(game.getCurrentPlayer());

        Effect11 effect11 = null;
        try {
            effect11 = new Effect11(game.getBoard().extractStudents(4));
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }
        Student chosenStudent = effect11.getStudents().get(0);

        try {
            effect11.apply(game, chosenStudent);
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }

        assertTrue(player.getPlayerBoard().getEntrance().contains(chosenStudent));
        assertTrue(effect11.getStudents().size() == 4);
    }
}
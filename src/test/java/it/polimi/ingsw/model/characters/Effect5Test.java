package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.enumerations.TowerColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Effect5Test {

    @Test
    void apply() {
        Game game = new Game(2, false);
        game.getBoard().prepareGame();
        game.addPlayer("FirstPlayer", TowerColor.WHITE);
        Player player = game.getPlayers().get(0);

        Effect5 effect5 = new Effect5();
        effect5.apply(game, game.getBoard().getMotherNatureIsland());

        assertTrue(game.getBoard().getMotherNatureIsland().isNoEntryTile());
        assertEquals(3, effect5.getNumEntryTiles());

        game.getBoard().getMotherNatureIsland().setOwner(player);
        game.influence(game.getBoard().getMotherNatureIsland());

        assertEquals(0, player.getInfluenceValue());
    }
}
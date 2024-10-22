package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.enumerations.Student;

/**
 * The effect 12 class
 */
public class Effect12 implements Actionable{
    @Override
    public void apply(Game game) {

    }

    @Override
    public void apply(Game game, Student chosenStudent) {
        game.putStudentInBag(chosenStudent);
    }

    @Override
    public void apply(Game game, Island chosenIsland) {

    }
}

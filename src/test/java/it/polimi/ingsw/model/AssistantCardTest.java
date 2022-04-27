package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssistantCardTest {

    @Test
    void getValue() {
        AssistantCard assistantCard = new AssistantCard(2, 2);

        assertEquals(2, assistantCard.getValue());
    }

    @Test
    void getMaxMotherNatureMoves() {
        AssistantCard assistantCard = new AssistantCard(2, 2);

        assertEquals(2, assistantCard.getMaxMotherNatureMoves());
    }
}
package it.polimi.ingsw.network.client.reducedModel;

import java.io.Serial;
import java.io.Serializable;

public class ReducedCharacter implements Serializable {
    @Serial
    private static final long serialVersionUID = 4685160795889445445L;
    private int cost;
    private int effectId;
    private String characterDesc;

    public ReducedCharacter(int cost, int effectId, String characterDesc) {
        this.cost = cost;
        this.effectId = effectId;
        this.characterDesc = characterDesc;
    }

    public int getCost() {
        return cost;
    }

    public int getEffectId() {
        return effectId;
    }

    public String getCharacterDesc() {
        return characterDesc;
    }
}

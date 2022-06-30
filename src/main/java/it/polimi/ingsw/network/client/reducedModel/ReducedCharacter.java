package it.polimi.ingsw.network.client.reducedModel;

import it.polimi.ingsw.model.enumerations.Student;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class ReducedCharacter implements Serializable {
    @Serial
    private static final long serialVersionUID = 4685160795889445445L;
    private int cost;
    private int effectId;
    private String characterDesc;
    private boolean isActive;
    private ArrayList<Student> students;

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

    public void activate() {
        isActive = true;
    }

    public void setStudents(ArrayList<Student> students){
        this.students = students;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public boolean isActive() {
        return isActive;
    }

    public void disactivate() {
        isActive = false;
    }
}

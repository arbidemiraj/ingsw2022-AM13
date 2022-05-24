package it.polimi.ingsw.network.client.reducedModel;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.maps.IntColorMap;
import it.polimi.ingsw.view.cli.Color;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;

public class ReducedPlayerBoard implements Serializable {
    @Serial
    private static final long serialVersionUID = -7603520844991327878L;
    private int[] entranceStudents;
    private int[] hallStudents;
    private IntColorMap studentColorMap = new IntColorMap();
    private HashMap<Integer, Student> studentColor = studentColorMap.getMap();

    public ReducedPlayerBoard(int[] entranceStudents, int[] hallStudents) {
        this.entranceStudents = entranceStudents;
        this.hallStudents = hallStudents;
    }

    public void addEntranceStudent(){

    }

    public void removeEntranceStudent(){

    }

    public void addHallStudent(){

    }

    public void removeHallStudent(){

    }

    public String print(){
        String pb = "";
        pb += Color.ANSI_YELLOW + "\n---------------------------------------------------------------------\n" +
                "| Y: ";
        for(int i = 0; i < entranceStudents[0]; i++) pb+= "*";
        pb+= " |   Y: ";
        for(int i = 0; i < hallStudents[0]; i++) pb+= "*";

        pb+= Color.ANSI_BLUE + "\n---------------------------------------------------------------------\n";
        pb+="| B: ";
        for(int i = 0; i < entranceStudents[1]; i++) pb+= "*";
        pb+= " |   B: ";
        for(int i = 0; i < hallStudents[1]; i++) pb+= "*";

        pb+="\n---------------------------------------------------------------------\n";

        pb+= Color.ANSI_GREEN + "| G: ";
        for(int i = 0; i < entranceStudents[2]; i++) pb+= "*";
        pb+= " |   G: ";
        for(int i = 0; i < hallStudents[2]; i++) pb+= "*";

        pb+="\n---------------------------------------------------------------------\n";

        pb+= Color.ANSI_PINK + "| P: ";
        for(int i = 0; i < entranceStudents[3]; i++) pb+= "*";
        pb+= " |   P: ";
        for(int i = 0; i < hallStudents[3]; i++) pb+= "*";

        pb+="\n---------------------------------------------------------------------\n";

        pb+= Color.ANSI_RED + "| R: ";
        for(int i = 0; i < entranceStudents[4]; i++) pb+= "*";
        pb+= " |   R: ";
        for(int i = 0; i < hallStudents[4]; i++) pb+= "*";

        pb+="\n---------------------------------------------------------------------\n" + Color.RESET ;


        return pb;
    }


}

package it.polimi.ingsw.network.client.reducedModel;

import it.polimi.ingsw.model.enumerations.Student;
import it.polimi.ingsw.model.maps.ColorIntMap;
import it.polimi.ingsw.model.maps.IntColorMap;
import it.polimi.ingsw.view.cli.Color;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ReducedPlayerBoard implements Serializable {
    @Serial
    private static final long serialVersionUID = -7603520844991327878L;
    private int[] entranceStudents;
    private int[] hallStudents;
    private int numTowers;
    private IntColorMap studentColorMap = new IntColorMap();
    private HashMap<Integer, Student> studentColor = studentColorMap.getMap();
    private ColorIntMap intColorMap = new ColorIntMap();
    private HashMap<Student, Integer> intColor = intColorMap.getMap();
    private ArrayList<Student> students = new ArrayList<>();
    private final String LINE = "\n---------------------------------------------------------------------\n";

    public ReducedPlayerBoard(int[] entranceStudents, int[] hallStudents, int numTowers) {
        this.entranceStudents = entranceStudents;
        this.hallStudents = hallStudents;
        this.numTowers = numTowers;

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < entranceStudents[i]; j++){
                students.add(studentColor.get(i));
            }
        }

    }

    public int getNumTowers() {
        return numTowers;
    }

    public void setNumTowers(int numTowers) {
        this.numTowers = numTowers;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public int[] getHallStudents() {
        return hallStudents;
    }

    public void addEntranceStudent(String student){
        entranceStudents[intColor.get(Student.valueOf(student))]++;
    }

    public void removeEntranceStudent(String student){
        entranceStudents[intColor.get(Student.valueOf(student))]--;
    }

    public void addHallStudent(String student){
        hallStudents[intColor.get(Student.valueOf(student))]++;
    }

    public void removeHallStudent(String student){
        hallStudents[intColor.get(Student.valueOf(student))]--;
    }

    public String print(){
        String pb = "";

        pb += Color.ANSI_YELLOW + LINE +
                "| Y: ";
        for(int i = 0; i < entranceStudents[0]; i++) pb+= "*";
        pb+= " |   Y: ";
        for(int i = 0; i < hallStudents[0]; i++) pb+= "*";

        pb+= Color.ANSI_BLUE + LINE;
        pb+="| B: ";
        for(int i = 0; i < entranceStudents[1]; i++) pb+= "*";
        pb+= " |   B: ";
        for(int i = 0; i < hallStudents[1]; i++) pb+= "*";

        pb+=LINE;

        pb+= Color.ANSI_GREEN + "| G: ";
        for(int i = 0; i < entranceStudents[2]; i++) pb+= "*";
        pb+= " |   G: ";
        for(int i = 0; i < hallStudents[2]; i++) pb+= "*";

        pb+=LINE;

        pb+= Color.ANSI_PINK + "| P: ";
        for(int i = 0; i < entranceStudents[3]; i++) pb+= "*";
        pb+= " |   P: ";
        for(int i = 0; i < hallStudents[3]; i++) pb+= "*";

        pb+=LINE;

        pb+= Color.ANSI_RED + "| R: ";
        for(int i = 0; i < entranceStudents[4]; i++) pb+= "*";
        pb+= " |   R: ";
        for(int i = 0; i < hallStudents[4]; i++) pb+= "*";

        pb+=LINE + Color.RESET ;


        return pb;
    }


}

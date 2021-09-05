package com.company.server.processing.collection_manage;

import com.company.server.InputDevice;
import com.company.common.collection_objects.*;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;


public class CollectionManagement {
    private LinkedHashSet<StudyGroup> collection;
    public LocalDateTime CreationDate = LocalDateTime.now();
    private PrintStream out;

    public CollectionManagement(PrintStream printStream) {
        this.collection = new LinkedHashSet<>();
        out = printStream;
    }

    public CollectionManagement() {
        this(System.out);
    }

    public void setPrintStream(PrintStream printStream) {
        this.out = printStream;
    }

    public LinkedHashSet<StudyGroup> getCollection() {
        return collection;
    }

    public void clear() {
        collection.clear();
        out.println("Произошла очистка коллекции");
    }
    public String info() {
        String Info;
        Info = this.getClass().getTypeName();
        Info += " | ";
        Info += this.CreationDate;
        Info += " | ";
        Info += String.valueOf(this.collection.size());
        Info += " | ";
        return Info;
    }

    public void show() {
        for (StudyGroup studyGroup : collection) {
            out.println(
                    "StudyGroup " + '\n' +
                            "id: " + studyGroup.getid() + '\n' +
                            "StudyCounter: " + studyGroup.getStudentsCount() + '\n' +
                            "Coordinates" + '\n' + " X: " + studyGroup.getCoordinates().getX() + '\n' + " Y: " + studyGroup.getCoordinates().getY() + '\n' +
                            "Admin Name: " + studyGroup.getGroupAdmin().getName() + '\n' + "Admin Passport: " + studyGroup.getGroupAdmin().getPassportID() + '\n' + "Admin Location: " + '\n' + "X: " + studyGroup.getGroupAdmin().getLocation().getX() + '\n' + "Y: " + studyGroup.getGroupAdmin().getLocation().getY() + '\n' + "Z: " + studyGroup.getGroupAdmin().getLocation().getZ() + '\n' +
                            "Creation Date: " + studyGroup.getCreationDate() + '\n' +
                            "Name: " + studyGroup.getName() + '\n' +
                            "Form of education: " + studyGroup.getFormOfEducation() + '\n' +
                            "Semester: " + studyGroup.getSemesterEnum());
        }
    }

    public void add() {
        collection.add(InputDevice.input());
    }

    public void add(String CommandArgs) {
        collection.add(InputDevice.inputFromFile(CommandArgs));
    }

    public void add(StudyGroup group) {
        collection.add(group);
        out.println("В коллекцию добавлена группа: " + group);
    }

    public void edit() {
        //TODO
        //InputDevice.edit();
    }
    public void edit(StudyGroup studyGroup, String CommandArgs) {
        InputDevice.editFromFile(studyGroup, CommandArgs);
    }

}


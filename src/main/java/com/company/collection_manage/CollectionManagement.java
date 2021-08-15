package com.company.collection_manage;

import com.company.validation.InputDevice;
import com.company.collection_objects.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;


public class CollectionManagement {
    private LinkedHashSet<StudyGroup> collection;
    public LocalDateTime CreationDate = LocalDateTime.now();

    public CollectionManagement() {
        this.collection = new LinkedHashSet<>();
    }

    public LinkedHashSet<StudyGroup> getCollection() {
        return collection;
    }

    public void clear() {
        collection.clear();
        System.out.println("Произошла очистка коллекции");
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
            System.out.println(
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
        collection.add(new InputDevice().add());
    }
    public void add(String CommandArgs) {
        collection.add(new InputDevice().addFromFile(CommandArgs));
    }

    InputDevice device = new InputDevice();
    public void edit() {
        device.edit(this);
    }
    public void edit(StudyGroup studyGroup, String CommandArgs) {
        device.EditFromFile(studyGroup, CommandArgs);
    }

}


package com.company.common.commands;

import com.company.server.processing.collection_manage.CollectionManagement;
import com.company.common.collection_objects.StudyGroup;

import java.util.LinkedHashSet;

public class ShowCommand extends AbstractCommand {
    private final CollectionManagement collectionManagement;
    private LinkedHashSet<StudyGroup> collection;
    public ShowCommand(CollectionManagement collectionManagement) {
        this.collectionManagement = collectionManagement;
    }

    @Override
    public String execute(String CommandArgs) {
        StringBuilder showStr = new StringBuilder();
        for (StudyGroup studyGroup : collectionManagement.getCollection()) {
            showStr.append(
                    "\n\nStudyGroup \nid: ").append(studyGroup.getid())
                    .append("\nStudyCounter: ").append(studyGroup.getStudentsCount())
                    .append("\nCoordinates\n X: ").append(studyGroup.getCoordinates().getX())
                    .append("\n Y: ").append(studyGroup.getCoordinates().getY())
                    .append("\nAdmin Name: ").append(studyGroup.getGroupAdmin().getName())
                    .append("\nAdmin Passport: ").append(studyGroup.getGroupAdmin().getPassportID())
                    .append("\nAdmin Location:\n X: ").append(studyGroup.getGroupAdmin().getLocation().getX())
                    .append("\n Y: ").append(studyGroup.getGroupAdmin().getLocation().getY())
                    .append("\n Z: ").append(studyGroup.getGroupAdmin().getLocation().getZ())
                    .append("\nCreation Date: ").append(studyGroup.getCreationDate())
                    .append("\nName: ").append(studyGroup.getName())
                    .append("\nForm of education: ").append(studyGroup.getFormOfEducation())
                    .append("\nSemester: ").append(studyGroup.getSemesterEnum());
        }
        return showStr.toString();
    }

    @Override
    public String describe() {
        return ("Выводит в стандартный поток вывода все элементы коллекции в строковом представлении");
    }
}

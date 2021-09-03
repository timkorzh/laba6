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
    public void execute(String CommandArgs) {
        collectionManagement.show();
        for (StudyGroup studyGroup : collectionManagement.getCollection()) {
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
        return;
        //return "";
    }

    @Override
    public String describe() {
        return ("Выводит в стандартный поток вывода все элементы коллекции в строковом представлении");
    }
}

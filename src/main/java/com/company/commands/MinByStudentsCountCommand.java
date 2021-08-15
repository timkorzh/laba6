package com.company.commands;

import com.company.collection_objects.StudyGroup;
import com.company.collection_manage.CollectionManagement;

import java.util.Comparator;
import java.util.Optional;

public class MinByStudentsCountCommand extends AbstractCommand {
    CollectionManagement collectionManagement;
    public MinByStudentsCountCommand(CollectionManagement collectionManagement) {
        this.collectionManagement = collectionManagement;
    }
    @Override
    public void execute(String CommandArgs) {

        if(collectionManagement.getCollection().size() != 0 && collectionManagement.getCollection() != null) {

            Optional<StudyGroup> MINGroup = collectionManagement.getCollection().stream().min(Comparator.comparingInt(StudyGroup::getStudentsCount));

            MINGroup.ifPresent(studyGroup -> System.out.println("Группа с минимальным количеством студентов: " + studyGroup.getId()));
        }
        else {
            System.out.println("Не могу найти группу с минимальным количсетвом студентов, так как групп нет. Мне очень жаль(");
        }
    }
    @Override
    public String describe() {
        return ("Выводит любой объект из коллекции, значение поля studentsCount которого является минимальным");
    }
}

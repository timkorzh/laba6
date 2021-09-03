package com.company.validation;

import com.company.collection_manage.CollectionManagement;
import com.company.collection_objects.StudyGroup;

import java.util.InputMismatchException;
import java.util.stream.Stream;

public class CommandMethodsExecute {

    public enum RemoveMode {
        High,
        Low,
        Equals
    }

    public void remove(CollectionManagement collectionManagement, int RemoveById, RemoveMode Equals) {
        try {
            if (collectionManagement.getCollection().stream().noneMatch(a -> a.getId() == RemoveById)) {
                System.out.println("Ничего не нашёл по этому номеру:((");
                return;
            }
            switch (Equals) {
                case Equals : {collectionManagement.getCollection().removeIf(studyGroup -> RemoveById == studyGroup.getid());
                    System.out.println("Группа с id: " + RemoveById + " была успешно удалена! ~~~~~~~~~~~Помянем~~~~~~~~~~");}
                case Low : {collectionManagement.getCollection().removeIf(a -> a.getId() < RemoveById);
                    System.out.println("Группа с id ниже, чем: " + RemoveById + " была успешно удалена! ~~~~~~~~~~~Помянем~~~~~~~~~~");}
                case High : {collectionManagement.getCollection().removeIf(a -> a.getId() > RemoveById);
                    System.out.println("Группы с id выше, чем: " + RemoveById + " была успешно удалена! ~~~~~~~~~~~Помянем~~~~~~~~~~");}
            }

        } catch (InputMismatchException Ex) {
            System.out.println("Введите число");
        }

    }

    public void countFormOfEducation(CollectionManagement collectionManagement, Integer FormEducation) {

        FormOfEducationValidator I = new FormOfEducationValidator();
        if (I.IsValid(FormEducation.toString())) {
            long b = collectionManagement.getCollection().stream().filter(a -> a.getFormOfEducation().ordinal() > FormEducation).count();
            System.out.println("Количество элементов, значение поля formOfEducation которых больше заданного: " + b);
        } else {
            System.out.println(I.ErrorMessage());
        }
    }

    public void filterBySem(CollectionManagement collectionManagement, Integer Sem) {

        try {
            SemesterValidator I = new SemesterValidator();
            if (I.IsValid(Sem.toString())) {
                long Count = collectionManagement.getCollection().stream().filter(a -> a.getSemesterEnum().ordinal() == Sem).count();

                if( Count == 0) {
                    System.out.println("Нет элементов, значения поля semesterEnum которых равно зааднному");
                }
                else {
                    Stream<StudyGroup> b = collectionManagement.getCollection().stream().filter(a -> a.getSemesterEnum().ordinal() == Sem);
                    b.forEach(n -> System.out.println("Элементы, значение поля semesterEnum которых равно заданному: " + n.getId() + " " + n.getName()));
                }
            } else {
                System.out.println(I.ErrorMessage());
            }
        } catch (InputMismatchException Ex) {
            System.out.println("Введите число");
        }
    }

}

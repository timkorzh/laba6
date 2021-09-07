package com.company.common.collection_objects;

import java.io.*;
import java.time.LocalDateTime;

public class  StudyGroup implements Serializable {

//TODO: compareTo
    public StudyGroup() {
        this("", new Coordinates(), 0, null, Semester.FIRST, new Person());

    }

    public StudyGroup(String name, Coordinates coordinates, Integer studentsCount, FormOfEducation formOfEducation, Semester semester, Person admin) {
        this.name = name;
        this.coordinates = coordinates;
        this.studentsCount = studentsCount;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semester;
        this.groupAdmin = admin;
        creationDate = LocalDateTime.now();
        id = ++groupCounter;
    }

    public void setStudentsCount(Integer studentsCount) {
        this.studentsCount = studentsCount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        if (newName != null && !newName.trim().isEmpty()) {
            this.name = newName;
        } else {
            System.out.println("Не хулигань, пекарь.Поле не может быть null, Строка не может быть пустой");
        }
    }

    public int getid() {
        return id;
    }

    private static int groupCounter;

    public void updateId() {
        id = ++groupCounter;
    }

    public static int getGroupCounter() {
        return groupCounter;
    }


    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    private int id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Integer studentsCount;
    private FormOfEducation formOfEducation;
    private Semester semesterEnum;
    private Person groupAdmin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }

    public String toString() {
        return "StudyGroup " + '\n' +
                "id: " + id + '\n' +
                "StudyCounter: " + studentsCount + '\n' +
                "Coordinates" + '\n' + " X: " + coordinates.getX() + '\n' + " Y: " + coordinates.getY() + '\n' +
                "Admin Name: " + this.getGroupAdmin().getName() + '\n' + "Admin Passport: " + this.getGroupAdmin().getPassportID() + '\n' + "Admin Location: " + '\n' + "X: " + this.getGroupAdmin().getLocation().getX() + '\n' + "Y: " + this.getGroupAdmin().getLocation().getY() + '\n' + "Z: " + this.getGroupAdmin().getLocation().getZ() + '\n' +
                "Creation Date: " + creationDate + '\n' +
                "Name: " + name + '\n' +
                "Form of education: " + formOfEducation + '\n' +
                "Semester: " + semesterEnum;
    }


    public void getCreationDate(String nodeValue) {
        //TODO: попытаться убрать getCreationDate
    }
}

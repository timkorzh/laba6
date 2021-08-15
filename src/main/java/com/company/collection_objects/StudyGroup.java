package com.company.collection_objects;

import java.time.LocalDateTime;

public class StudyGroup {


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
        id = ++GroupCounter;
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

    public static int GroupCounter;



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


    public static int getGroupCounter() {
        return GroupCounter;
    }

    public Integer getStudentsCount() {
        return studentsCount;
    }


    public void getCreationDate(String nodeValue) {
    }
}

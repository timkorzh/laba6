package collection_objects;

import java.io.*;
import java.time.LocalDateTime;

public class StudyGroup implements Serializable {

//TODO: compareTo
    public StudyGroup() {
        creationDate = LocalDateTime.now();
        groupCounter += 1;
        id = groupCounter;
        coordinates = new Coordinates();
        name="";
        this.groupAdmin = new Person();
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


    public void getCreationDate(String nodeValue) {
        //TODO: попытаться убрать
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bout);
        StudyGroup sg0 = new StudyGroup("group", new Coordinates(), 10, FormOfEducation.DISTANCE_EDUCATION, Semester.FIRST, new Person("name", "228", new Location()));
        StudyGroup sg = new StudyGroup("group", new Coordinates(), 10, FormOfEducation.DISTANCE_EDUCATION, Semester.FIRST, new Person("name", "228", new Location()));
        out.writeObject(sg);
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray()));
        StudyGroup sg2 = (StudyGroup) in.readObject();
        System.out.println(sg2.id);
        System.out.println(sg2.name);
        System.out.println(sg2.studentsCount);
        System.out.println(sg2.coordinates.getX() + " " + sg2.coordinates.getY());
        System.out.println(sg2.creationDate);
        System.out.println(sg2.formOfEducation);
        System.out.println(sg2.groupAdmin.getName());
        System.out.println(sg2.groupAdmin.getPassportID());
        System.out.println(sg2.semesterEnum);
    }
}

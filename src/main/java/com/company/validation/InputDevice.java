package com.company.validation;

import com.company.collection_objects.*;
import com.company.collection_manage.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

interface IValidator {
    boolean IsValid(String input);

    String ErrorMessage();
}


class NameValidator implements IValidator {

    @Override
    public boolean IsValid(String input) {
        return input.matches("\\w[\\w\\s]*");
    }

    @Override
    public String ErrorMessage() {
        return "Введите любые символы кроме пустой строки и пробела";
    }
}

class CoordinatesValidator implements IValidator {

    @Override
    public boolean IsValid(String input) {

        return input.matches("\\d+; ?[-]?[0-9]*\\.?,?[0-9]+");
    }

    @Override
    public String ErrorMessage() {
        return "Введите координаты в формате X; Y";
    }
}

class StudentsCountValidator implements IValidator {

    @Override
    public boolean IsValid(String input) {
         return input.matches("[1-9]\\d{0,8}");
    }

    @Override
    public String ErrorMessage() {
        return "Введите число";
    }
}

class FormOfEducationValidator implements IValidator {


    @Override
    public boolean IsValid(String input) {
        try {
            Integer.parseInt(input);
            return(Integer.parseInt(input) < FormOfEducation.values().length);
        } catch (NumberFormatException e) {
            return  false;
        }
    }

    @Override
    public String ErrorMessage() {
        return "Введите одно из указанных чисел, уважаемый пекарь";
    }
}

class SemesterValidator implements IValidator {
    @Override
    public boolean IsValid(String input) {
        try {
            Integer.parseInt(input);
            return(Integer.parseInt(input) < Semester.values().length);
        } catch (NumberFormatException e) {
            return  false;
        }
    }

    @Override
    public String ErrorMessage() {
        return "Введите одно из указанных чисел, уважаемый пекарь";
    }
}
class AdminExistsValidator implements IValidator {

    private boolean Exist = true;

    public boolean getExist() {
        return Exist;
    }

    @Override
    public boolean IsValid(String input) {

        if(input.isEmpty()) {
            Exist = false;
            return true;
        }
        if(input.matches("[Yy,Дд]\\w*")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String ErrorMessage() {
        return "Укажите, существует ли админ";
    }
}

class AdminNameValidator implements IValidator {

    @Override
    public boolean IsValid(String input) {
        return input.matches("([A-Za-zА-Яа-я\\-]+)\\s*([A-Za-zА-Яа-я]+)");
    }

    @Override
    public String ErrorMessage() {
        return "Введите имя в соответствии с форматом, уважаемый пекарь";
    }
}

class AdminPassportValidator implements IValidator {

    @Override
    public boolean IsValid(String input) {
        return input.matches("^\\d{4}\\s\\d{6}");
    }

    @Override
    public String ErrorMessage() {
        return "Введите паспортные данные в соответствии с форматом, уважаемый пекарь";
    }
}

class AdminLocationValidator implements IValidator {

    @Override
    public boolean IsValid(String input) {
        return input.matches("\\d+\\.?,?\\d*; ?\\d+;? ?[-]?\\d*\\.?,?\\d*");
    }

    @Override
    public String ErrorMessage() {
        return "Введите координаты в соответствии с форматом, уважаемый пекарь";
    }
}

public class InputDevice {

    ArrayList<quiz> questions = new ArrayList<>();

    public InputDevice() {

        questions.add(new quiz("Имя группы", new NameValidator(), "gName", true));
        questions.add(new quiz("Координаты группы в формате X(1); Y(1,0)", new CoordinatesValidator(), "gCoords", true));
        questions.add(new quiz("Число студентов в группе", new StudentsCountValidator(), "stCount", true));
        questions.add(new quiz("Форма обучения " + FormOfEducation.GetStringValues(), new FormOfEducationValidator(), "foedu", false));
        questions.add(new quiz("У группы есть админ? Если нет, введите пустую строку" + Person.ifPersonExists(), new AdminExistsValidator() , "aExs", true));
        questions.add(new quiz("Семестр " + Semester.GetStringValues(), new SemesterValidator(), "sem", true));
        questions.add(new quiz("Имя админа группы(Фамилия Имя)", new AdminNameValidator(), "aName", true));
        questions.add(new quiz("Серия и номер паспорта(пример: 1234 123456) админа группы", new AdminPassportValidator(), "passport", false));
        questions.add(new quiz("Введите координаты в формате X(0,0); Y(0); Z(-1,0)", new AdminLocationValidator(), "aCoords", false));

    }

    public class quiz {
        public String question;
        public String answer;
        public IValidator validator;
        public String ScriptName;
        public boolean Important;

        public quiz(String question, IValidator validator, String ScriptName, boolean Important) {
            this.question = question;
            this.validator = validator;
            this.ScriptName = ScriptName;
            this.Important = Important;
        }
    }
    public String GetScriptName() {

        StringBuilder Keys = new StringBuilder("\n" + "Ключи для ввода данных: ");
        for (int i = 0; i < 8; i++) {
          Keys.append(String.format("%n-%-45s  %-45s", questions.get(i).ScriptName, "[" + questions.get(i).question + "]"));
        }
        return Keys.toString();
    }

    public int RemoveById() throws InputMismatchException {
        int RemoveById;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID группы для удаления");
        RemoveById = scanner.nextInt();
        return RemoveById;
    }
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

    public int ReadFormOfEducation() throws InputMismatchException {
        int FormEducation;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите FormOfEducation группы: " + FormOfEducation.GetStringValues());
        FormEducation = scanner.nextInt();
        return FormEducation;
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

    public Path ReadExecuteFilePath() {

        Scanner scanner = new Scanner(System.in);
        String ExecuteFilePath = scanner.next();
        Path path = Paths.get(ExecuteFilePath);
        if (!((new File(path.toString())).exists())) {
            System.out.println("Не нашёл такой файл, пекарб((");
            return null;
        } else {
            return path;
        }


    }
    public int ReadFilterSem() {
        int Sem;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите semester, по которому хотите отсортировать: " + Semester.GetStringValues());
        Sem = scanner.nextInt();
        return Sem;

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

    public void edit(CollectionManagement collectionManagement) {

        StudyGroup GroupElement = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID группы для редактирования");

        try {
            int GroupId = scanner.nextInt();
            for (StudyGroup studyGroup : collectionManagement.getCollection()) {
                if (GroupId == studyGroup.getid()) {
                    GroupElement = studyGroup;
                }
            }
            if (GroupElement == null) {
                System.out.println("Ничего не нашёл по этому номеру:((");
                return;
            }
        }
        catch (InputMismatchException Ex) {
            System.out.println("Введите число");
            return;
        }

        for (quiz quiz : questions) {

            boolean ResultOK = false;
            //до тех пор пока не ввел правильное
            while (!ResultOK) {

                System.out.println("(" + (questions.indexOf(quiz) + 1) + "/" + questions.size()+ ")" + quiz.question);
                String name = scanner.nextLine();
                //Проверяем надо ли что-то менять
                if (name.equals("N")) {
                    ResultOK = true;
                }
                else {
                        if (name.equals("exitcmd")) {
                            System.out.println("Выходим из команды add, пекарь");
                            return;
                        }
                        else {
                            if (quiz.validator != null) {
                                if (quiz.validator.IsValid(name)) {
                                    quiz.answer = name;
                                    ResultOK = true;
                                } else {
                                    System.out.println(quiz.validator.ErrorMessage());
                                }
                            } else {
                                quiz.answer = name;
                                ResultOK = true;
                            }
                        }
                    }
                }
            if (quiz.validator instanceof AdminExistsValidator) {
                AdminExistsValidator admValid = (AdminExistsValidator)(quiz.validator);
                if (!admValid.getExist()) {
                    break;
                }
            }
            }

        for (quiz answer : questions) {
            System.out.println(answer.answer);
        }
        Person Admin = null;

        AdminExistsValidator admValid = (AdminExistsValidator)(questions.get(5).validator);

        if (questions.get(5).answer != null && admValid.getExist()) {
            if (GroupElement.getGroupAdmin() == null) {
                GroupElement.setGroupAdmin(new Person());
            }
            Admin = GroupElement.getGroupAdmin();

            if (questions.get(6).answer != null) {
                Admin.setName(questions.get(6).answer);
            }
            if (questions.get(7).answer != null) {
                Admin.setPassportID(questions.get(7).answer);
            }
            if (questions.get(8).answer != null) {
                Admin.setLocation(new Location(questions.get(8).answer));
            }
        }
        if (questions.get(0).answer != null) {
            GroupElement.setName(questions.get(0).answer);
        }
        if (questions.get(1).answer != null) {
            GroupElement.getCoordinates().parseString(questions.get(1).answer);
        }
        if (questions.get(2).answer != null) {
            GroupElement.setStudentsCount(Integer.parseInt(questions.get(2).answer));
        }
        if (questions.get(3).answer != null) {
            if(questions.get(3).answer.equals("")) {
                GroupElement.setFormOfEducation(null);
            } else {
                GroupElement.setFormOfEducation(FormOfEducation.values()[Integer.parseInt(questions.get(3).answer)]);
            }
        }
        if (questions.get(4).answer != null) {
            GroupElement.setSemesterEnum(Semester.values()[Integer.parseInt(questions.get(4).answer)]);
        }

    }

    public void EditFromFile(StudyGroup studyGroup, String CommandArgs) {

        for(quiz Question : questions) {
            Pattern p = Pattern.compile("-" + Question.ScriptName + "(.+?)( -|$)");
            Matcher m = p.matcher(CommandArgs);
            if (m.find()) {

                if (Question.validator != null) {
                    String FoundAnswer = m.group(1).trim();
                    if (Question.validator.IsValid(FoundAnswer)) {
                        Question.answer = FoundAnswer;
                    }
                }
            }
        }
        for (quiz answer : questions) {
            System.out.println(answer.answer);
        }

        if (questions.get(0).answer != null) {
            studyGroup.setName(questions.get(0).answer);
        }
        if (questions.get(1).answer != null) {
            studyGroup.getCoordinates().parseString(questions.get(1).answer);
        }
        if (questions.get(2).answer != null) {
            studyGroup.setStudentsCount(Integer.parseInt(questions.get(2).answer));
        }
        if (questions.get(3).answer != null) {
            if(questions.get(3).answer.equals("")) {
                studyGroup.setFormOfEducation(null);
            } else {
                studyGroup.setFormOfEducation(FormOfEducation.values()[Integer.parseInt(questions.get(3).answer)]);
            }
        }
        if (questions.get(4).answer !=null) {
            studyGroup.setSemesterEnum(Semester.values()[Integer.parseInt(questions.get(4).answer)]);
        }
        Person Admin;
        AdminExistsValidator admValid = (AdminExistsValidator)(questions.get(5).validator);

        if (questions.get(5).answer != null && admValid.getExist()) {

            if (studyGroup.getGroupAdmin() == null) {
                studyGroup.setGroupAdmin(new Person());
            }
            Admin = new Person();
            Admin.setName(questions.get(6).answer);
            if (questions.get(7).answer != null) {
                Admin.setPassportID(questions.get(7).answer);
            }
            if (questions.get(8).answer != null) {
                Admin.setLocation(new Location(questions.get(8).answer));
            }
        }

    }

    public StudyGroup add() {
        Scanner scanner = new Scanner(System.in);

        for (quiz quiz : questions) {
            boolean ResultOK = false;

            while (!ResultOK) {

                System.out.println("(" + (questions.indexOf(quiz) + 1) + "/" + questions.size()+ ")" + quiz.question);
                String name = scanner.nextLine();

                if (name.equals("exitcmd")) {
                    System.out.println("Выходим из команды add, пекарь");
                    return null;
                }
                else {
                    if (quiz.validator != null) {
                        if (quiz.validator.IsValid(name)) {
                            quiz.answer = name;
                            ResultOK = true;
                        } else {
                            System.out.println(quiz.validator.ErrorMessage());
                        }

                    } else {
                        quiz.answer = name;
                        ResultOK = true;
                    }
                }
            }
            if (quiz.validator instanceof AdminExistsValidator) {
                AdminExistsValidator admValid = (AdminExistsValidator) (quiz.validator);
                if (!admValid.getExist()) {
                    break;
                }
            }
        }

        for (quiz answer : questions) {
            System.out.println(answer.answer);
        }
        Person Admin = null;
        AdminExistsValidator admValid = (AdminExistsValidator)(questions.get(5).validator);

        if (questions.get(5).answer != null && admValid.getExist()) {
            Admin = new Person();
            Admin.setName(questions.get(6).answer);
            Admin.setPassportID(questions.get(7).answer);
            Admin.setLocation(new Location(questions.get(8).answer));

        }
        return new StudyGroup(questions.get(0).answer,
                new Coordinates(questions.get(1).answer),
                Integer.parseInt(questions.get(2).answer),
                FormOfEducation.values()[Integer.parseInt(questions.get(3).answer)],
                Semester.values()[Integer.parseInt(questions.get(4).answer)],
                Admin);

    }

    public StudyGroup addFromFile(String CommandArgs) {

        boolean ImportantQuestionMistake = false;
        for(quiz Question : questions) {
            Pattern p = Pattern.compile("-" + Question.ScriptName + "(.+?)( -|$)");
            Matcher m = p.matcher(CommandArgs);
            if (m.find()) {

                if (Question.validator != null) {
                    String FoundAnswer = m.group(1).trim();
                    if (Question.validator.IsValid(FoundAnswer)) {
                        Question.answer = FoundAnswer;
                    }
                    else {
                        if(Question.Important) {

                            ImportantQuestionMistake = true;

                        }
                    }
                }
            }
            else {
                if(Question.Important) {
                    ImportantQuestionMistake = true;
                }
            }
        }
        if (ImportantQuestionMistake) {
            System.out.println("Не получен(Ы) ответы на важные вопросы, идите лесом");
        }
        for (quiz answer : questions) {
            System.out.println(answer.answer);
        }
        FormOfEducation f = null;
        if (questions.get(3).answer != null) {
            if(questions.get(3).answer.equals("")) {
                f = null;
            } else {
                f = FormOfEducation.values()[Integer.parseInt(questions.get(3).answer)];
            }
        }

        Person Admin = null;
        AdminExistsValidator admValid = (AdminExistsValidator)(questions.get(5).validator);

        if (questions.get(5).answer != null && admValid.getExist()) {
            Admin = new Person();
            Admin.setName(questions.get(6).answer);
            Admin.setPassportID(questions.get(7).answer);
            Admin.setLocation(new Location(questions.get(8).answer));

        }

        return new StudyGroup(questions.get(0).answer,
                new Coordinates(questions.get(1).answer),
                Integer.parseInt(questions.get(2).answer),
                f,
                Semester.values()[Integer.parseInt(questions.get(4).answer)],
                Admin);
    }
}
 
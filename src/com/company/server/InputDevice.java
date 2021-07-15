package com.company.server;

import collection_objects.*;
import collection_objects.StudyGroupField.StudyGroupField;
import com.company.collection_manage.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

interface IValidator {
    boolean isValid(String input);

    String errorMessage();
}


class NameValidator implements IValidator {

    @Override
    public boolean isValid(String input) {
        return input.matches("\\w[\\w\\s]*");
    }

    @Override
    public String errorMessage() {
        return "Введите любые символы кроме пустой строки и пробела";
    }
}

class CoordinatesValidator implements IValidator {

    @Override
    public boolean isValid(String input) {

        return input.matches("\\d+; ?[-]?[0-9]*\\.?,?[0-9]+");
    }

    @Override
    public String errorMessage() {
        return "Введите координаты в формате X; Y";
    }
}

class StudentsCountValidator implements IValidator {

    @Override
    public boolean isValid(String input) {
         return input.matches("[1-9]\\d{0,8}");
    }

    @Override
    public String errorMessage() {
        return "Введите число";
    }
}

class FormOfEducationValidator implements IValidator {


    @Override
    public boolean isValid(String input) {
        try {
            Integer.parseInt(input);
            return(Integer.parseInt(input) < FormOfEducation.values().length);
        } catch (NumberFormatException e) {
            return  false;
        }
    }

    @Override
    public String errorMessage() {
        return "Введите одно из указанных чисел, уважаемый пекарь";
    }
}

class SemesterValidator implements IValidator {
    @Override
    public boolean isValid(String input) {
        try {
            Integer.parseInt(input);
            return(Integer.parseInt(input) < Semester.values().length);
        } catch (NumberFormatException e) {
            return  false;
        }
    }

    @Override
    public String errorMessage() {
        return "Введите одно из указанных чисел, уважаемый пекарь";
    }
}

class AdminNameValidator implements IValidator {

    @Override
    public boolean isValid(String input) {
        return input.matches("([A-Za-zА-Яа-я\\-]+)\\s*([A-Za-zА-Яа-я]+)");
    }

    @Override
    public String errorMessage() {
        return "Введите имя в соответствии с форматом, уважаемый пекарь";
    }
}
class AdminPassportValidator implements IValidator {

    @Override
    public boolean isValid(String input) {
        return input.matches("^\\d{4}\\s\\d{6}");
    }

    @Override
    public String errorMessage() {
        return "Введите паспортные данные в соответствии с форматом, уважаемый пекарь";
    }
}
class AdminLocationValidator implements IValidator {

    @Override
    public boolean isValid(String input) {
        return input.matches("\\d+\\.?,?\\d*; ?\\d+;? ?[-]?\\d*\\.?,?\\d*");
    }

    @Override
    public String errorMessage() {
        return "Введите координаты в соответствии с форматом, уважаемый пекарь";
    }
}
public class InputDevice {
    //TODO: переделать Person и StudyGroup, чтобы позволяли создание с нулевыми полями. Переделать валидаторы.
    private static class Question {

        final String question;
        final IValidator validator;
        final String scriptName;
        final boolean important;
        final boolean permitNulls;

        private Question(String question, IValidator validator, String scriptName, boolean important, boolean permitNulls) {
            this.question = question;
            this.validator = validator;
            this.scriptName = scriptName;
            this.important = important;
            this.permitNulls = permitNulls;
        }

        private class Answer {
            String value;
            boolean answered;

            Answer() { this.answered = false; }

            Answer(String value) {
                this.value = value;
                this.answered = true;
            }

            Question getQuestion() { return Question.this; }
        }
    }

    private static ArrayList<Question.Answer> initAnswers(ArrayList<Question> questions) {
        ArrayList<Question.Answer> answers = new ArrayList<>(questions.size());
        for (Question question : questions) {
            answers.add(question.new Answer());
        }
        return answers;
    }

    private static ArrayList<Question> quiz = generateQuestions();

    private static ArrayList<Question> generateQuestions() {
        if (quiz != null) return quiz;
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("Имя группы", new NameValidator(), StudyGroupField.NAME.getScriptName(), true, false));
        questions.add(new Question("Координаты группы в формате X(1); Y(1,0)", new CoordinatesValidator(), StudyGroupField.COORDINATES.getScriptName(), true, false));
        questions.add(new Question("Число студентов в группе", new StudentsCountValidator(), StudyGroupField.STUDENTS_COUNT.getScriptName(), true, false));
        questions.add(new Question("Форма обучения " + FormOfEducation.GetStringValues(), new FormOfEducationValidator(), StudyGroupField.FORM_OF_EDUCATION.getScriptName(), false, true));
        questions.add(new Question("Семестр " + Semester.GetStringValues(), new SemesterValidator(), StudyGroupField.SEMESTER.getScriptName(), true, false));
        questions.add(new Question("Имя админа группы(Фамилия Имя)", new AdminNameValidator(), StudyGroupField.ADMIN_NAME.getScriptName(), true, true));
        questions.add(new Question("Серия и номер паспорта(пример: 1234 123456) админа группы", new AdminPassportValidator(), StudyGroupField.ADMIN_PASSPORT.getScriptName(), false, true));
        questions.add(new Question("Введите координаты в формате X(0,0); Y(0); Z(-1,0)", new AdminLocationValidator(), StudyGroupField.ADMIN_LOCATION.getScriptName(), false, true));
        return questions;
    }

    public static String getScriptName() {

        StringBuilder Keys = new StringBuilder("\n" + "Ключи для ввода данных: ");
        for (int i = 0; i < 8; i++) {
          Keys.append(String.format("%n-%-45s  %-45s", quiz.get(i).scriptName, "[" + quiz.get(i).question + "]"));
        }
        return Keys.toString();
    }

    public static int removeById() throws InputMismatchException {
        int RemoveById;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ID группы для удаления");
        RemoveById = scanner.nextInt();
        return RemoveById;
    }
    public static enum RemoveMode {
        High,
        Low,
        Equals
    }

    public static void remove(CollectionManagement collectionManagement, int RemoveById, RemoveMode Equals) {
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

    public static int readFormOfEducation() throws InputMismatchException {
        int FormEducation;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите FormOfEducation группы: " + FormOfEducation.GetStringValues());
        FormEducation = scanner.nextInt();
        return FormEducation;
    }

    public static void countFormOfEducation(CollectionManagement collectionManagement, Integer FormEducation) {

        FormOfEducationValidator I = new FormOfEducationValidator();
        if (I.isValid(FormEducation.toString())) {
            long b = collectionManagement.getCollection().stream().filter(a -> a.getFormOfEducation().ordinal() > FormEducation).count();
            System.out.println("Количество элементов, значение поля formOfEducation которых больше заданного: " + b);
        } else {
            System.out.println(I.errorMessage());
        }
    }

    public static Path readExecuteFilePath() {

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
    public static int readFilterSem() {
        int Sem;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите semester, по которому хотите отсортировать: " + Semester.GetStringValues());
        Sem = scanner.nextInt();
        return Sem;

    }
    public static void filterBySem(CollectionManagement collectionManagement, Integer Sem) {

        try {
            SemesterValidator I = new SemesterValidator();
            if (I.isValid(Sem.toString())) {
               long Count = collectionManagement.getCollection().stream().filter(a -> a.getSemesterEnum().ordinal() == Sem).count();

                if( Count == 0) {
                    System.out.println("Нет элементов, значения поля semesterEnum которых равно зааднному");
                }
                else {
                    Stream<StudyGroup> b = collectionManagement.getCollection().stream().filter(a -> a.getSemesterEnum().ordinal() == Sem);
                    b.forEach(n -> System.out.println("Элементы, значение поля semesterEnum которых равно заданному: " + n.getId() + " " + n.getName()));
                }
            } else {
                System.out.println(I.errorMessage());
            }
        } catch (InputMismatchException Ex) {
            System.out.println("Введите число");
        }
    }

    private interface StudyGroupFieldParser {
        void parseValue(StudyGroup studyGroup, String value);
    }

    private final static HashMap<String, StudyGroupFieldParser> fieldParsers = fillFieldParsers();

    private static HashMap<String, StudyGroupFieldParser> fillFieldParsers() {
        HashMap<String, StudyGroupFieldParser> map = new HashMap<>();
        map.put(StudyGroupField.NAME.getScriptName(), StudyGroup::setName);
        map.put(StudyGroupField.COORDINATES.getScriptName(),
                (studyGroup, value) -> studyGroup.getCoordinates().parseString(value));
        map.put(StudyGroupField.STUDENTS_COUNT.getScriptName(),
                (studyGroup, value) -> studyGroup.setStudentsCount(Integer.parseInt(value)));
        map.put(StudyGroupField.FORM_OF_EDUCATION.getScriptName(),
                (studyGroup, value) -> studyGroup.setFormOfEducation
                        ((value == null) ? null : FormOfEducation.values()[Integer.parseInt(value)]));
        map.put(StudyGroupField.SEMESTER.getScriptName(),
                (studyGroup, value) -> studyGroup.setSemesterEnum(Semester.values()[Integer.parseInt(value)]));
        map.put(StudyGroupField.ADMIN_NAME.getScriptName(),
                (studyGroup, value) -> {
            if (value == null)
                studyGroup.setGroupAdmin(null);
            else if (studyGroup.getGroupAdmin() == null)
                studyGroup.setGroupAdmin(new Person(value));
            else studyGroup.getGroupAdmin().setName(value);
        });
        map.put(StudyGroupField.ADMIN_PASSPORT.getScriptName(),
                (studyGroup, value) -> studyGroup.getGroupAdmin().setPassportID(value));
        map.put(StudyGroupField.ADMIN_LOCATION.getScriptName(),
                (studyGroup, value) -> studyGroup.getGroupAdmin().
                        setLocation((value == null) ? null : new Location(value)));
        return map;
    }

    private static void printQuizAnswers(ArrayList<Question.Answer> answers) {
        for (Question.Answer answer : answers) {
            System.out.println(answer.value);
        }
    }

    private static String assignQuizAnswers(StudyGroup studyGroup, ArrayList<Question.Answer> answers) {
        StringBuilder keysBuilder = new StringBuilder();
        for (Question.Answer answer : answers) {
            String key = answer.getQuestion().scriptName;
            if (answer.answered) {
                fieldParsers.get(key).parseValue(studyGroup, answer.value);
                keysBuilder.append(" ").append(key);
            }
        }
        return keysBuilder.toString().trim();
    }

    private static boolean acceptAnswer(Question.Answer answer, String value) {
        IValidator validator = answer.getQuestion().validator;
        if (validator != null && !validator.isValid(value)) {
            return false;
        } else {
            if (value.equals("") && answer.getQuestion().permitNulls)
                answer.value = null;
            else
                answer.value = value;
            answer.answered = true;
            return true;
        }
    }

    private static String fillGroupFromSysIn(StudyGroup studyGroup, String skipQuestionExpr) {

        ArrayList<Question.Answer> answers = initAnswers(quiz);
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < answers.size(); i++) {

            Question.Answer answer = answers.get(i);
            boolean ResultOK = false;
            //до тех пор пока не ввел правильное
            while (!ResultOK) {

                System.out.println("(" + (i + 1) + "/" + answers.size()+ ")" + quiz.get(i));
                String name = scanner.nextLine();
                //Проверяем надо ли что-то менять
                if (skipQuestionExpr != null && name.equals(skipQuestionExpr)) {
                    ResultOK = true;
                }
                else {
                    if (name.equals("exitcmd")) {
                        System.out.println("Выходим из команды ввода, пекарь");
                        return "";
                    }
                    else {
                        ResultOK = acceptAnswer(answer, name);
                        if (!ResultOK)
                            System.out.println(answer.getQuestion().validator.errorMessage());
                    }
                }
            }
        }

        printQuizAnswers(answers);

        return assignQuizAnswers(studyGroup, answers);
    }

    private static boolean fillGroupFromFile(StudyGroup studyGroup, String commandArgs, boolean importantQuestions) {

        ArrayList<Question.Answer> answers = initAnswers(quiz);
        boolean importantQuestionMistake = false;
        for(Question.Answer answer : answers) {
            Pattern p = Pattern.compile("-" + answer.getQuestion().scriptName + "(.+?)( -|$)");
            Matcher m = p.matcher(commandArgs);
            if (m.find()) {
                String foundAnswer = m.group(1).trim();
                if (!acceptAnswer(answer, foundAnswer) && answer.getQuestion().important) {
                    importantQuestionMistake = true;
                }
            }
            else {
                if(answer.getQuestion().important) {
                    importantQuestionMistake = true;
                }
            }
        }
        if (importantQuestionMistake && importantQuestions) {
            System.out.println("Не получен(Ы) ответы на важные вопросы, идите лесом");
        }

        printQuizAnswers(answers);

        assignQuizAnswers(studyGroup, answers);
        return true;
    }

    public static String edit(StudyGroup studyGroup) {
        return fillGroupFromSysIn(studyGroup, "N");
    }

    public static void editFromFile(StudyGroup studyGroup, String commandArgs) {
        fillGroupFromFile(studyGroup, commandArgs, false);
    }

    public static StudyGroup input() {
        StudyGroup studyGroup = new StudyGroup();
        if (fillGroupFromSysIn(studyGroup, null).equals(""))
            return null;
        return studyGroup;
    }

    public static StudyGroup inputFromFile(String commandArgs) {
        StudyGroup studyGroup = new StudyGroup();
        fillGroupFromFile(studyGroup, commandArgs, true);
        return studyGroup;
    }

    public static void main(String[] args) {
        System.out.println(FormOfEducation.valueOf(null));
    }
}
 
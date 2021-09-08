package com.company.common.commands;

import com.company.client.validation.CommandMethodsExecute;
import com.company.common.collection_objects.Semester;
import com.company.server.processing.collection_manage.CollectionManagement;
import com.company.client.validation.CommandMethods;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterBySemCommand extends AbstractCommand {

    CollectionManagement collectionManagement;
    public FilterBySemCommand(CollectionManagement collectionManagement) {
        this.collectionManagement = collectionManagement;
        }
        @Override
        public String execute(String commandArgs) {
            return "Укажите аргументы для фильтрации";
    }
    @Override
    public String execute(String strArgs, Object commandArgs) {
        try {
            CommandMethodsExecute methodsExecute = new CommandMethodsExecute();
            if (commandArgs == null) throw new NullPointerException("FilterBySemCommand expects to receive a Semester object. " +
                    "Received null.");
            if (commandArgs instanceof Semester) {
                return methodsExecute.filterBySem(collectionManagement, (Semester) commandArgs);
            } else throw new IllegalArgumentException("FilterBySemCommand expects to receive a Semester object. " +
                    "Type of received object: " + commandArgs.getClass());
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

        @Override
        public String describe() {
            return ("Выводит элементы, значение поля semesterEnum которых равно заданному");
        }
    }

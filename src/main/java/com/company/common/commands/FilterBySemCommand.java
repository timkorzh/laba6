package com.company.common.commands;

import com.company.client.validation.CommandMethodsExecute;
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
        public String execute(String CommandArgs) {
            //InputDevice device = new InputDevice();
            CommandMethods device = new CommandMethods();
            CommandMethodsExecute methodsExecute = new CommandMethodsExecute();
            int FBS;

            if(CommandArgs == null) {
                try {
                    FBS = device.readFilterSem();//TODO: этот метод отсюда надо убрать
                } catch (InputMismatchException Ex) {
                    return "Введите число";
                }
            }
            else {
                Pattern p = Pattern.compile("-sem (\\d+?)( -|$)");
                Matcher m = p.matcher(CommandArgs);
                if (m.find()) {
                    FBS = Integer.parseInt(m.group(1));
                }
                else {
                    return "Ожидалось число";
                }
            }
            return methodsExecute.filterBySem(collectionManagement, FBS);
    }

        @Override
        public String describe() {
            return ("Выводит элементы, значение поля semesterEnum которых равно заданному");
        }
    }

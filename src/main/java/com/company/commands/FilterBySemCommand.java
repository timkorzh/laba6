package com.company.commands;

import com.company.collection_manage.CollectionManagement;
import com.company.validation.CommandMethods;
import com.company.validation.InputDevice;

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
            int FBS;

            if(CommandArgs == null) {
                try {
                    FBS = device.ReadFilterSem();
                } catch (InputMismatchException Ex) {
                    System.out.println("Введите число");
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
                    System.out.println("Ожидалось число");
                    return "Ожидалось число";
                    }
            }
        device.filterBySem(collectionManagement, FBS);
        return "";
    }

        @Override
        public String describe() {
            return ("Выводит элементы, значение поля semesterEnum которых равно заданному");
        }
    }

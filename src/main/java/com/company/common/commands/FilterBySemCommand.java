package com.company.common.commands;

import com.company.server.collection_manage.CollectionManagement;
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
        public void execute(String CommandArgs) {
            //InputDevice device = new InputDevice();
            CommandMethods device = new CommandMethods();
            int FBS;

            if(CommandArgs == null) {
                try {
                    FBS = device.readFilterSem();
                } catch (InputMismatchException Ex) {
                    System.out.println("Введите число");
                    return;
                    //return "Введите число";
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
                    return;
                    //return "Ожидалось число";
                    }
            }
        device.filterBySem(collectionManagement, FBS);
            return;
            //return "";
    }

        @Override
        public String describe() {
            return ("Выводит элементы, значение поля semesterEnum которых равно заданному");
        }
    }

package com.company.common.commands;

import com.company.client.validation.CommandMethodsExecute;
import com.company.server.processing.collection_manage.CollectionManagement;
import com.company.client.validation.CommandMethods;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CountGreaterCommand extends AbstractCommand {

    CollectionManagement collectionManagement;
    public CountGreaterCommand(CollectionManagement collectionManagement) {
        this.collectionManagement = collectionManagement;
    }

    @Override
    public void execute(String CommandArgs) {
        //InputDevice device = new InputDevice();
        CommandMethods device = new CommandMethods();
        CommandMethodsExecute methodsExecute = new CommandMethodsExecute();
        int FOE;

        if(CommandArgs == null) {
            try {
                FOE = device.readFormOfEducation();
            } catch (InputMismatchException Ex) {
                System.out.println("Введите число");
                return;
            }
        }
        else {
            Pattern p = Pattern.compile("-num (\\d+?)( -|$)");
            Matcher m = p.matcher(CommandArgs);
            if (m.find()) {
                FOE = Integer.parseInt(m.group(1));
            }
            else {
                System.out.println("Ожидалось число");
                return;
            }
        }
        methodsExecute.countFormOfEducation(collectionManagement, FOE);

    }

    @Override
    public String describe() {
        return ("Выводит количество элементов, значение поля formOfEducation которых больше заданного");
    }
}

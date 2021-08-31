package com.company.commands;

import com.company.collection_manage.CollectionManagement;
import com.company.validation.CommandMethods;
import com.company.validation.InputDevice;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveLowerCommand extends AbstractCommand {

    CollectionManagement collectionManagement;
    public RemoveLowerCommand(CollectionManagement collectionManagement) {
        this.collectionManagement = collectionManagement;
    }


    @Override
    public String execute(String CommandArgs) {
        //InputDevice device = new InputDevice();
        CommandMethods device = new CommandMethods();
        int RBI;
        if(CommandArgs == null) {
            try {
                RBI = device.RemoveById();
            } catch (InputMismatchException Ex) {
                System.out.println("Введите число");
                return "Введите число";
            }
        }
        else {
            Pattern p = Pattern.compile("-id (\\d+?)( -|$)");
            Matcher m = p.matcher(CommandArgs);
            if (m.find()) {
                RBI = Integer.parseInt(m.group(1));
            }
            else {
                System.out.println("Ожидалось число");
                return "Ожидалось число";
            }
        }

        device.remove(collectionManagement, RBI, CommandMethods.RemoveMode.Low);
        return "";
    }

    @Override
    public String describe() {
        return ("Удаляет из коллекции все элементы, меньшие, чем заданный. Введите после названия команды номер группы для редактирования при помощи ключа: (-id [id])");
    }
}

package com.company.commands;

import com.company.collection_manage.CollectionManagement;
import com.company.validation.CommandMethods;
import com.company.server.InputDevice;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveByIdCommand extends AbstractCommand {
    private CollectionManagement collectionManagement;
    public RemoveByIdCommand(CollectionManagement collectionManagement) {
        this.collectionManagement = collectionManagement;
    }

    @Override
    public String execute(String CommandArgs) {
        //InputDevice device = new InputDevice();
        CommandMethods device = new CommandMethods();
        int RBI;
        if(CommandArgs == null) {
            try {
                    RBI = device.removeById();
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

        device.remove(collectionManagement, RBI, CommandMethods.RemoveMode.Equals);
        return "";
    }

    @Override
    public String describe() {
        return ("Удаляет элемент из коллекции по его id. Введите после названия команды номер группы для редактирования при помощи ключа: (-id [id])");
    }
}

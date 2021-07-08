package com.company.commands;

import com.company.collection_manage.CollectionManagement;
import com.company.work_client.InputDevice;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveByIdCommand extends AbstractCommand {
    private CollectionManagement collectionManagement;
    public RemoveByIdCommand(CollectionManagement collectionManagement) {
        this.collectionManagement = collectionManagement;
    }

    @Override
    public void execute(String CommandArgs) {
        InputDevice device = new InputDevice();
        int RBI;
        if(CommandArgs == null) {
            try {
                    RBI = device.RemoveById();
            } catch (InputMismatchException Ex) {
                System.out.println("Введите число");
                return;
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
                return;
            }
        }

        device.remove(collectionManagement, RBI, InputDevice.RemoveMode.Equals);
    }

    @Override
    public String describe() {
        return ("Удаляет элемент из коллекции по его id. Введите после названия команды номер группы для редактирования при помощи ключа: (-id [id])");
    }
}
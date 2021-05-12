package com.company.commands;

import com.company.collection_manage.CollectionManagement;
import com.company.collection_manage.StudyGroup;
import com.company.work_client.InputDevice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateCommand extends AbstractCommand {
    private final CollectionManagement collectionManagement;

    public UpdateCommand(CollectionManagement collectionManagement) {
        this.collectionManagement = collectionManagement;
    }
    InputDevice device = new InputDevice();

    @Override
    public void execute(String CommandArgs) {
        if (CommandArgs != null) {
            Pattern p = Pattern.compile("-id (\\d+?)( -|$)");
            Matcher m = p.matcher(CommandArgs);
            if (m.find()) {
                int GroupId = Integer.parseInt(m.group(1).trim());
                if (collectionManagement.getCollection().stream().anyMatch(n -> n.getId() == GroupId)) {
                    device.EditFromFile(((StudyGroup) collectionManagement.getCollection().stream().filter(n -> GroupId == n.getId()).toArray()[0]), CommandArgs);
                } else {
                    System.out.println("Ничего не нашёл по этому номеру((");
                    return;
                }
            }
            else {
                System.out.println("Параметр id не найден");
                return;
            }
        }
        else {
            collectionManagement.edit();
        }
    }

    @Override
    public String describe() {
        return ("Обновляет значение элемента коллекции, id которого равен заданному. Введите после названия команды номер группы для редактирования при помощи ключа: (-id [id])" + device.GetScriptName() + "\nВ режиме построчного ввода напишите 'N', чтобы не редактировать данные. ");
    }
}

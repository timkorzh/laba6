package com.company.common.commands;

import com.company.server.processing.collection_manage.CollectionManagement;
import com.company.common.collection_objects.StudyGroup;
import com.company.server.InputDevice;

public class AddCommand extends AbstractCommand {
    private final CollectionManagement collectionManagement;

    public AddCommand(CollectionManagement collectionManagement) {
        this.collectionManagement = collectionManagement;
    }

    @Override
    public void execute(String commandArgs) {

        if (commandArgs == null) {
            collectionManagement.add();
        }
        else {
            collectionManagement.add(commandArgs);
        }

    }

    @Override
    public void execute(String strArgs, Object commandArgs) {
        if (commandArgs instanceof StudyGroup) {
            collectionManagement.add((StudyGroup) commandArgs);
        }
    }

    @Override
    public String describe() {
        return ("Добавляет новый элемент в коллекцию." + InputDevice.getScriptName() + "\n Чтобы выйти из команды в режиме построчного ввода, напишите: exitcmd");
    }

}

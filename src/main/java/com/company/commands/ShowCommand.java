package com.company.commands;

import com.company.collection_manage.CollectionManagement;

public class ShowCommand extends AbstractCommand {
    private final CollectionManagement collectionManagement;

    public ShowCommand(CollectionManagement collectionManagement) {
        this.collectionManagement = collectionManagement;
    }

    @Override
    public void execute(String CommandArgs) {
        collectionManagement.show();
    }

    @Override
    public String describe() {
        return ("Выводит в стандартный поток вывода все элементы коллекции в строковом представлении");
    }
}

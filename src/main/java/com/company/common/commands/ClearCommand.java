package com.company.common.commands;

import com.company.server.processing.collection_manage.CollectionManagement;

public class ClearCommand extends AbstractCommand{
    private final CollectionManagement collectionManagement;

    public ClearCommand(CollectionManagement collectionManagement) {
        this.collectionManagement = collectionManagement;
    }
    @Override
    public String describe() {
        return ("Очищает коллекцию");
    }

    @Override
    public void execute(String CommandArgs) {
        collectionManagement.clear();
        return;
        //return "";
    }
}

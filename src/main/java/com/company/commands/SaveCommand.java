package com.company.commands;


import com.company.server.CommandInvoker.ClientCommandReceiver;

public class SaveCommand extends AbstractCommand {
    private final ClientCommandReceiver clientCommandReceiver;

    public SaveCommand(ClientCommandReceiver clientCommandReceiver) {
        this.clientCommandReceiver = clientCommandReceiver;
    }
    @Override
    public String execute(String CommandArgs) {
        clientCommandReceiver.save();
        return "";
    }

        @Override
        public String describe () {
        return ("сохранить коллекцию в файл");
        }
    }


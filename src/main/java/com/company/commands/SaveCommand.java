package com.company.commands;


import com.company.server.CommandInvoker.ClientCommandReceiver;

public class SaveCommand extends AbstractCommand {
    private final ClientCommandReceiver clientCommandReceiver;

    public SaveCommand(ClientCommandReceiver clientCommandReceiver) {
        this.clientCommandReceiver = clientCommandReceiver;
    }
    @Override
    public void execute(String CommandArgs) { clientCommandReceiver.save(); }

        @Override
        public String describe () {
        return ("сохранить коллекцию в файл");
        }
    }


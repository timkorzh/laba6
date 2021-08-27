package com.company.commands;

import com.company.server.CommandInvoker.CommandInvoker;

public class HistoryCommand extends AbstractCommand {
    CommandInvoker commandInvoker;

    public HistoryCommand(CommandInvoker commandInvoker) {
        this.commandInvoker = commandInvoker;
    }

    @Override
    public void execute(String CommandArgs) {
        System.out.println("Последние команды:" + commandInvoker.history.toString());
    }

    @Override
    public String describe () {
        return ("Выводит историю команд");
    }
}
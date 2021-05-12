package com.company.commands;

import com.company.work_client.Client;

public class HistoryCommand extends AbstractCommand {
    Client client;

    public HistoryCommand(Client client) {
        this.client = client;}
        @Override
            public void execute(String CommandArgs) {
            System.out.println("Последние команды:" + client.getCommandInvoker().History.toString());
        }

        @Override
            public String describe () {
        return ("Выводит историю команд");
        }
}
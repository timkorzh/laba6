package com.company.commands;

import com.company.server.RequestReader.RequestReader;

public class HistoryCommand extends AbstractCommand {
    RequestReader requestReader;

    public HistoryCommand(RequestReader requestReader) {
        this.requestReader = requestReader;}
        @Override
            public String execute(String CommandArgs) {
            System.out.println("Последние команды:" + requestReader.getCommandInvoker().History.toString());
            return "";
        }

        @Override
            public String describe () {
        return ("Выводит историю команд");
        }
}
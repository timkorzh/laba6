package com.company.common.commands;

//import com.company.server.recognition.ClientCommandReceiver;
import com.company.server.recognition.CommandInvoker;

import java.util.HashMap;

public class HelpCommand extends AbstractCommand {
    private final CommandInvoker commandInvoker;

    public HelpCommand(CommandInvoker commandInvoker) {
        this.commandInvoker = commandInvoker;
        }
        @Override
        public String describe() {
        return ("Выводит справку о командах");
    }

        @Override
        public void execute(String CommandArgs) {
            HashMap<String, AbstractCommand> hashMap = commandInvoker.getHashMap();
            System.out.printf("%-45s %-45s %n", "ИМЯ КОМАНДЫ", "ОПИСАНИЕ");
            for (String commandName : hashMap.keySet()) {
                System.out.printf("%-45s %-45s %n", commandName, commandInvoker.getHashMap().get(commandName).describe());

            }
    }
    }

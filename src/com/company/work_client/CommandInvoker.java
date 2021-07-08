package com.company.work_client;

import com.company.commands.AbstractCommand;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandInvoker {
    private final HashMap<String, AbstractCommand> hashMap;

    public CommandInvoker() {
        this.hashMap = new HashMap<>();
        History = new ArrayDeque<>(7);
    }

    public void register(String commandName, AbstractCommand command) {
        hashMap.put(commandName, command);
    }

    public ArrayDeque<String> History;


    public HashMap<String, AbstractCommand> getHashMap() {
        return hashMap;
    }

    public void execute(String InputString) {
        String CommandName, CommandArgs;
        CommandName = InputString.split("\\s")[0];
        Pattern p = Pattern.compile("\\s-.*");
        Matcher m = p.matcher(InputString);
        if (m.find()) {

            CommandArgs = m.group(0);
        } else {
            CommandArgs = null;
        }

        try {
            hashMap.get(CommandName).execute(CommandArgs);

            History.push(CommandName);
            if (History.size() > 7) {
                History.removeLast();
            }

        } catch (NullPointerException e) {
            System.out.println("Пекарь, " + InputString + " - незарегистрированная команда. Похудей пальчики или напиши help.");
        }
    }

}

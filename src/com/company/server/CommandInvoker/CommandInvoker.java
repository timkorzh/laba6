package com.company.server.CommandInvoker;

import com.company.collection_manage.CollectionManagement;
import com.company.commands.*;
import com.company.server.RequestReader.RequestReader;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс для выполнения команды
 */
public class CommandInvoker {
    private final HashMap<String, AbstractCommand> hashMap;

    public CommandInvoker(RequestReader requestReader) {
        this.hashMap = new HashMap<>();
        History = new ArrayDeque<>(7);
        ClientCommandReceiver clientCommandReceiver = new ClientCommandReceiver(requestReader);
        register("help", new HelpCommand(clientCommandReceiver));
        register("clear", new ClearCommand(requestReader.getCollectionManagement()));
        register("show", new ShowCommand(requestReader.getCollectionManagement()));
        register("save", new SaveCommand(clientCommandReceiver));
        register("add", new AddCommand(requestReader.getCollectionManagement()));
        register("history", new HistoryCommand(clientCommandReceiver.requestReader));
        register("update", new UpdateCommand(requestReader.getCollectionManagement()));
        register("min_by_students_count", new MinByStudentsCountCommand(requestReader.getCollectionManagement()));
        register("remove_by_id", new RemoveByIdCommand(requestReader.getCollectionManagement()));
        register("load", new LoadCommand(requestReader));
        register("remove_lower", new RemoveLowerCommand(requestReader.getCollectionManagement()));
        register("remove_higher", new RemoveHigherCommand(requestReader.getCollectionManagement()));
        register("count_greater_than_form_of_education", new CountGreaterCommand(requestReader.getCollectionManagement()));
        register("filter_by_semester_enum", new FilterBySemCommand(requestReader.getCollectionManagement()));
        register("info", new InfoCommand(requestReader.getCollectionManagement()));
        register("execute_script", new ExecuteCommand(this));
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

package com.company.work_client;
import com.company.commands.AbstractCommand;
import com.company.parsers.XMLParser;
import java.util.HashMap;


public class ClientCommandReceiver {
    Client client;

    public ClientCommandReceiver(Client client) {
        this.client = client;
    }

    public void help() {
        HashMap<String, AbstractCommand> hashMap = client.getCommandInvoker().getHashMap();
        System.out.printf("%-45s %-45s %n", "ИМЯ КОМАНДЫ", "ОПИСАНИЕ");
        for (String commandName : hashMap.keySet()) {
            System.out.printf("%-45s %-45s %n", commandName, client.getCommandInvoker().getHashMap().get(commandName).describe());

        }
    }

    public void save() {
        if (client.getFilePath() != null && !client.getFilePath().matches("[/\\\\]dev.*")) {
            XMLParser xmlParser = new XMLParser(client.getFilePath());
            xmlParser.saveCollection(client.getCollectionManagement());
        }
    }

    public void exit() {

    }
}

package com.company.commands;

import com.company.parsers.XMLParser;
import com.company.work_client.Client;

public class LoadCommand extends AbstractCommand {
    private final Client client;

    public LoadCommand(Client client) {
        this.client = client;

        }
        @Override
        public void execute(String CommandArgs) {
        if(client.getFilePath() != null && !client.getFilePath().matches("[/\\\\]dev.*")) {
            XMLParser parser = new XMLParser(client.getFilePath());
            client.setCollectionManagement(parser.deParseCollection());
        }
        }

        @Override
        public String describe() {
        return ("Загружает коллекцию из файла");
    }
    }

package com.company.commands;

import com.company.parsers.XMLParser;
import com.company.server.RequestReader.RequestReader;

public class LoadCommand extends AbstractCommand {
    private final RequestReader requestReader;

    public LoadCommand(RequestReader requestReader) {
        this.requestReader = requestReader;

        }
        @Override
        public String execute(String CommandArgs) {
        if(requestReader.getFilePath() != null && !requestReader.getFilePath().matches("[/\\\\]dev.*")) {
            XMLParser parser = new XMLParser(requestReader.getFilePath());
            requestReader.setCollectionManagement(parser.deParseCollection());
        }
        return "";
        }

        @Override
        public String describe() {
        return ("Загружает коллекцию из файла");
    }
    }

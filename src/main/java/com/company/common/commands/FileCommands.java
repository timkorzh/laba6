package com.company.common.commands;

import com.company.server.processing.collection_manage.CollectionManagement;
import com.company.server.processing.parsers.XMLParser;

public class FileCommands {
    private String filePath;
    private SaveCommand saveCommand;
    private LoadCommand loadCommand;

    public FileCommands(CollectionManagement collectionManagement, String filePath) {
        this(collectionManagement);
        this.filePath = filePath;
    }

    public FileCommands(CollectionManagement collectionManagement) {
        saveCommand = new SaveCommand(collectionManagement);
        loadCommand = new LoadCommand(collectionManagement);
    }

    public SaveCommand getSaveCommand() { return saveCommand; }

    public LoadCommand getLoadCommand() { return loadCommand; }

    public class SaveCommand extends AbstractCommand {
        private final CollectionManagement collectionManagement;

        public SaveCommand(CollectionManagement collectionManagement) {
            this.collectionManagement = collectionManagement;
        }

        @Override
        public String execute(String CommandArgs) {
            if (filePath != null && !filePath.matches("[/\\\\]dev.*")) {
                XMLParser xmlParser = new XMLParser(filePath);
                xmlParser.saveCollection(collectionManagement);
                return "Коллекция сохранена в файл " + filePath;
            } else {//TODO: стоит сообщать, если ничего не произошло
                return "Не удалось сохранить коллекцию";
            }
        }

        @Override
        public String describe () {
            return ("сохранить коллекцию в файл");
            }
        }

    public class LoadCommand extends AbstractCommand {
        private final CollectionManagement collectionManagement;

        public LoadCommand(CollectionManagement collectionManagement) {
            this.collectionManagement = collectionManagement;
        }

        @Override
        public String execute(String filePath) {
            if(filePath != null) {
                if (!filePath.matches("[/\\\\]dev.*"))
                    FileCommands.this.filePath = filePath;
                else return "Файл " + filePath + " недоступен для загрузки"; //TODO: Ok?
            }

            if(FileCommands.this.filePath != null && !FileCommands.this.filePath.matches("[/\\\\]dev.*")) {
                XMLParser parser = new XMLParser(FileCommands.this.filePath);
                collectionManagement.getCollection().addAll(parser.deParseCollection().getCollection());
                //requestReader.setCollectionManagement(parser.deParseCollection()); //TODO: clean
                return "Коллекция загружена из файла " + FileCommands.this.filePath;
            } else {
                return "Не удалось загрузить коллекцию из файла " + FileCommands.this.filePath;
            }
        }

        @Override
        public String describe() {
            return ("Загружает коллекцию из файла");
        }
    }
}

package com.company.work_client;
import com.company.collection_manage.CollectionManagement;
import com.company.commands.*;
import java.util.Scanner;

public class Client {
    private final CommandInvoker commandInvoker;
    private final CollectionManagement collectionManagement;
    private final String filePath;

    public Client(CollectionManagement collectionManagement, String filePath) {
        this.collectionManagement = collectionManagement;
        this.commandInvoker = new CommandInvoker();
        ClientCommandReceiver clientCommandReceiver = new ClientCommandReceiver(this);
        this.filePath = filePath;
        commandInvoker.register("help", new HelpCommand(clientCommandReceiver));
        commandInvoker.register("clear", new ClearCommand(this.collectionManagement));
        commandInvoker.register("show", new ShowCommand(this.collectionManagement));
        commandInvoker.register("save", new SaveCommand(clientCommandReceiver));
        commandInvoker.register("add", new AddCommand(this.collectionManagement));
        commandInvoker.register("history", new HistoryCommand(clientCommandReceiver.client));
        commandInvoker.register("update", new UpdateCommand(this.collectionManagement));
        commandInvoker.register("min_by_students_count", new MinByStudentsCountCommand(this.collectionManagement));
        commandInvoker.register("remove_by_id", new RemoveByIdCommand(this.collectionManagement));
        commandInvoker.register("load", new LoadCommand(this));
        commandInvoker.register("remove_lower", new RemoveLowerCommand(this.collectionManagement));
        commandInvoker.register("remove_higher", new RemoveHigherCommand(this.collectionManagement));
        commandInvoker.register("count_greater_than_form_of_education", new CountGreaterCommand(this.collectionManagement));
        commandInvoker.register("filter_by_semester_enum", new FilterBySemCommand(this.collectionManagement));
        commandInvoker.register("info", new InfoCommand(this.collectionManagement));
        commandInvoker.register("execute_script", new ExecuteCommand(this.commandInvoker));
    }

    public CommandInvoker getCommandInvoker() {
        return commandInvoker;
    }

    public CollectionManagement getCollectionManagement() {
        return collectionManagement;
    }

    public void setCollectionManagement(CollectionManagement collectionManagement) {
        this.collectionManagement.getCollection().addAll(collectionManagement.getCollection());
    }

    public void start() {
        commandInvoker.execute("load");
        System.out.println("Готов начать работу, уважаемый пекарь");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("exit")) {
            commandInvoker.execute(line);
            line = scanner.nextLine();
        }
    }

    public String getFilePath() { return filePath; }
}

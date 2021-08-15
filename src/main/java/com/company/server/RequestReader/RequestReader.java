package com.company.server.RequestReader;
import com.company.collection_manage.CollectionManagement;
import com.company.commands.*;
import com.company.server.CommandInvoker.CommandInvoker;
import com.company.server.CommandInvoker.ClientCommandReceiver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;
/**
 * Класс для распознавния команды
 */
public class RequestReader {
    private final CommandInvoker commandInvoker;
    private final CollectionManagement collectionManagement;
    private final String filePath;

    public RequestReader(CollectionManagement collectionManagement, String filePath) {
        this.collectionManagement = collectionManagement;
        this.commandInvoker = new CommandInvoker(this);
        this.filePath = filePath;
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

    public void start(int PORT) throws IOException {
        commandInvoker.execute("load");
        System.out.println("Готов начать работу, уважаемый пекарь");
        byte[] b = new byte[10];
        SocketAddress a =
                new InetSocketAddress(PORT);
        DatagramSocket s =
                new DatagramSocket(a);
        DatagramPacket i =
                new DatagramPacket(b, b.length);

        String command = "";
        while (true) {
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                while(!command.endsWith("\n")) {
                    s.receive(i);
                    out.write(b);
                    command = out.toString().replaceAll("\00", "");
                    Arrays.fill(b, (byte) 0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            command = command.substring(0, command.length() - 1);
            if(command.equals("exit")) {
                break;
            }
            if(!command.equals("")) {

                commandInvoker.execute(command);
            }
        }
    }

    public String getFilePath() { return filePath; }
}

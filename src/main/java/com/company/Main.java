package com.company;


import com.company.collection_manage.CollectionManagement;
import com.company.work_client.Client;

import java.net.SocketException;

public class  Main {

    public static void main(String[] args) throws SocketException {
        String fileName = System.getenv("INPUT_FILE_PATH");
        if(fileName == null) {
            System.out.println("Переменная среды \"INPUT_FILE_PATH\" пустая. Методы load и save не будут работать(");
        }
        else {
            if (fileName.matches("[/\\\\]dev.*")) {
                System.out.println("Не могу исполнить данный файл");

            }
        }
        Client client = new Client(new CollectionManagement(), fileName);
        int PORT = 2222;
        client.start(PORT);
    }
}

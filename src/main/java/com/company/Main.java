package com.company;


import com.company.collection_manage.CollectionManagement;
import com.company.server.RequestReader.RequestReader;

import java.io.IOException;
import java.net.SocketException;

public class  Main {
    //TODO: proper names and packages
    //TODO: request ending
    //TODO: Модуль приёма подключений. (RequestBuilder)
    //TODO: Модуль чтения запроса. (RequestReader)
    //TODO: Модуль обработки полученных команд. (CommandInvoker)
    //TODO: Модуль отправки ответов клиенту. (ServerPrintStream)

    public static void main(String[] args) throws SocketException, IOException {
        String fileName = System.getenv("INPUT_FILE_PATH");
        if(fileName == null) {
            System.out.println("Переменная среды \"INPUT_FILE_PATH\" пустая. Методы load и save не будут работать(");
        }
        else {
            if (fileName.matches("[/\\\\]dev.*")) {
                System.out.println("Не могу исполнить данный файл");

            }
        }
        RequestReader requestReader = new RequestReader(new CollectionManagement(), fileName);
        int PORT = 22;
        requestReader.start(PORT);
    }
}

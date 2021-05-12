package com.company;


import com.company.collection_manage.CollectionManagement;
import com.company.work_client.Client;

public class  Main {

    public static void main(String[] args) {
        String fileName = System.getenv("INPUT_FILE_PATH");
        if(fileName == null) {
            System.out.println("Переменная среды \"INPUT_FILE_PATH\" пустая. Методы load и save не будут работать(");
        }
        Client client = new Client(new CollectionManagement(), fileName);
        client.start();
    }
}

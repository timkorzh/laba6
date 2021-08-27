package com.company.server;

import com.company.server.ConnectionManager.ConnectionManager;

public class Lab6ServerMain {
    private static final int DEFAULT_PORT = 22;

    public static void main(String[] args) {
        int port;
        try {
            port = Integer.valueOf(System.getenv("L6_SERVER_PORT"));
        } catch (NumberFormatException nfe) {
            port = DEFAULT_PORT;
            System.out.println("Установлено значение порта по умолчанию: " + port);
        }
        ConnectionManager connectionManager = new ConnectionManager(port);
    }
}

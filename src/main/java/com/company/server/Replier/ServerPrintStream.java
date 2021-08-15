package com.company.server.Replier;

import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * Класс для отправки сообщений на клиент
 */
public class ServerPrintStream extends PrintStream {
    public ServerPrintStream(String adress) throws FileNotFoundException {
        //TODO
        super(adress);
    }

}

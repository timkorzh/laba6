package com.company.server.Replier;

import java.io.*;
import java.net.InetAddress;

/**
 * Класс для отправки сообщений на клиент
 */
//TODO: ?
public class ServerOutputStream extends OutputStream {
    Replier replier;
    byte[] buf;

    public ServerOutputStream(Replier replier) {
        //TODO
        this.replier = replier;
    }

    @Override
    public void write(int b) throws IOException {

    }
}

package com.company.server.Replier;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Класс для управления ответами сервера
 */
public class Replier extends OutputStream {
    private ByteArrayOutputStream bArrStream;
    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    public Replier(DatagramSocket socket, InetAddress address, int port) {
        this(socket);
        this.address = address;
        this.port = port;
    }

    public Replier(DatagramSocket socket) {
        this.socket = socket;
        bArrStream = new ByteArrayOutputStream();
    }

    public void setAddressPort(InetAddress address, int port) {
        this.address = address;
        setPort(port);
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void write(int b) throws IOException {
        bArrStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        bArrStream.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
        DatagramPacket packet = new DatagramPacket(bArrStream.toByteArray(),
                bArrStream.size(), address, port);
        socket.send(packet);
        bArrStream.reset();
    }
}

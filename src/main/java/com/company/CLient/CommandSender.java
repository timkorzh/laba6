package com.company.CLient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class CommandSender {
    public CommandSender(InetAddress addr, int port) throws IOException {
        this.socketAddress = new InetSocketAddress(addr, port);

        this.datagramChannel = (DatagramChannel)ConnectionSetter.getDatagramChannel(this.socketAddress).configureBlocking(false);

        this.datagramSocket = new DatagramSocket();
        this.datagramSocket.setSoTimeout(10000);
    }

    private SocketAddress socketAddress;
    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    private DatagramChannel datagramChannel;

    private DatagramSocket datagramSocket;

    public void send(String command, SocketAddress a) throws IOException {
        byte[] b = command.getBytes();
        ByteBuffer f;
        for(int i = 0; i < b.length; i += 10) {
            int length = Math.min(10, b.length - i);
            f = ByteBuffer.wrap(b, i, length);
            datagramChannel.send(f, a);
        }
    }

    public String receive() {
        byte[] buffer = new byte[1024];

        DatagramPacket reply = new DatagramPacket(buffer, buffer.length, this.socketAddress);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            do {
                this.datagramSocket.receive(reply);
                out.write(buffer);
            } while (buffer.length > 0);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toString();
    }
}
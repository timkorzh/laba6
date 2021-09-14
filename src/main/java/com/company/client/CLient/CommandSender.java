package com.company.client.CLient;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import com.company.common.cmdparcel.CommandParcel;

public class CommandSender {
    public CommandSender(InetAddress addr, int port) throws IOException {
        this.socketAddress = new InetSocketAddress(addr, port);
        this.datagramSocket = new DatagramSocket();
        //this.datagramChannel = (DatagramChannel)ConnectionSetter.getDatagramChannel(this.socketAddress).configureBlocking(false);
    }

    private SocketAddress socketAddress;

    private DatagramSocket datagramSocket;

    public CommandSender() {

    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    //private DatagramChannel datagramChannel;

      public void send(String command, SocketAddress a) throws IOException {
        send(new CommandParcel(command), a);
    }

    public void send(String command, Serializable args, SocketAddress a) throws IOException {
        send(new CommandParcel(command, args), a);
    }

    public void send(CommandParcel commandParcel, SocketAddress a) throws IOException {

        //TODO: реализацию можно вынести в специальный поток
        ByteBuffer bBuf;
        byte[] bArr;
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutputStream objOut = new ObjectOutputStream(byteOut)) {
            objOut.writeObject(commandParcel);
            bArr = byteOut.toByteArray();
        }

        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutputStream objOut = new ObjectOutputStream(byteOut)) {
            objOut.writeObject(bArr.length);
            bBuf = ByteBuffer.wrap(byteOut.toByteArray(), 0, byteOut.size());
        }

        DatagramPacket packet = new DatagramPacket(bArr, bArr.length, a);
        datagramSocket.send(packet);
        //datagramChannel.send(bBuf, a);

        for(int i = 0; i < bArr.length; i += 32757) {
            int length = Math.min(32757, bArr.length - i);
            bBuf = ByteBuffer.wrap(bArr, i, length);
            //datagramChannel.send(bBuf, a);
        }
    }

    public String receive() {
        byte[] buffer = new byte[1024];

        DatagramPacket reply = new DatagramPacket(buffer, buffer.length, this.socketAddress);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            do {
                this.datagramSocket.receive(reply);
                out.write(reply.getData(), reply.getOffset(), reply.getLength());
            } while (reply.getLength() == buffer.length);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toString();

    }
}
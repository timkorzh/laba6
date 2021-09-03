package com.company.client.CLient;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import com.company.common.cmdparcel.CommandParcel;

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

    //TODO: Убрать параметр SocketAddress
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
            //objOut.flush();
            bArr = byteOut.toByteArray();
        }

        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutputStream objOut = new ObjectOutputStream(byteOut)) {
            objOut.writeObject(new Integer(bArr.length));
            //objOut.flush();
            bBuf = ByteBuffer.wrap(byteOut.toByteArray(), 0, byteOut.size());
        }
        datagramChannel.send(bBuf, a);

        //TODO: определить размер пакетов
        for(int i = 0; i < bArr.length; i += 32757) {
            int length = Math.min(32757, bArr.length - i);
            bBuf = ByteBuffer.wrap(bArr, i, length);
            datagramChannel.send(bBuf, a);
        }
    }

public class ReplyReceiver {
//TODO: вынести в отдельный файл
        public ReplyReceiver(DatagramChannel datagramChannel) {
            this.datagramChannel = datagramChannel;
        }
        private DatagramChannel datagramChannel;
}

    public String receive() throws IOException {
        StringBuilder answer = new StringBuilder();
        ByteBuffer f = ByteBuffer.allocate(32757);
        SocketAddress s = datagramChannel.receive(f);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            while(!answer.toString().endsWith("\04")) {
                for (int i = 0; i < 10 && s == null; i++) {
                    try {
                        Thread.sleep(1000);
                        s = datagramChannel.receive(f);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(s == null) {
                    System.out.println("no datagram was immediately available");
                    return null;
                }
                out.write(f.array());
                answer.append(out.toString().replaceAll("\00", ""));
                f.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer.deleteCharAt(answer.length() - 1).toString();
    }
}
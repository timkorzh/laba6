package com.company.CLient;

import com.company.CommandParcel.CommandParcel;

import java.io.*;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class CommandSender {
    public CommandSender(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }
    private DatagramChannel datagramChannel;

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
            //objOut.flush(); //TOdo: check if it is necessary
            bArr = byteOut.toByteArray();
        }

        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
             ObjectOutputStream objOut = new ObjectOutputStream(byteOut)) {
            objOut.writeObject(new Integer(bArr.length));
            //objOut.flush();
            bBuf = ByteBuffer.wrap(byteOut.toByteArray(), 0, byteOut.size());
        }

        ///////////////////
        System.out.println(bBuf.array().length);
        for (byte b : bBuf.array()) {
            System.out.print(b + " ");
        }
        System.out.println();
        /////////////////

        test(bBuf);//todo: remove
        datagramChannel.send(bBuf, a);

        //TODO: определить размер пакетов
        for(int i = 0; i < bArr.length; i += 32757) {
            int length = Math.min(32757, bArr.length - i);
            bBuf = ByteBuffer.wrap(bArr, i, length);
            datagramChannel.send(bBuf, a);
        }
    }
    //Todo: remove
    private void test(ByteBuffer bBuf) {
        byte[] data = bBuf.array();
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(data))) {
            System.out.println((Integer) objIn.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
        String answer = "";
        ByteBuffer f = ByteBuffer.allocate(1000);
        SocketAddress s = datagramChannel.receive(f);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            while(!answer.endsWith("\04")) {
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
                answer = out.toString().replaceAll("\00", "");
                f.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new String(f.array());
    }
}
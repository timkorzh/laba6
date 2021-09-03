package com.company.server.ConnectionManager;

import com.company.common.CommandParcel.CommandParcel;
import com.company.server.CommandInvoker.CommandInvoker;
import com.company.server.Replier.Replier;
import com.company.server.RequestBuilder.RequestBuilder;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ConnectionManager {
    private final int port;
    private RequestBuilder requestBuilder;
    private Replier replier;
    //private RequestReader requestReader;
    private DatagramSocket socket;
    private DatagramPacket packet;

    public ConnectionManager(int port) throws IOException {
        this.port = port;
        requestBuilder = new RequestBuilder();
        byte[] b = new byte[32757];
        SocketAddress a = new InetSocketAddress(port);
        socket = new DatagramSocket(a);
        socket.setSoTimeout(1000);
        packet = new DatagramPacket(b, b.length);
        replier = new Replier(socket);
        //requestReader = new RequestReader(new CollectionManagement(), "f"); //TODO: change args or delete RequestReader
    }

    public RequestBuilder getRequestBuilder() { return requestBuilder; }

    public Replier getReplier() { return replier; }

    Scanner sc = new Scanner(System.in);
    private boolean checkExit() throws IOException {
        return (System.in.available() > 0 && sc.nextLine().split(" ")[0].equals("exit"));
    }

    public void start(CommandInvoker invoker) throws IOException, ClassNotFoundException {
        CommandParcel parcel;
        boolean receiveTimedOut;
        while (true) {
            if (checkExit()) return;
            do {
                receiveTimedOut = false;
                try {
                    socket.receive(packet);
                } catch (SocketTimeoutException ste) {
                    if (checkExit()) return; //Todo: Эта проверка должна быть на уровень выше
                    receiveTimedOut = true;
                }
            } while (receiveTimedOut || (parcel = requestBuilder.append(packet)) == null);

            replier.setAddressPort((InetSocketAddress) packet.getSocketAddress());
            invoker.execute(parcel.getCommand(), parcel.getArgs());
            replier.flush();
            /*
            Для многопоточности
            someCommandQueue.put(parcel);
            */
        }
    }
}

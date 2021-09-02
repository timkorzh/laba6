package com.company.server.ConnectionManager;

import com.company.common.CommandParcel.CommandParcel;
import com.company.server.CommandInvoker.CommandInvoker;
import com.company.server.Replier.Replier;
import com.company.server.RequestBuilder.RequestBuilder;

import java.io.IOException;
import java.net.*;

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
        packet = new DatagramPacket(b, b.length);
        replier = new Replier(socket);
        //requestReader = new RequestReader(new CollectionManagement(), "f"); //TODO: change args or delete RequestReader
    }

    public RequestBuilder getRequestBuilder() { return requestBuilder; }

    public Replier getReplier() { return replier; }

    public void start(CommandInvoker invoker) throws IOException, ClassNotFoundException {
        CommandParcel parsel;
        while (true) {
            socket.receive(packet);
            while ((parsel = requestBuilder.append(packet)) == null) socket.receive(packet);

            replier.setAddressPort((InetSocketAddress) packet.getSocketAddress());
            invoker.execute(parsel.getCommand());
            replier.flush();
            /*
            Для многопоточности
            someCommandQueue.put(parsel);
            */
        }
    }
}

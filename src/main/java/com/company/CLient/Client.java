package com.company.CLient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.util.Scanner;

public class Client {
    private DatagramChannel datagramChannel;
    private final SocketAddress socketAddress;
    private final CommandSender commandSender;
    public Client(SocketAddress a) throws IOException {
        DatagramChannel d = (DatagramChannel)ConnectionSetter.getDatagramChannel(a).configureBlocking(false);

        socketAddress = a;
        commandSender = new CommandSender(d);
    }
    public static void main(String[] args) throws IOException {


                SocketAddress a =
                        new InetSocketAddress(InetAddress.getLocalHost(), 22);
                Client client = new Client(a);
                client.start();

    }
    private boolean connectionCheck() throws IOException {


                commandSender.send("connection_check\n", socketAddress);
                String s = commandSender.receive();
                if (s == null) {
                    return false;
                }

        return true;
    }

    public void start() throws IOException {
        //commandSender.send("show\n", socketAddress);
        System.out.println("Готов начать работу, уважаемый пекарь");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        String reply = null;
        while (!line.equals("exit")) {

            try {

                    commandSender.send(line + "\n", socketAddress);
                    reply = commandSender.receive();


            } catch (PortUnreachableException e) {
                System.out.println("Gopa poppa");
            }
            if(reply == null) {
                System.out.println("Сервер временно недоступен, уважаемый пекарь( Кажется, кто-то не заплатил за интернет.");
            } else {
                System.out.println(reply);
            }
            line = scanner.nextLine();
            reply = null;
        }
    }
}
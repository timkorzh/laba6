package com.company.client.CLient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.util.Scanner;

public class Client {
    private final CommandSender commandSender;

    public Client(InetAddress addr, int port) throws IOException {
        commandSender = new CommandSender(addr, port);
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client(InetAddress.getLocalHost(), 22);
        client.start();
    }

    private boolean connectionCheck() throws IOException {


                commandSender.send("connection_check\n", this.commandSender.getSocketAddress());
                String s = commandSender.receive();
                if (s == null) {
                    return false;
                }

        return true;
    }

    public void start() throws IOException {
        //commandSender.send("show\n", this.commandSender.getSocketAddress());
        System.out.println("Готов начать работу, уважаемый пекарь");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        String reply = null;
        while (!line.equals("exit")) {

            try {

                    commandSender.send(line + "\n", this.commandSender.getSocketAddress());
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
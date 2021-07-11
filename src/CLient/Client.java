package CLient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Client {
    private DatagramChannel datagramChannel;
    private final SocketAddress socketAddress;
    private final CommandSender commandSender;
    public Client(SocketAddress a) throws IOException {
        DatagramChannel d = ConnectionSetter.getDatagramChannel(a);
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
        if(s == null) {
            return false;
        }
        return true;
    }

    public void start() throws IOException {
        commandSender.send("load\n", socketAddress);
        System.out.println("Готов начать работу, уважаемый пекарь");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        String reply = null;
        while (!line.equals("exit")) {

            if(connectionCheck()) {
                commandSender.send(line + "\n", socketAddress);
                reply = commandSender.receive();
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
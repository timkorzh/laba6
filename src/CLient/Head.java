package CLient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

public class Head {
    public static void main(String[] args) throws IOException {
        SocketAddress a =
                new InetSocketAddress(InetAddress.getLocalHost(), 22);
        DatagramChannel d = ConnectionSetter.getDatagramChannel(a);
        start(d, a);

    }
    public static void start(DatagramChannel d, SocketAddress a) throws IOException {
        CommandSender commandSender = new CommandSender(d);
        commandSender.send("load\n", a);
        System.out.println("Готов начать работу, уважаемый пекарь");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        while (!line.equals("exit")) {
            commandSender.send(line + "\n", a);
            line = scanner.nextLine();
        }
    }
}
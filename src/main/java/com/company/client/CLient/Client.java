package com.company.client.CLient;

import com.company.client.validation.CommandMethods;
import com.company.client.validation.CommandMethodsExecute;
import com.company.client.validation.InputDevice;
import com.company.common.collection_objects.Semester;
import com.company.common.collection_objects.StudyGroup;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String line = scanner.nextLine().trim();

        String reply = null;
        while (!line.equals("exit")) {

            try {
                Pattern pCommand = Pattern.compile("(\\w*)[\\s-]?");
                Pattern pArgs = Pattern.compile("(\\s-.*)$");
                Matcher cmd = pCommand.matcher(line);
                cmd.find();
                String commandName = cmd.group(0);
                Matcher args = pArgs.matcher(line);
                boolean hasArgs = args.find();
                InputDevice inputDevice = new InputDevice();
                if (!hasArgs) {

                    StudyGroup studyGroup;

                    if(commandName.equals("add")) {
                        studyGroup = inputDevice.add();
                        commandSender.send(commandName + "\n", studyGroup, this.commandSender.getSocketAddress());
                    } else if(commandName.equals("update")) {
                        studyGroup = inputDevice.update();
                        commandSender.send(commandName + "\n", studyGroup, this.commandSender.getSocketAddress());
                    } else if(commandName.equals("filter_by_semester_enum")) {
                        CommandMethods device = new CommandMethods();
                        Semester FBS = device.readFilterSem();
                        commandSender.send(commandName + "\n", FBS, this.commandSender.getSocketAddress());
                    } else {
                        commandSender.send(commandName + "\n", this.commandSender.getSocketAddress());
                    }
                } else {
                    String commandArgs = args.group(0);
                    commandSender.send(commandName, commandArgs, this.commandSender.getSocketAddress());
                }
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
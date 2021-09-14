package com.company.client.CLient;

import com.company.client.validation.CommandMethods;
import com.company.client.validation.InputDevice;
import com.company.common.collection_objects.StudyGroup;

import java.io.IOException;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    public final CommandSender commandSender;

    public Client(InetAddress addr, int port) throws IOException {
        commandSender = new CommandSender(addr, port);
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client(InetAddress.getLocalHost(), 6756);
        client.start();
    }

    public void start() throws IOException {
        System.out.println("Готов начать работу, уважаемый пекарь");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine().trim();

        String reply = null;
        while (!line.equals("exit")) {

            try {
                Pattern pCommand = Pattern.compile("([a-zA-Zа-яА-ЯёЁ_]*)[\\s-]?");
                Pattern pArgs = Pattern.compile("(\\s-.*)$");
                Matcher cmd = pCommand.matcher(line);
                cmd.find();
                String commandName = cmd.group(0);
                Matcher args = pArgs.matcher(line);
                boolean hasArgs = args.find();
                InputDevice inputDevice = new InputDevice();
                if (!hasArgs) {

                    StudyGroup studyGroup;

                    switch (commandName) {
                        case "add":
                            studyGroup = inputDevice.add();
                            commandSender.send(commandName + "\n", studyGroup, this.commandSender.getSocketAddress());
                            break;
                        case "update":
                            studyGroup = inputDevice.update();
                            commandSender.send(commandName + "\n", studyGroup, this.commandSender.getSocketAddress());
                            break;
                        case "filter_by_semester_enum":
                            CommandMethods device = new CommandMethods();
                            int FBS = device.readFilterSem();
                            commandSender.send(commandName + "\n", String.valueOf(FBS), this.commandSender.getSocketAddress());
                            break;
                        case "execute_script":
                            ScriptExecute scriptExecute = new ScriptExecute(this);
                            scriptExecute.execute(null);
                            break;
                        default:
                            commandSender.send(commandName + "\n", this.commandSender.getSocketAddress());
                            break;
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
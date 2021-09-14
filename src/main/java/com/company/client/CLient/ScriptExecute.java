package com.company.client.CLient;

import com.company.client.validation.CommandMethods;
import com.company.client.validation.InputDevice;
import com.company.common.collection_objects.StudyGroup;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ScriptExecute {
    Client client;
    public ScriptExecute(Client client) {this.client = client;}
    public String execute(String CommandArgs) {
        CommandSender commandSender = client.commandSender;
        boolean finished = false;
        String result = "";
        CommandMethods device = new CommandMethods();
        Path REF = null;
        if (CommandArgs == null) {
            System.out.println("Введите путь к файлу");
            REF = device.readExecuteFilePath();
            if(REF.toString().matches("[/\\\\]dev.*")) {
                result = "Не могу исполнить данный файл";
                finished=true;
            }
        } else {

            Pattern p = Pattern.compile("-path (.+?)( -|$)");
            Matcher m = p.matcher(CommandArgs);

            if (m.find()) {
                REF = Paths.get(m.group(1));
                Pattern b = Pattern.compile("[/\\\\]dev.*");
                Matcher n = b.matcher(m.group(1));
                if (n.find()) {
                    result = "Не могу исполнить данный файл";
                    finished=true;
                }
            }
            else {
                result = "Ожидался путь к файлу";
                finished = true;
            }
        }
        if (!finished) {
            try {
                Scanner FileScanner = new Scanner(REF);
                FileScanner.useDelimiter(System.getProperty("line.separator"));
                String scanner = FileScanner.nextLine();
                InputDevice inputDevice = new InputDevice();
                StudyGroup studyGroup;
                while (FileScanner.hasNext()) {
                    commandSender.send(scanner, commandSender.getSocketAddress());
                    if (scanner.equals("add")) {
                        studyGroup = inputDevice.add();
                        commandSender.send(scanner + "\n", studyGroup, commandSender.getSocketAddress());
                    } else if (scanner.equals("update")) {
                        studyGroup = inputDevice.update();
                        commandSender.send(scanner + "\n", studyGroup, commandSender.getSocketAddress());
                    } else if (scanner.equals("filter_by_semester_enum")) {
                        int FBS = device.readFilterSem();
                        commandSender.send(scanner + "\n", String.valueOf(FBS), commandSender.getSocketAddress());
                    } else if (scanner.equals("execute_script")) {
                        ScriptExecute scriptExecute = new ScriptExecute(client);
                        scriptExecute.execute(null);
                    } else {
                        commandSender.send(scanner + "\n", commandSender.getSocketAddress());
                    }
                    scanner = FileScanner.nextLine();
                    String reply = commandSender.receive();
                    System.out.println(reply);
                }
            }
            catch (IOException e) {
                System.out.println("Мне жаль, что так вышло((((");
            }
            }
        else {
            System.out.println(result);
        }
        return result;
        } 
        
    }
    

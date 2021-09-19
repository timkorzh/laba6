package com.company.client.CLient;

import com.company.client.validation.CommandMethods;
import com.company.client.validation.InputDevice;
import com.company.common.collection_objects.StudyGroup;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
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
        String filePath = null;
        if (CommandArgs == null) {
            System.out.println("Введите путь к файлу");
            filePath = device.readExecuteFilePath();
        } else {
            Pattern p = Pattern.compile("-path (.+?)( -|$)");
            Matcher m = p.matcher(CommandArgs);
            if (m.find()) {
                filePath = m.group(1);
            }
        }
            if (filePath != null) {
                Pattern b = Pattern.compile("[/\\\\]dev.*");
                Matcher n = b.matcher(filePath);
                if (n.find()) {
                    return "Не могу исполнить данный файл";
                }
                REF = Paths.get(filePath);
                if (!((new File(REF.toString()).exists()))) {
                    System.out.println("Не нашёл такой файл, пекарб((");
                    finished=true;
                }
            }
            else {
                result = "Ожидался путь к файлу";
                finished = true;
            }

        if (!finished) {
            try {
                Scanner FileScanner = new Scanner(REF);
                FileScanner.useDelimiter(System.getProperty("line.separator"));
                Pattern pCommand = Pattern.compile("([a-zA-Zа-яА-ЯёЁ_]*)[\\s-]?");
                Pattern pArgs = Pattern.compile("(\\s-.*)$");
                InputDevice inputDevice = new InputDevice();
                StudyGroup studyGroup;

                label:
                do {
                    String command = FileScanner.nextLine();
                    Matcher cmd = pCommand.matcher(command);
                    cmd.find();
                    String commandName = cmd.group(0).trim();
                    Matcher args = pArgs.matcher(command);
                    String commandArgues = args.group(1).trim();
                    boolean hasArgs = args.find();

                    if(command == null || command.isEmpty()) {
                        continue;
                    }
                    System.out.println("Выполняю: " + command);
                    switch (commandName) {
                        case "add":
                            if (hasArgs) {
                                studyGroup = inputDevice.addFromFile(commandArgues);
                            } else {
                                studyGroup = inputDevice.add();
                            }
                            commandSender.send(commandName + "\n", studyGroup, commandSender.getSocketAddress());
                            break label;
                        case "update":
                            if (hasArgs) {
                                studyGroup = inputDevice.updateFromFile(commandArgues);
                            } else {
                                studyGroup = inputDevice.update();
                            }
                            if (studyGroup != null) {
                                commandSender.send(commandName + "\n", studyGroup, commandSender.getSocketAddress());
                            }
                            break label;
                        case "filter_by_semester_enum":
                            int FBS;
                            try {
                                FBS = device.readFilterSem();
                            } catch (InputMismatchException e) {
                                System.out.println("Вы ввели какую-то чушь. Я для вас какая-то шутка?");
                                break;
                            }
                            commandSender.send(commandName + "\n", String.valueOf(FBS), commandSender.getSocketAddress());
                            break label;
                        case "execute_script":
                            ScriptExecute scriptExecute = new ScriptExecute(client);
                            if(hasArgs) {
                                scriptExecute.execute(commandArgues);
                            } else {
                                scriptExecute.execute(null);
                            }
                            break label;
                        case "remove_by_id" :
                        case "remove_lower" :
                        case "remove_higher" :
                            if (hasArgs) {
                                commandSender.send(commandName + "\n", commandArgues, commandSender.getSocketAddress());
                            }
                            int removeById = device.removeById();
                            commandSender.send(commandName + "\n", String.valueOf(removeById), commandSender.getSocketAddress());
                            break;
                        default:
                            commandSender.send(command + "\n", commandSender.getSocketAddress());
                            break;
                    }

                    commandSender.receive();  //заплатка от получения старого ответа
                    String reply = commandSender.receive();
                    System.out.println(reply);
                } while (FileScanner.hasNext());
            }catch (IOException  e) {
                System.out.println("Мне жаль, что так вышло((((");
            }
        }
        else {
            System.out.println(result);
        }
        return result;
        }
    }
    

package com.company.common.commands;

import com.company.server.recognition.CommandInvoker;
import com.company.client.validation.CommandMethods;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExecuteCommand extends AbstractCommand {

    CommandInvoker commandInvoker;
    public ExecuteCommand(CommandInvoker commandInvoker) {
        this.commandInvoker = commandInvoker;
    }

        @Override
        public void execute(String CommandArgs) {
            boolean finished = false;
            String result = "";
            //InputDevice device = new InputDevice();
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
                    while (FileScanner.hasNext()) {
                        commandInvoker.execute(FileScanner.nextLine());
                    }
                }
                catch (IOException e) {
                    System.out.println("Мне жаль, что так вышло((((");
                }
            } else {
                System.out.println(result);
            }
            return;
            //return result;
        }

        @Override
        public String describe() {
        return ("Считывает и исполняет команды из файла. Введите -path и путь к файлу");
    }
}

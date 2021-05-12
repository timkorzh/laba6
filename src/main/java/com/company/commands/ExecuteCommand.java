package com.company.commands;

import com.company.work_client.CommandInvoker;
import com.company.work_client.InputDevice;

import javax.lang.model.element.Name;
import java.io.IOException;
import java.io.OutputStream;
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
        InputDevice device = new InputDevice();
        Path REF = null;
            if (CommandArgs == null) {
                System.out.println("Введите путь к файлу");
                REF = device.ReadExecuteFilePath();
            } else {
                Pattern p = Pattern.compile("-path (.+?)( -|$)");
                Matcher m = p.matcher(CommandArgs);
                if (m.find()) {
                    REF = Paths.get(m.group(1));
                    if(m.group(1) == "/dev/null") {
                        System.out.println("Не могу исполнить данный файл");
                    }
                }
                    else {
                        System.out.println("Ожидался путь к файлу");
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
                e.printStackTrace();
                }
            }
        }

        @Override
        public String describe() {
        return ("Считывает и исполняет команды из файла. Введите -path и путь к файлу");
    }
}

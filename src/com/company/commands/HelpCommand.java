package com.company.commands;

import com.company.work_client.ClientCommandReceiver;

public class HelpCommand extends AbstractCommand {
    private final ClientCommandReceiver clientCommandReceiver;

    public HelpCommand(ClientCommandReceiver clientCommandReceiver) {
        this.clientCommandReceiver = clientCommandReceiver;
        }
        @Override
        public String describe() {
        return ("Выводит справку о командах");
    }

        @Override
        public void execute(String CommandArgs) {
        clientCommandReceiver.help();
    }
    }

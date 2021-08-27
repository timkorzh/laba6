package com.company.CommandParcel;

import java.io.*;

public class CommandParsel implements Serializable {
    private String command;
    private Serializable args;

    public CommandParsel(String command, Serializable args) {
        this.command = command;
        this.args = args;
    }

    public CommandParsel(String command) { this.command = command; }

    public String getCommand() { return command; }

    public Object getArgs() { return args; }
}

package com.company.commands;

public abstract class AbstractCommand {
    public abstract void execute(String CommandArgs);
    public abstract String describe();
}

package com.company.commands;

public abstract class AbstractCommand {
    public abstract void execute(String commandArgs);
    //TODO: настроить для всех команд. Возможно, нужен и строковый аргумент, и объект?
    public void execute(String stingArgs, Object objArgs) { execute(stingArgs); }
    public abstract String describe();
}

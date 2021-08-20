package cn.fusionfish.core.commands.item;

import cn.fusionfish.libs.command.SimpleCommand;

public class ItemCommand extends SimpleCommand {
    public ItemCommand() {
        super("item","it");

        new ItemGetCommand(this);

        setPlayerOnly();
    }

    @Override
    public void onCommand() {

    }
}

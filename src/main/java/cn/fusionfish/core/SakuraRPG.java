package cn.fusionfish.core;

import cn.fusionfish.core.command.TestCommand;
import cn.fusionfish.core.items.ItemManager;
import cn.fusionfish.core.items.listeners.BlockBreakListener;
import cn.fusionfish.libs.plugin.FusionPlugin;

public class SakuraRPG extends FusionPlugin {

    private static ItemManager itemManager;

    @Override
    protected void init() {
        registerListeners(
                BlockBreakListener.class
        );

        itemManager = new ItemManager();
        getCommandManager().registerCommand(new TestCommand());
    }

    @Override
    protected void disable() {

    }

    public static ItemManager getItemManager() {
        return itemManager;
    }
}

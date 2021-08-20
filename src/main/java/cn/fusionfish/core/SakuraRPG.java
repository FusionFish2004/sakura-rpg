package cn.fusionfish.core;

import cn.fusionfish.core.command.TestCommand;
import cn.fusionfish.core.items.ItemManager;
import cn.fusionfish.core.items.listeners.BlockBreakListener;
import cn.fusionfish.libs.plugin.FusionPlugin;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static cn.fusionfish.libs.utils.MessageUtil.log;

public class SakuraRPG extends FusionPlugin {

    private static ItemManager itemManager;
    private static CoreProtectAPI coreProtectAPI;

    @Override
    protected void init() {

        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");
        if (plugin instanceof CoreProtect coreProtect) {
            log("插件软前置CoreProtect已加载！");
            coreProtectAPI = coreProtect.getAPI();
        }

        registerListeners(
                BlockBreakListener.class
        );

        itemManager = new ItemManager();
        getCommandManager().registerCommand(new TestCommand());
    }

    @Override
    protected void disable() {

    }

    @NotNull
    public static ItemManager getItemManager() {
        return itemManager;
    }

    @Nullable
    public static CoreProtectAPI getCoreProtectAPI() {
        return coreProtectAPI;
    }
}

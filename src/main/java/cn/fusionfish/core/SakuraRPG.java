package cn.fusionfish.core;

import cn.fusionfish.core.commands.TestCommand;
import cn.fusionfish.core.commands.item.ItemCommand;
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

        //加载CoreProtect软前置
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");
        if (plugin instanceof CoreProtect coreProtect) {
            log("插件软前置CoreProtect已加载！");
            coreProtectAPI = coreProtect.getAPI();
        }

        //注册监听器
        registerListeners(
                BlockBreakListener.class
        );

        itemManager = new ItemManager();

        //注册命令
        getCommandManager().registerCommand(new TestCommand());
        getCommandManager().registerCommand(new ItemCommand());
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

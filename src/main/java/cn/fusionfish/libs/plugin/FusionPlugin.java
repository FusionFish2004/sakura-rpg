package cn.fusionfish.libs.plugin;

import cn.fusionfish.libs.command.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static cn.fusionfish.libs.utils.MessageUtil.*;

public abstract class FusionPlugin extends JavaPlugin {

    private static FusionPlugin instance;
    private CommandManager commandManager;
    public final File CONFIG_FILE = new File(getDataFolder(), "config.yml");

    /**
     * 获取插件实例
     * @return 插件实例
     */
    public static FusionPlugin getInstance() {
        return instance;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public final void onEnable() {
        instance = this;
        commandManager = new CommandManager();

        if (!getDataFolder().exists()) {
            log("正在创建插件数据文件夹...");
            boolean result = getDataFolder().mkdir();
            if (!result) {
                error("创建插件数据文件夹失败！");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        }

        if (!CONFIG_FILE.exists()) {
            log("正在创建默认插件配置文件...");
            saveDefaultConfig();
        }

        init();
    }

    @Override
    public final void onDisable() {

        commandManager.unregisterCommands();
        disable();
    }

    public final void registerListener(Class<? extends Listener> clazz) {
        try {
            Listener instance = clazz.getDeclaredConstructor().newInstance();
            Bukkit.getPluginManager().registerEvents(instance, this);
            log("正在注册监听器" + clazz.getSimpleName() + "...");
        } catch (Exception e) {
            warn("监听器" + clazz.getSimpleName() + "注册失败！");
        }
    }

    @SafeVarargs
    public final void registerListeners(Class<? extends Listener>... clazz) {
        List<Class<? extends Listener>> classes = Arrays.asList(clazz);
        classes.forEach(this::registerListener);
    }

    protected abstract void init();

    protected abstract void disable();
}

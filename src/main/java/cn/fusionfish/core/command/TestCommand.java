package cn.fusionfish.core.command;

import cn.fusionfish.core.items.customized.tools.BlazingPickaxe;
import cn.fusionfish.libs.command.SimpleCommand;
import org.bukkit.entity.Player;

public class TestCommand extends SimpleCommand {
    public TestCommand() {
        super("test");
        setAdminCommand();
        setPlayerOnly();
    }

    @Override
    public void onCommand() {
        Player player = (Player) sender;
        BlazingPickaxe pickaxe = new BlazingPickaxe();
        pickaxe.setUpgradeLevel(50);
        player.getInventory().addItem(pickaxe.getItemStack());
    }
}

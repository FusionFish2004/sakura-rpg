package cn.fusionfish.core.commands.item;

import cn.fusionfish.core.items.Item;
import cn.fusionfish.core.items.ItemManager;
import cn.fusionfish.libs.command.SimpleCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemGetCommand extends SimpleCommand {

    protected ItemGetCommand(@NotNull SimpleCommand parent) {
        super(parent, "get");
    }

    @Override
    public void onCommand() {

        Player player = (Player) sender;

        if (args.length != 3) {
            sendMsg("参数错误！");
            return;
        }

        String simpleName = args[2];
        String category = args[1];
        Item<?> item = ItemManager.getItem(category, simpleName);

        if (item == null) {
            sendMsg("参数错误！");
            return;
        }

        ItemStack itemStack = item.getItemStack();
        player.getInventory().addItem(itemStack);
    }
}

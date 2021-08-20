package cn.fusionfish.core.items.listeners;

import cn.fusionfish.core.items.Item;
import cn.fusionfish.core.items.ItemManager;
import cn.fusionfish.core.items.customized.tools.Tool;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBreak(@NotNull BlockBreakEvent event) {
        Player player = event.getPlayer();

        Item<? extends Event> item = ItemManager.getInstance().getItem(player);

        if (item == null) {
            return;
        }

        if (!(item instanceof Tool tool)) {
            return;
        }

        tool.execute(event);
    }
}

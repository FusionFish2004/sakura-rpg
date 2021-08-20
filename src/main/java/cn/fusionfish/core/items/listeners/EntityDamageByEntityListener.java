package cn.fusionfish.core.items.listeners;

import cn.fusionfish.core.items.Item;
import cn.fusionfish.core.items.ItemManager;
import cn.fusionfish.core.items.customized.weapons.Weapon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

public class EntityDamageByEntityListener implements Listener {
    @EventHandler
    public void onDamage(@NotNull EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) {
            return;
        }

        Item<?> item = ItemManager.getInstance().getItem(player);

        if (item == null) {
            return;
        }

        if (!(item instanceof Weapon weapon)) {
            return;
        }

        weapon.execute(event);
    }
}

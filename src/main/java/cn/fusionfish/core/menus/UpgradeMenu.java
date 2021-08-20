package cn.fusionfish.core.menus;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class UpgradeMenu implements InventoryHolder {

    private final Inventory inventory = Bukkit.createInventory(this, InventoryType.ANVIL);

    public UpgradeMenu() {

    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }
}

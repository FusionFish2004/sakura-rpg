package cn.fusionfish.core.items.customized.tools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlazingPickaxe extends Tool {

    public BlazingPickaxe() {
        setMaterial(Material.DIAMOND_PICKAXE);
        setUpgradeLevel(0);
        setName("炽热之镐");
    }

    @Override
    public void execute(@NotNull BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material material = block.getType();
        Random random = new Random();

        if (random.nextDouble() < getExplodePercentage(getUpgradeLevel())) {
            Location location = block.getLocation();
            location.createExplosion(player, 4);
            return;
        }

        if (random.nextDouble() < getSmeltPercentage(getUpgradeLevel())) {
            if (!isSmeltable(material)) {
                return;
            }

            event.setDropItems(false);
            smelt(block);
        }
    }

    private static void smelt(@NotNull Block block) {
        Location location = block.getLocation().add(0.5,0.5,0.5);
        World world = location.getWorld();
        ItemStack itemStack = getSmeltedItem(block);

        if (itemStack == null) {
            return;
        }
        world.dropItem(location, itemStack);
    }

    @Nullable
    private static Material getSmelted(@NotNull Material material) {
        return switch (material) {
            case IRON_ORE, DEEPSLATE_IRON_ORE -> Material.IRON_INGOT;
            case GOLD_ORE, DEEPSLATE_GOLD_ORE -> Material.GOLD_INGOT;
            case COPPER_ORE, DEEPSLATE_COPPER_ORE -> Material.COPPER_INGOT;
            case ANCIENT_DEBRIS -> Material.NETHERITE_SCRAP;
            default -> null;
        };
    }

    private static boolean isSmeltable(Material material) {
        return getSmelted(material) != null;
    }

    @Nullable
    private static ItemStack getSmeltedItem(@NotNull Block block) {
        Material material = getSmelted(block.getType());
        if (material == null) {
            return null;
        }
        return new ItemStack(material);
    }

    private static double getSmeltPercentage(int lvl) {
        return 0.6 + lvl * 0.1;
    }

    private static double getExplodePercentage(int lvl) {
        return 0.3 + lvl * 0.1;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}

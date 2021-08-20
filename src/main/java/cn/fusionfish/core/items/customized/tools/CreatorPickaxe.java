package cn.fusionfish.core.items.customized.tools;

import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class CreatorPickaxe extends Tool {

    public CreatorPickaxe() {
        setMaterial(Material.DIAMOND_PICKAXE);
        setUpgradeLevel(0);
        setName("创世神之镐");
    }

    @Override
    public void execute(@NotNull BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Random random = new Random();

        if (random.nextDouble() < getVeinMineRate(getUpgradeLevel())) {
            mineVein(player, block);
            return;
        }

        if (random.nextDouble() < getPenetrateRate(getUpgradeLevel())) {
            penetrate(player, block);
        }

    }

    private static void penetrate(@NotNull Player player, @NotNull Block block) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        Vector direction = player.getLocation().getDirection()
                .clone()
                .setY(0)
                .normalize()
                .multiply(0.3);

        if (direction.equals(new Vector().zero())) {
            return;
        }

        Location location = block.getLocation()
                .clone()
                .add(0.5,0.5,0.5);

        Set<Block> blocks = Sets.newLinkedHashSet();
        while (blocks.size() < 5) {
            location.add(direction);
            blocks.add(location.getBlock());
        }
        //TODO 兼容CoreProtect
        blocks.forEach(b -> b.breakNaturally(itemStack, true));
    }

    private static void mineVein(@NotNull Player player, @NotNull Block block) {

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        Material material = block.getType();
        //方块不是矿物
        if (!isOre(material)) {
            return;
        }

        Stack<Block> stack = new Stack<>();
        Set<Block> buffer = Sets.newHashSet();
        Block b = block;
        stack.add(b);

        while (!stack.isEmpty() && buffer.size() < 10) {
            b = stack.pop();
            buffer.add(b);
            Set<Block> nearOres = getBlockNearBy(b).stream()
                    .filter(block1 -> isOre(block1.getType()))
                    .filter(block1 -> !buffer.contains(block1))
                    .collect(Collectors.toSet());
            if (!nearOres.isEmpty()) {
                nearOres.forEach(stack::push);
            }
        }
        //TODO 兼容CoreProtect
        buffer.forEach(b1 -> b1.breakNaturally(itemStack, true));
    }

    private static boolean isOre(@NotNull Material material) {
        if (material.toString().contains("ORE")) {
            return true;
        }

        return material == Material.ANCIENT_DEBRIS;
    }

    private static Set<Block> getBlockNearBy(@NotNull Block block) {
        Location location = block.getLocation();

        Location l1 = location.clone().add(1, 0, 0);
        Location l2 = location.clone().add(-1, 0, 0);
        Location l3 = location.clone().add(0, 1, 0);
        Location l4 = location.clone().add(0, -1, 0);
        Location l5 = location.clone().add(0, 0, 1);
        Location l6 = location.clone().add(0, 0, -1);

        Set<Location> buffer = Sets.newHashSet(l1,l2,l3,l4,l5,l6);
        return buffer.stream().map(Location::getBlock).collect(Collectors.toSet());
    }

    private static double getPenetrateRate(int lvl) {
        return 0.3 + lvl * 0.1;
    }

    private static double getVeinMineRate(int lvl) {
        return 0.6 + lvl * 0.1;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}

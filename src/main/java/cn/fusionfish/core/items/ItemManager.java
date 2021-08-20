package cn.fusionfish.core.items;

import cn.fusionfish.core.SakuraRPG;
import com.google.common.collect.Maps;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static cn.fusionfish.core.items.Item.KEY_TYPE;
import static cn.fusionfish.core.items.Item.KEY_UUID;

public class ItemManager {
    private final Map<UUID, Item<? extends Event>> itemMap = Maps.newHashMap();

    public @Nullable Item<? extends Event> getItem(@NotNull ItemStack itemStack) {

        if (!itemStack.hasItemMeta()) return null;

        ItemMeta meta = itemStack.getItemMeta();

        PersistentDataContainer container = meta.getPersistentDataContainer();

        //非自定义物品
        if (!isCustomized(container)) return null;

        //获取UUID和Type
        UUID uuid = UUID.fromString(Objects.requireNonNull(container.get(KEY_UUID, PersistentDataType.STRING)));

        if (!itemMap.containsKey(uuid)) {
            itemMap.put(uuid, createItem(itemStack));
        }

        return itemMap.get(uuid);
    }

    public @Nullable Item<? extends Event> getItem(@NotNull Player player) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        return getItem(itemStack);
    }

    @SuppressWarnings("unchecked")
    public static @Nullable Item<? extends Event> createItem(@NotNull ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();

        //非自定义物品
        if (!isCustomized(container)) {
            return null;
        }

        String type = container.get(KEY_TYPE, PersistentDataType.STRING);
        try {
            Class<? extends Item<? extends Event>> clazz = (Class<? extends Item<? extends Event>>) Class.forName(type);
            Item<? extends Event> item = clazz.getDeclaredConstructor().newInstance();
            //设置物品名
            if (meta.hasDisplayName()) {
                item.setName(meta.getDisplayName());
            }
            //设置材料
            item.setMaterial(itemStack.getType());
            //存入数据
            Map<NamespacedKey, String> dataMap = getData(container);
            item.setDataMap(dataMap);
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isCustomized(@NotNull PersistentDataContainer container) {
        return container.has(KEY_UUID, PersistentDataType.STRING) && container.has(KEY_TYPE, PersistentDataType.STRING);
    }

    private static @NotNull Map<NamespacedKey, String> getData(@NotNull PersistentDataContainer container) {
        Map<NamespacedKey, String> buffer = Maps.newHashMap();
        container.getKeys().forEach(key -> buffer.put(key, container.get(key, PersistentDataType.STRING)));
        return buffer;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static Item<?> getItem(String category, String simpleName){
        String className = "cn.fusionfish.core.items.customized." + category + "." + simpleName;
        try {
            Class<Item<?>> clazz = (Class<Item<?>>) Class.forName(className);
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }

    }

    @Contract(pure = true)
    public static @NotNull ItemManager getInstance() {
        return SakuraRPG.getItemManager();
    }
}

package cn.fusionfish.core.items;

import cn.fusionfish.core.SakuraRPG;
import com.google.common.collect.Maps;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public abstract class Item<T extends Event> {

    public static final NamespacedKey KEY_UUID = new NamespacedKey(SakuraRPG.getInstance(), "uuid");
    public static final NamespacedKey KEY_TYPE = new NamespacedKey(SakuraRPG.getInstance(), "type");

    //材料
    private Material material = Material.WOODEN_PICKAXE;
    //物品自定义名称
    private String name;
    //实现类
    private String type = this.getClass().getTypeName();
    private final String uuid = UUID.randomUUID().toString();
    private Map<NamespacedKey, String> dataMap = Maps.newHashMap();

    public final Material getMaterial() {
        return material;
    }

    public final void setMaterial(Material material) {
        this.material = material;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final String getType() {
        return type;
    }

    public final void setType(String type) {
        this.type = type;
    }

    public final String getUuid() {
        return uuid;
    }

    public void setData(NamespacedKey key, String value) {
        dataMap.put(key, value);
    }

    public Map<NamespacedKey, String> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<NamespacedKey, String> dataMap) {
        this.dataMap = dataMap;
    }

    public @NotNull ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();

        PersistentDataContainer container = meta.getPersistentDataContainer();

        dataMap.put(KEY_UUID, uuid);
        dataMap.put(KEY_TYPE, type);

        dataMap.forEach(((k, v) -> container.set(k, PersistentDataType.STRING, v)));

        meta.displayName(Component.text(name));
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public abstract void execute(T event);
}

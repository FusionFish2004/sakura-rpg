package cn.fusionfish.core.items.customized.weapons;

import cn.fusionfish.core.items.Item;
import cn.fusionfish.core.items.customized.Damageable;
import cn.fusionfish.core.items.customized.Upgradable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public abstract class Weapon extends Item<EntityDamageByEntityEvent> implements Damageable, Upgradable {

    private int level;
    private float damage;
    private int maxLevel = 5;

    @Override
    public void setDamage(float damage) {
        this.damage = damage;
        setData(KEY_DAMAGE, String.valueOf(damage));
    }

    @Override
    public float getDamage() {
        this.damage = Float.parseFloat(getDataMap().get(KEY_DAMAGE));
        return this.damage;
    }

    @Override
    public int getUpgradeLevel() {
        this.level = Integer.parseInt(getDataMap().get(KEY_UPGRADE_LEVEL));
        return this.level;
    }

    @Override
    public void setUpgradeLevel(int level) {
        this.level = level;
        setData(KEY_UPGRADE_LEVEL, String.valueOf(level));
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public void setMaxLevel(int level) {
        maxLevel = level;
    }
}

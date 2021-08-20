package cn.fusionfish.core.items.customized;

import cn.fusionfish.core.SakuraRPG;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface Damageable {
    NamespacedKey KEY_DAMAGE = new NamespacedKey(SakuraRPG.getInstance(), "damage");

    void setDamage(float damage);
    float getDamage();
    default void damage(@NotNull LivingEntity entity, @NotNull Player damager) {
        entity.damage(getDamage(), damager);
    }
}

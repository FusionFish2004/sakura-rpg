package cn.fusionfish.core.items.customized;

import cn.fusionfish.core.SakuraRPG;
import org.bukkit.NamespacedKey;

public interface Upgradable {
    int getUpgradeLevel();
    void setUpgradeLevel(int level);

    default void upgrade() {
        int level = getUpgradeLevel();
        level++;
        setUpgradeLevel(level);
    }

    NamespacedKey KEY_UPGRADE_LEVEL = new NamespacedKey(SakuraRPG.getInstance(), "upgrade_level");
}

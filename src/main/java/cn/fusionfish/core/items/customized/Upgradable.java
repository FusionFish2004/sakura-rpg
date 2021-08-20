package cn.fusionfish.core.items.customized;

import cn.fusionfish.core.SakuraRPG;
import org.bukkit.NamespacedKey;

public interface Upgradable {
    int getUpgradeLevel();
    void setUpgradeLevel(int level);
    int getMaxLevel();

    default void upgrade() {
        int level = getUpgradeLevel();
        //物品等级已经达到最大值
        if (level == getMaxLevel()) {
            return;
        }
        level++;
        setUpgradeLevel(level);
    }

    NamespacedKey KEY_UPGRADE_LEVEL = new NamespacedKey(SakuraRPG.getInstance(), "upgrade_level");
}

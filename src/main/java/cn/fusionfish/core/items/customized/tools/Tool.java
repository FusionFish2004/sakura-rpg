package cn.fusionfish.core.items.customized.tools;

import cn.fusionfish.core.items.Item;
import cn.fusionfish.core.items.customized.Upgradable;
import org.bukkit.event.block.BlockBreakEvent;

public abstract class Tool extends Item<BlockBreakEvent> implements Upgradable {

    private int level;

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

}

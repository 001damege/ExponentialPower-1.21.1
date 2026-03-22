package damege.exponentialpower.items;

import net.minecraft.world.item.Item;

public class EnderCell extends Item {
    public EnderCell(Properties p) {
        super(p.stacksTo(64).fireResistant());
    }
}

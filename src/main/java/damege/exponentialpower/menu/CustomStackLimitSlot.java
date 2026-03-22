package damege.exponentialpower.menu;

import damege.exponentialpower.entities.baseclasses.GeneratorBE;
import net.minecraft.world.inventory.Slot;

public class CustomStackLimitSlot extends Slot {
    private final int stackLimit;

    public CustomStackLimitSlot(int stackLimit, GeneratorBE generator, int slot, int x, int y) {
        super(generator, slot, x, y);
        this.stackLimit = stackLimit;
    }

    @Override
    public int getMaxStackSize() {
        return this.stackLimit;
    }
}

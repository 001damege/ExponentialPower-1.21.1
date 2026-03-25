package damege.exponentialpower.container;

import damege.exponentialpower.entities.baseclasses.GeneratorBE;
import net.minecraft.world.inventory.Slot;

public class CustomStackLimitSlot extends Slot {
    private final int stackLimit;

    public CustomStackLimitSlot(int stackLimit, GeneratorBE generator, int index, int x, int y) {
        super(generator, index, x, y);
        this.stackLimit = stackLimit;
    }

    @Override
    public int getMaxStackSize() {
        return this.stackLimit;
    }
}

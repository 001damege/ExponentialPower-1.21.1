package damege.exponentialpower.entities;

import damege.exponentialpower.entities.baseclasses.StorageBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AdvancedEnderStorageBE extends StorageBE {
    public AdvancedEnderStorageBE(BlockPos pos, BlockState state) {
        super(Tier.ADVANCED, pos, state);
    }
}

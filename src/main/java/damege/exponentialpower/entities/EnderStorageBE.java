package damege.exponentialpower.entities;

import damege.exponentialpower.entities.baseclasses.StorageBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnderStorageBE extends StorageBE {
    public EnderStorageBE(BlockPos pos, BlockState state) {
        super(Tier.REGULAR, pos, state);
    }
}

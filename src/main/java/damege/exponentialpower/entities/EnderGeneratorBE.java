package damege.exponentialpower.entities;

import damege.exponentialpower.entities.baseclasses.GeneratorBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EnderGeneratorBE extends GeneratorBE {
    public EnderGeneratorBE(BlockPos pos, BlockState state) {
        super(Tier.REGULAR, pos, state);
    }
}

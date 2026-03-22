package damege.exponentialpower.entities;

import damege.exponentialpower.entities.baseclasses.GeneratorBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class AdvancedEnderGeneratorBE extends GeneratorBE {
    public AdvancedEnderGeneratorBE(BlockPos pos, BlockState state) {
        super(Tier.ADVANCED, pos, state);
    }
}

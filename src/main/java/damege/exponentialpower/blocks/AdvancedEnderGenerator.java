package damege.exponentialpower.blocks;

import damege.exponentialpower.entities.AdvancedEnderGeneratorBE;
import damege.exponentialpower.entities.baseclasses.GeneratorBE;
import damege.exponentialpower.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AdvancedEnderGenerator extends EnderGenerator {
    public AdvancedEnderGenerator(Properties p) {
        super(p);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new AdvancedEnderGeneratorBE(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return type == Registration.ADV_ENDER_GENERATOR_BE.get() ? GeneratorBE::tick : null;
    }
}

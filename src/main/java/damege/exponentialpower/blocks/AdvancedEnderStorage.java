package damege.exponentialpower.blocks;

import damege.exponentialpower.entities.AdvancedEnderStorageBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AdvancedEnderStorage extends EnderStorage {
    public AdvancedEnderStorage(Properties p) {
        super(p);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new AdvancedEnderStorageBE(pos, state);
    }
}

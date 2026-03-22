package damege.exponentialpower.blocks;

import damege.exponentialpower.entities.EnderStorageBE;
import damege.exponentialpower.entities.baseclasses.StorageBE;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnderStorage extends Block implements EntityBlock {
    public EnderStorage(Properties p) {
        super(p
                .mapColor(MapColor.COLOR_BLUE)
                .forceSolidOn()
                .pushReaction(PushReaction.DESTROY)
                .strength(2.5f, 15.0f));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new EnderStorageBE(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return ItemInteractionResult.sidedSuccess(true);
        }

        StorageBE be = (StorageBE) level.getBlockEntity(pos);
        double percent = ((int) (be.energy / be.getMaxEnergy() * 10000.00)) / 100.00;
        player.sendSystemMessage(Component.translatable("screen.exponentialpower.storage_total", be.energy, be.getMaxEnergy(), percent));
        return ItemInteractionResult.SUCCESS;
    }
}

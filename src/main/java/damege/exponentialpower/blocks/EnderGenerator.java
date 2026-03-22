package damege.exponentialpower.blocks;

import damege.exponentialpower.entities.EnderGeneratorBE;
import damege.exponentialpower.entities.baseclasses.GeneratorBE;
import damege.exponentialpower.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnderGenerator extends Block implements EntityBlock {
    public EnderGenerator(Properties p) {
        super(p
                .mapColor(MapColor.COLOR_BLUE)
                .forceSolidOn()
                .pushReaction(PushReaction.DESTROY)
                .strength(2.5f, 15.0f));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new EnderGeneratorBE(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return type == Registration.ENDER_GENERATOR_BE.get() ? GeneratorBE::tick : null;
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof GeneratorBE be) {
                player.openMenu(new SimpleMenuProvider(be, Component.literal("Ender Generator")), pos);
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected void onRemove(BlockState oldState, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoved) {
        if (!oldState.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof Container) {
                Containers.dropContents(level, pos, (Container) blockEntity);
                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(oldState, level, pos, newState, isMoved);
        }
    }
}

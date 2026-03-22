package damege.exponentialpower.entities.baseclasses;

import damege.exponentialpower.Config;
import damege.exponentialpower.setup.Registration;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class StorageBE extends BlockEntity implements BlockEntityTicker<StorageBE> {
    @Getter
    private final Tier tier;

    public double energy = 0;
    public Map<Direction, Boolean> freezeExpand;

    public StorageBE(Tier tier, BlockPos pos, BlockState state) {
        super(tier == Tier.ADVANCED ? Registration.ADV_ENDER_STORAGE_BE.get() : Registration.ENDER_STORAGE_BE.get(), pos, state);
        this.tier = tier;
        freezeExpand = new EnumMap<>(Direction.class);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putDouble("energy", energy);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        energy = tag.getDouble("energy");
    }

    @Override
    public void tick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull StorageBE storageBE) {
        if (energy > 0) {
            handleSendingEnergy();
        }
    }

    private void handleSendingEnergy() {
        if (!Objects.requireNonNull(level).isClientSide()) {
            if (energy <= 0) {
                return;
            }
            for (Direction dir : Direction.values()) {
                if (!freezeExpand.containsKey(dir)) {
                    freezeExpand.put(dir, false);
                }
                if (freezeExpand.get(dir)) {
                    freezeExpand.put(dir, false);
                    continue;
                }

                BlockPos targetPos = getBlockPos().relative(dir);
                BlockEntity entity = level.getBlockEntity(targetPos);
                if (entity != null) {
                    if (entity instanceof StorageBE storage) {
                        double dif = storage.acceptEnergy(energy);
                        energy -= dif;
                        if (dif > 0) {
                            freezeExpand.put(dir, true);
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public double getMaxEnergy() {
        return switch (tier) {
            case REGULAR -> Config.ENDER_STORAGE_MAX_ENERGY.get();
            case ADVANCED -> Config.ADV_ENDER_STORAGE_MAX_ENERGY.get();
            default -> Double.MAX_VALUE;
        };
    }

    public double acceptEnergy(double energyOffered) {
        if (energy >= getMaxEnergy() || energyOffered <= 0) {
            return 0;
        }

        if (energy + energyOffered > getMaxEnergy()) {
            double amountAccepted = getMaxEnergy() - energy;
            energy = getMaxEnergy();
            return amountAccepted;
        }

        if (energy + energyOffered < 0) {
            double amountAccepted = Double.MAX_VALUE - energy;
            energy = Double.MAX_VALUE;
            return amountAccepted;
        }

        energy += energyOffered;
        return energyOffered;
    }

    public enum Tier {
        REGULAR,
        ADVANCED
    }
}

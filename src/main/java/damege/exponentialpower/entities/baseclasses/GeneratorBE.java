package damege.exponentialpower.entities.baseclasses;

import damege.exponentialpower.Config;
import damege.exponentialpower.energy.GeneratorConnection;
import damege.exponentialpower.items.EnderCell;
import damege.exponentialpower.setup.Registration;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class GeneratorBE extends BaseContainerBlockEntity {
    private final ItemStackHandler inv = new ItemStackHandler(1) {
        @Override
        public int getSlotLimit(int slot) {
            return 64;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    @Getter
    private final Tier tier;
    public double curOutput = 0;
    public double energy = 0;

    @Getter
    private final GeneratorConnection connection;

    public GeneratorBE(Tier tier, BlockPos pos, BlockState state) {
        super(tier == Tier.ADVANCED ? Registration.ADV_ENDER_GENERATOR_BE.get() : Registration.ENDER_GENERATOR_BE.get(), pos, state);
        this.tier = tier;
        connection = new GeneratorConnection(this, true, false);
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putDouble("energy", energy);
        tag.putDouble("curEnergy", curOutput);
        tag.put("Items", inv.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        energy = tag.getDouble("energy");
        curOutput = tag.getDouble("curEnergy");
        inv.deserializeNBT(registries, tag.getCompound("Items"));
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T t) {
        if (t instanceof GeneratorBE generator) {
            if (generator.getItem(0).getItem() instanceof EnderCell) {
                generator.energy = generator.curOutput;
                generator.curOutput = generator.calculateEnergy(generator.getItem(0).getCount());
                if (generator.curOutput == 0) {
                    generator.curOutput = -1;
                }
            } else {
                generator.curOutput = 0;
                generator.energy = 0;
            }
            generator.handleSendingEnergy();
        }
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < this.getContainerSize(); i++) {
            this.setItem(i, ItemStack.EMPTY);
        }
    }

    @Override
    protected @NotNull Component getDefaultName() {
        if (tier == Tier.ADVANCED) {
            return Component.translatable(Registration.ADV_ENDER_GENERATOR.get().getDescriptionId());
        } else {
            return Component.translatable(Registration.ENDER_GENERATOR.get().getDescriptionId());
        }
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return NonNullList.of(ItemStack.EMPTY);
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> stacks) {
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInv) {
        return null;
    }

    private void handleSendingEnergy() {
        assert level != null;
        if (!level.isClientSide()) {
            if (energy <= 0) {
                return;
            }
            for (Direction dir : Direction.values()) {
                BlockPos targetPos = getBlockPos().relative(dir);
                BlockEntity entity = level.getBlockEntity(targetPos);
                if (entity == null) {
                    continue;
                }
                if (entity instanceof StorageBE storage) {
                    energy -= storage.acceptEnergy(energy);
                    return;
                }
            }
        }
    }

    @Override
    public int getContainerSize() {
        return inv.getSlots();
    }

    @Override
    public boolean isEmpty() {
        return getItem(0).getCount() == 0;
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return slot <= getContainerSize() && slot >= 0 ? inv.getStackInSlot(slot) : ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        if (slot < getContainerSize() && slot >= 0) {
            ItemStack stack = getItem(slot);
            if (amount > stack.getCount()) {
                setItem(slot, ItemStack.EMPTY);
                return stack;
            } else {
                ItemStack newStack = stack.copy();
                newStack.setCount(amount);
                stack.setCount(stack.getCount() - amount);
                return newStack;
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = getItem(slot);
        setItem(slot, ItemStack.EMPTY);
        return stack;
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        if (slot < getContainerSize() && slot >= 0) {
            inv.setStackInSlot(slot, stack);
        }
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

    public double getBase() {
        return switch (tier) {
            case REGULAR -> Config.ENDER_GENERATOR_BASE.get();
            case ADVANCED -> Config.ADV_ENDER_GENERATOR_BASE.get();
            default -> 2.0;
        };
    }

    public int getMaxStack() {
        return switch (tier) {
            case REGULAR -> Config.ENDER_GENERATOR_MAX_STACK.get();
            case ADVANCED -> Config.ADV_ENDER_GENERATOR_MAX_STACK.get();
            default -> 64;
        };
    }

    public double calculateEnergy(int itemStackCount) {
        return switch (tier) {
            case REGULAR -> longPow(getBase(), (63 * ((itemStackCount) / (double) getMaxStack()))) - 1L;
            case ADVANCED -> Math.pow(getBase(), 1023 * (itemStackCount / (double) getMaxStack())) * (2 - Math.pow(2, -52));
            default -> 1.0;
        };
    }

    private long longPow (double a, double b) {
        if (b == 0) {
            return 1L;
        }
        if (b == 1) {
            return (long) a;
        }
        return (long) Math.pow(a, b);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return stack.getItem() == Registration.ENDER_CELL.get();
    }

    public Component getTitle() {
        if (hasCustomName()) {
            return getCustomName();
        }
        return getDefaultName();
    }

    public enum Tier {
        REGULAR,
        ADVANCED
    }
}

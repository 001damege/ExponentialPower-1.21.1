package damege.exponentialpower.container;

import damege.exponentialpower.entities.baseclasses.GeneratorBE;
import lombok.Getter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class EnderGeneratorMenu extends AbstractContainerMenu {
    @Getter
    private final GeneratorBE blockEntity;

    public EnderGeneratorMenu(int containerId, Inventory playerInv, FriendlyByteBuf data) {
        this(containerId, playerInv, getBlockEntity(playerInv, data));
    }

    public EnderGeneratorMenu(int containerId, Inventory playerInv, GeneratorBE be) {
        super(null, containerId);
        this.blockEntity = be;

        this.addSlot(new CustomStackLimitSlot(be.getMaxStack(), be, 0, 80, 35));
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        for (int x = 0; x < 9; ++x) {
            this.addSlot(new Slot(playerInv, x, 8 + x * 18, 142));
        }
    }

    private static GeneratorBE getBlockEntity(Inventory playerInv, FriendlyByteBuf data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        BlockEntity be = playerInv.player.level().getBlockEntity(data.readBlockPos());
        if (be instanceof GeneratorBE) {
            return (GeneratorBE) be;
        }

        throw new IllegalStateException("BlockEntity is not correct! " + be);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack prev = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack cur = slot.getItem();
            prev = cur.copy();
            if (index == 0) {
                if (cur.getCount() <= 0) {
                    return ItemStack.EMPTY;
                }
                if (!this.moveItemStackTo(cur, 1, 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(cur, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (cur.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (cur.getCount() == prev.getCount()) {
                return ItemStack.EMPTY;
            }
        }
        return prev;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }
}

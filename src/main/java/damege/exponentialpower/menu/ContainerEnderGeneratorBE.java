package damege.exponentialpower.menu;

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

public class ContainerEnderGeneratorBE extends AbstractContainerMenu {
    @Getter
    private final GeneratorBE blockEntity;

    public ContainerEnderGeneratorBE(int containerId, Inventory playerInv, FriendlyByteBuf data) {
        //noinspection resource
        this(containerId, playerInv, playerInv.player.level().getBlockEntity(data.readBlockPos()));
    }

    public ContainerEnderGeneratorBE(int containerId, Inventory playerInv, BlockEntity blockEntity) {
        super(null, containerId);
        this.blockEntity = (GeneratorBE) blockEntity;

        this.addSlot(new CustomStackLimitSlot(((GeneratorBE) blockEntity).getMaxStack(), (GeneratorBE) blockEntity, 0, 80, 35));
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }

        for (int x = 0; x < 9; ++x) {
            this.addSlot(new Slot(playerInv, x, 8 + x * 18, 142));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack cur = slot.getItem();
            stack = cur.copy();
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
                return ItemStack.EMPTY;
            } else {
                slot.setChanged();
            }

            if (cur.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }
        }

        return stack;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}

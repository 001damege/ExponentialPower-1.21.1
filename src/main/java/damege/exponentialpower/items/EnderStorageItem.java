package damege.exponentialpower.items;

import damege.exponentialpower.Config;
import damege.exponentialpower.entities.baseclasses.StorageBE;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EnderStorageItem extends BlockItem {
    private final StorageBE.Tier tier;

    public EnderStorageItem(Block block, StorageBE.Tier tier) {
        super(block, new Properties().stacksTo(1).fireResistant());
        this.tier = tier;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext ctx, @NotNull List<Component> tooltip, @NotNull TooltipFlag type) {
        super.appendHoverText(stack, ctx, tooltip, type);
        double energy = 0;

        CustomData data = stack.get(DataComponents.BLOCK_ENTITY_DATA);
        if (data != null) {
            CompoundTag tag = data.copyTag();
            if (tag.contains("energy")) {
                energy = tag.getDouble("energy");
            }
        }

        tooltip.add(Component.translatable("item.exponentialpower.storage.tooltip.stored"));
        tooltip.add(Component.literal(energy + "/" + getMaxEnergy()));
        double percent = ((int) energy / getMaxEnergy() * 10000.00) / 100.00;
        tooltip.add(Component.literal("(" + percent + "%)"));
    }

    public double getMaxEnergy() {
        return switch (tier) {
            case REGULAR -> Config.ENDER_STORAGE_MAX_ENERGY.get();
            case ADVANCED -> Config.ADV_ENDER_STORAGE_MAX_ENERGY.get();
            default -> Double.MAX_VALUE;
        };
    }
}

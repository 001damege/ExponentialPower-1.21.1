package damege.exponentialpower.data;

import damege.exponentialpower.ExponentialPower;
import damege.exponentialpower.setup.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DataLootTables extends LootTableProvider {
    public DataLootTables(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Collections.emptySet(), List.of(), registries);
    }

    public static class ExponentialBlockTable extends BlockLootSubProvider {
        protected ExponentialBlockTable(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        protected @NotNull Iterable<Block> getKnownBlocks() {
            return List.of(
                    Registration.ENDER_GENERATOR.get(),
                    Registration.ADV_ENDER_GENERATOR.get(),
                    Registration.ENDER_STORAGE.get(),
                    Registration.ADV_ENDER_STORAGE.get());
        }

        @Override
        protected void generate() {
            ExponentialPower.LOGGER.info("Generating Loot Tables!");
            dropSelf(Registration.ENDER_GENERATOR.get());
            dropSelf(Registration.ADV_ENDER_GENERATOR.get());

            add(Registration.ENDER_STORAGE.get(), create("ender_storage", Registration.ENDER_STORAGE.get()));
            add(Registration.ADV_ENDER_STORAGE.get(), create("advanced_ender_storage", Registration.ADV_ENDER_STORAGE.get()));
        }
    }

    protected static LootTable.Builder create(String name, Block block) {
        LootPool.Builder builder = LootPool.lootPool()
                .name(name)
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block)
                        .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY)));
        return LootTable.lootTable().withPool(builder);
    }
}

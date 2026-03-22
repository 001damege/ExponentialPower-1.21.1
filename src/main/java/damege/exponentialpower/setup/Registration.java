package damege.exponentialpower.setup;

import damege.exponentialpower.blocks.AdvancedEnderGenerator;
import damege.exponentialpower.blocks.AdvancedEnderStorage;
import damege.exponentialpower.blocks.EnderGenerator;
import damege.exponentialpower.blocks.EnderStorage;
import damege.exponentialpower.entities.AdvancedEnderGeneratorBE;
import damege.exponentialpower.entities.AdvancedEnderStorageBE;
import damege.exponentialpower.entities.EnderGeneratorBE;
import damege.exponentialpower.entities.EnderStorageBE;
import damege.exponentialpower.entities.baseclasses.StorageBE;
import damege.exponentialpower.items.EnderCell;
import damege.exponentialpower.items.EnderStorageItem;
import damege.exponentialpower.menu.ContainerEnderGeneratorBE;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static damege.exponentialpower.ExponentialPower.MOD_ID;

public final class Registration {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MOD_ID);
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, MOD_ID);
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, MOD_ID);

    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        TYPES.register(eventBus);
        MENUS.register(eventBus);
        TABS.register(eventBus);
    }

    // Blocks
    public static final DeferredBlock<EnderGenerator> ENDER_GENERATOR = BLOCKS.registerBlock("ender_generator", EnderGenerator::new);
    public static final DeferredBlock<AdvancedEnderGenerator> ADV_ENDER_GENERATOR = BLOCKS.registerBlock("advanced_ender_generator", AdvancedEnderGenerator::new);
    public static final DeferredBlock<EnderStorage> ENDER_STORAGE = BLOCKS.registerBlock("ender_storage", EnderStorage::new);
    public static final DeferredBlock<AdvancedEnderStorage> ADV_ENDER_STORAGE = BLOCKS.registerBlock("advanced_ender_storage", AdvancedEnderStorage::new);

    // Items
    public static final DeferredItem<EnderCell> ENDER_CELL = ITEMS.registerItem("ender_cell", EnderCell::new);

    public static final DeferredItem<BlockItem> ENDER_GENERATOR_ITEM = ITEMS.register("ender_generator", () -> new BlockItem(ENDER_GENERATOR.get(), new Item.Properties().fireResistant()));
    public static final DeferredItem<BlockItem> ADV_ENDER_GENERATOR_ITEM = ITEMS.register("advanced_ender_generator", () -> new BlockItem(ADV_ENDER_GENERATOR.get(), new Item.Properties().fireResistant()));
    public static final DeferredItem<EnderStorageItem> ENDER_STORAGE_ITEM = ITEMS.register("ender_storage", () -> new EnderStorageItem(ENDER_STORAGE.get(), StorageBE.Tier.REGULAR));
    public static final DeferredItem<EnderStorageItem> ADV_ENDER_STORAGE_ITEM = ITEMS.register("advanced_ender_storage", () -> new EnderStorageItem(ADV_ENDER_STORAGE.get(), StorageBE.Tier.ADVANCED));

    // Block Entities
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EnderGeneratorBE>> ENDER_GENERATOR_BE = TYPES.register("ender_generator", () -> BlockEntityType.Builder.of(EnderGeneratorBE::new, ENDER_GENERATOR.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AdvancedEnderGeneratorBE>> ADV_ENDER_GENERATOR_BE = TYPES.register("advanced_ender_generator", () -> BlockEntityType.Builder.of(AdvancedEnderGeneratorBE::new, ADV_ENDER_GENERATOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EnderStorageBE>> ENDER_STORAGE_BE = TYPES.register("ender_storage", () -> BlockEntityType.Builder.of(EnderStorageBE::new, ENDER_STORAGE.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AdvancedEnderStorageBE>> ADV_ENDER_STORAGE_BE = TYPES.register("advanced_ender_storage", () -> BlockEntityType.Builder.of(AdvancedEnderStorageBE::new, ADV_ENDER_STORAGE.get()).build(null));

    // MenuTypes
    public static final DeferredHolder<MenuType<?>, MenuType<ContainerEnderGeneratorBE>> CONTAINER_ENDER_GENERATOR = register("ender_generator", ContainerEnderGeneratorBE::new);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_MODE_TAB = TABS.register("main", () -> CreativeModeTab.builder()
            .icon(ENDER_CELL::toStack)
            .title(Component.translatable("itemGroup.exponentialpower"))
            .displayItems((params, output) -> {
                output.accept(ENDER_CELL::get);
                output.accept(ENDER_GENERATOR_ITEM::get);
                output.accept(ADV_ENDER_GENERATOR_ITEM::get);
                output.accept(ENDER_STORAGE_ITEM::get);
                output.accept(ADV_ENDER_STORAGE_ITEM::get);
            })
            .build());

    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> register(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }
}

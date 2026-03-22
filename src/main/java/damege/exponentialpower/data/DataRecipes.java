package damege.exponentialpower.data;

import damege.exponentialpower.ExponentialPower;
import damege.exponentialpower.setup.Registration;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DataRecipes extends RecipeProvider {
    public DataRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        ExponentialPower.LOGGER.info("Registering Recipes!");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.ENDER_CELL.get())
                .pattern("DPD")
                .pattern("nIn")
                .pattern("DPD")
                .define('D', Blocks.DIAMOND_BLOCK)
                .define('n', Items.NETHERITE_INGOT)
                .define('P', Blocks.DARK_PRISMARINE)
                .define('I', Items.ENDER_EYE)
                .group("exponentialpower")
                .unlockedBy("enderEye", InventoryChangeTrigger.TriggerInstance.hasItems(Items.ENDER_EYE))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.ENDER_GENERATOR.get())
                .pattern("nOD")
                .pattern("OcO")
                .pattern("DOn")
                .define('n', Items.NETHERITE_INGOT)
                .define('O', Tags.Items.OBSIDIANS)
                .define('D', Blocks.DIAMOND_BLOCK)
                .define('c', Registration.ENDER_CELL.get())
                .group("exponentialpower")
                .unlockedBy("endercell", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.ENDER_CELL.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.ADV_ENDER_GENERATOR.get())
                .pattern("DGD")
                .pattern("GcG")
                .pattern("DGD")
                .define('D', Blocks.DIAMOND_BLOCK)
                .define('G', Registration.ENDER_GENERATOR.get())
                .define('c', Registration.ENDER_CELL.get())
                .group("exponentialpower")
                .unlockedBy("endergenerator", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.ENDER_GENERATOR.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.ENDER_STORAGE.get())
                .pattern("DOD")
                .pattern("PcP")
                .pattern("DOD")
                .define('D', Blocks.DIAMOND_BLOCK)
                .define('O', Tags.Items.OBSIDIANS)
                .define('P', Blocks.DARK_PRISMARINE)
                .define('c', Registration.ENDER_CELL.get())
                .group("exponentialpower")
                .unlockedBy("endercell", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.ENDER_CELL.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.ADV_ENDER_STORAGE.get())
                .pattern("DSD")
                .pattern("ScS")
                .pattern("DSD")
                .define('D', Blocks.DIAMOND_BLOCK)
                .define('S', Registration.ENDER_STORAGE.get())
                .define('c', Registration.ENDER_CELL.get())
                .group("exponentialpower")
                .unlockedBy("enderstorage", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.ENDER_STORAGE.get()))
                .save(output);
    }
}

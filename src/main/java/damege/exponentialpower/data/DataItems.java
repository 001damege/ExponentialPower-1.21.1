package damege.exponentialpower.data;

import damege.exponentialpower.ExponentialPower;
import damege.exponentialpower.setup.Registration;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static damege.exponentialpower.ExponentialPower.makeId;

public class DataItems extends ItemModelProvider {
    public DataItems(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ExponentialPower.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        singleTexture(Registration.ENDER_CELL.get().toString(), ResourceLocation.parse("item/handheld"), "layer0", makeId("item/ender_cell"));
        withExistingParent(Registration.ENDER_GENERATOR_ITEM.get().toString(), makeId("block/ender_generator"));
        withExistingParent(Registration.ADV_ENDER_GENERATOR_ITEM.get().toString(), makeId("block/advanced_ender_generator"));
        withExistingParent(Registration.ENDER_STORAGE_ITEM.get().toString(), makeId("block/ender_storage"));
        withExistingParent(Registration.ADV_ENDER_STORAGE_ITEM.get().toString(), makeId("block/advanced_ender_storage"));
    }
}

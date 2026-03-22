package damege.exponentialpower.data;

import damege.exponentialpower.ExponentialPower;
import damege.exponentialpower.setup.Registration;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class DataBlockStates extends BlockStateProvider {
    public DataBlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ExponentialPower.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ExponentialPower.LOGGER.info("Registering Block States and Models!");

        simpleBlock(Registration.ENDER_GENERATOR.get());
        simpleBlock(Registration.ADV_ENDER_GENERATOR.get());
        simpleBlock(Registration.ENDER_STORAGE.get());
        simpleBlock(Registration.ADV_ENDER_STORAGE.get());
    }
}

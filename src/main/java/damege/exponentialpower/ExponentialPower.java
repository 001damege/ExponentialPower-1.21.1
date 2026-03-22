package damege.exponentialpower;

import damege.exponentialpower.client.gui.GUIEnderGeneratorBE;
import damege.exponentialpower.data.DataGenerators;
import damege.exponentialpower.setup.Registration;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExponentialPower.MOD_ID)
public class ExponentialPower {
    public static final String MOD_ID = "exponentialpower";
    public static final Logger LOGGER = LogManager.getLogger();

    public ExponentialPower(IEventBus eventBus, ModContainer container) {

        Registration.init(eventBus);

        eventBus.addListener(RegisterCapabilitiesEvent.class, this::registerCapabilities);
        eventBus.addListener(DataGenerators::gatherData);

        container.registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
    }

    public static ResourceLocation makeId(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.ENDER_GENERATOR_BE.get(),
                (val, ctx) -> val.getStorage());
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, Registration.ADV_ENDER_GENERATOR_BE.get(),
                (val, ctx) -> val.getStorage());
    }

    @EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static class ClientEvents {

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(Registration.CONTAINER_ENDER_GENERATOR.get(), GUIEnderGeneratorBE::new);
        }
    }
}

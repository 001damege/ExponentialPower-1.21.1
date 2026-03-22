package damege.exponentialpower.data;

import damege.exponentialpower.ExponentialPower;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DataGenerators {

    public static void gatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var out = gen.getPackOutput();
        var efh = event.getExistingFileHelper();
        var registries = event.getLookupProvider();

        if (event.includeServer()) {
            ExponentialPower.LOGGER.info("Registering Server Providers!");
            gen.addProvider(true, new DataRecipes(out, registries));
            gen.addProvider(true, new DataLootTables(out, registries));
        }

        if (event.includeClient()) {
            ExponentialPower.LOGGER.info("Registering Client Providers!");
            gen.addProvider(true, new DataBlockStates(out, efh));
            gen.addProvider(true, new DataItems(out, efh));
            gen.addProvider(true, new DataLang(out));
        }
    }
}

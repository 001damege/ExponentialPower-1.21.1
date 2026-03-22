package damege.exponentialpower.data;

import damege.exponentialpower.ExponentialPower;
import damege.exponentialpower.setup.Registration;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class DataLang extends LanguageProvider {
    public DataLang(PackOutput output) {
        super(output, ExponentialPower.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addItem(Registration.ENDER_CELL, "Ender Cell");

        addBlock(Registration.ENDER_GENERATOR, "Ender Generator");
        addBlock(Registration.ADV_ENDER_GENERATOR, "Advanced Ender Generator");

        addBlock(Registration.ENDER_STORAGE, "Ender Storage");
        addBlock(Registration.ADV_ENDER_STORAGE, "Advanced Ender Storage");

        add("itemGroup.exponentialpower", "Exponential Power");

        add("screen.exponentialpower.generator_rate", "Current Energy Generation:");
        add("screen.exponentialpower.storage_total", "Current Energy Stored: %s/%s RF (%s%%)");

        add("item.exponentialpower.storage.tooltip.stored", "Current Energy Stored:");
    }
}

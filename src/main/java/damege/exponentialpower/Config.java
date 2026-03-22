package damege.exponentialpower;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    public static final String CATEGORY_ENDER_GENERATOR = "generator";
    public static final String SUBCATEGORY_ENDER_GENERATOR_REGULAR = "regular";
    public static final String SUBCATEGORY_ENDER_GENERATOR_ADVANCED = "advanced";
    public static final String CATEGORY_ENDER_STORAGE = "storage";
    public static final String SUBCATEGORY_ENDER_STORAGE_REGULAR = "regular";
    public static final String SUBCATEGORY_ENDER_STORAGE_ADVANCED = "advanced";

    public static ModConfigSpec.DoubleValue ENDER_GENERATOR_BASE;
    public static ModConfigSpec.IntValue ENDER_GENERATOR_MAX_STACK;
    public static ModConfigSpec.DoubleValue ADV_ENDER_GENERATOR_BASE;
    public static ModConfigSpec.IntValue ADV_ENDER_GENERATOR_MAX_STACK;
    public static ModConfigSpec.LongValue ENDER_STORAGE_MAX_ENERGY;
    public static ModConfigSpec.DoubleValue ADV_ENDER_STORAGE_MAX_ENERGY;
    public static ModConfigSpec SERVER_CONFIG;

    static {
        ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        BUILDER.comment("Ender Generator Settings")
                .push(CATEGORY_ENDER_GENERATOR);

        BUILDER.push(SUBCATEGORY_ENDER_GENERATOR_REGULAR);
        ENDER_GENERATOR_BASE = BUILDER
                .comment("Controls the rate of change of the power output. Remember Base^MaxStack-1 must be less than Long.MAX_VALUE for things to work correctly.")
                .defineInRange("base", 2, 0, Double.MAX_VALUE);
        ENDER_GENERATOR_MAX_STACK = BUILDER
                .comment("Controls the number of Ender Cells required to reach the maximum power output.")
                .defineInRange("maxStack", 64, 1 ,64);
        BUILDER.pop();

        BUILDER.push(SUBCATEGORY_ENDER_GENERATOR_ADVANCED);
        ADV_ENDER_GENERATOR_BASE = BUILDER
                .comment("Controls the rate of change of the power output. Remember Base^MaxStack-1 must be less than Long.MAX_VALUE for things to work correctly.")
                .defineInRange("base", 2, 0, Double.MAX_VALUE);
        ADV_ENDER_GENERATOR_MAX_STACK = BUILDER
                .comment("Controls the number of Ender Cells required to reach the maximum power output.")
                .defineInRange("maxStack", 64, 1 ,64);
        BUILDER.pop(2);

        BUILDER.comment("Ender Storage Settigns")
                .push(CATEGORY_ENDER_STORAGE);

        BUILDER.push(SUBCATEGORY_ENDER_STORAGE_REGULAR);
        ENDER_STORAGE_MAX_ENERGY = BUILDER
                .comment("The maximum amount of power that can be stored in a single Ender Storage block.")
                .defineInRange("maxEnergy", Long.MAX_VALUE, 0, Long.MAX_VALUE);
        BUILDER.pop()
                .push(SUBCATEGORY_ENDER_STORAGE_ADVANCED);
        ADV_ENDER_STORAGE_MAX_ENERGY = BUILDER
                .comment("The maximum amount of power that can be stored in a single Advanced Ender Storage block.")
                .defineInRange("maxEnergy", Double.MAX_VALUE, 0, Double.MAX_VALUE);
        BUILDER.pop(2);

        SERVER_CONFIG = BUILDER.build();
    }
}

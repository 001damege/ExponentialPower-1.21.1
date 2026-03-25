package damege.exponentialpower.container;

import damege.exponentialpower.ExponentialPower;
import damege.exponentialpower.entities.baseclasses.GeneratorBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class EnderGeneratorScreen extends AbstractContainerScreen<EnderGeneratorMenu> {
    private final GeneratorBE blockEntity;
    private final ResourceLocation GUI = ExponentialPower.makeId("textures/gui/ender_generator_gui.png");

    public EnderGeneratorScreen(EnderGeneratorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.blockEntity = menu.getBlockEntity();

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics draw, float partialTick, int mouseX, int mouseY) {
        renderMenuBackground(draw);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        draw.blit(GUI, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics draw, int mouseX, int mouseY, float partialTick) {
        super.render(draw, mouseX, mouseY, partialTick);
        renderTooltip(draw, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics draw, int mouseX, int mouseY) {
        super.renderLabels(draw, mouseX, mouseY);
        draw.drawString(Minecraft.getInstance().font, Component.translatable("screen.exponentialpower.generator_rate"), 10, 53, 0xffffff);
        draw.drawString(Minecraft.getInstance().font, blockEntity.energy + " RF/t", 10, 63, 0xffffff);
    }
}

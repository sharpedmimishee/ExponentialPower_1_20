package io.github.mosadie.exponentialpower.client.gui;

import io.github.mosadie.exponentialpower.ExponentialPower;
import io.github.mosadie.exponentialpower.container.ContainerEnderGeneratorBE;
import io.github.mosadie.exponentialpower.entities.BaseClasses.GeneratorBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class GUIEnderGeneratorBE extends AbstractContainerScreen<ContainerEnderGeneratorBE> {
	private final GeneratorBE be;

    private final ResourceLocation GUI = new ResourceLocation(ExponentialPower.MODID, "textures/gui/containerendergeneratorbe.png");

	public GUIEnderGeneratorBE(ContainerEnderGeneratorBE container, Inventory playerInv, Component title) {
        super(container, playerInv, title);

        be = container.getBlockEntity();

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTicks, int x, int y) {
        renderBackground(guiGraphics);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(GUI, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int p_97809_, int p_97810_) {
        super.renderLabels(guiGraphics, p_97809_, p_97810_);
        guiGraphics.drawString(Minecraft.getInstance().font, Component.translatable("screen.exponentialpower.generator_rate"), 10, 53, 0xffffff);
        guiGraphics.drawString(Minecraft.getInstance().font, be.energy + " RF/t", 10, 63, 0xffffff);
    }
}

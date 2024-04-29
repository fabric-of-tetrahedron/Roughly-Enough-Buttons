package pama1234.reb;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TexturedButtonWidget extends ImageButton {
    public final boolean renderBackground;

    public TexturedButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset, ResourceLocation texture, int textureWidth, int textureHeight, Button.OnPress pressAction, Component text) {
        this(x, y, width, height, u, v, hoveredVOffset, texture, textureWidth, textureHeight, pressAction, text, true);
    }

    public TexturedButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset, ResourceLocation texture, int textureWidth, int textureHeight, Button.OnPress pressAction, Component text, boolean renderBackground) {
        super(x, y, width, height, u, v, hoveredVOffset, texture, textureWidth, textureHeight, pressAction, text);
        this.renderBackground = renderBackground;
    }

    @Override
    public void renderWidget(GuiGraphics matrices, int mouseX, int mouseY, float delta) {
        if (this.renderBackground) {
            int i = 1;
            if (!this.active) {
                i = 0;
            } else if (this.isHovered) {
                i = 2;
            }

            matrices.blit(WIDGETS_LOCATION, this.getX(), this.getY(), 0, 46 + i * 20, this.getWidth() / 2, this.getHeight());
            matrices.blit(WIDGETS_LOCATION, this.getX() + this.getWidth() / 2, this.getY(), 200 - this.width / 2, 46 + i * 20, this.getWidth() / 2, this.getHeight());
        }

        super.renderWidget(matrices, mouseX, mouseY, delta);
    }
}

package pama1234.reb.compat;

import com.seibel.distanthorizons.core.config.Config;
import loaderCommon.fabric.com.seibel.distanthorizons.common.wrappers.gui.GetConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import pama1234.reb.TexturedButtonWidget;

import static pama1234.reb.RoughlyEnoughButtonsMod.createButton;

public class DistantHorizons {
    public static final ResourceLocation DH_ICON_TEXTURE = new ResourceLocation("distanthorizons", "textures/gui/button.png");

    public static TexturedButtonWidget addButton(Screen screenIn) {
        if (Config.Client.optionsButton.get()) {
            return (createButton(DH_ICON_TEXTURE, (buttonWidget) -> Minecraft.getInstance().setScreen(GetConfigScreen.getScreen(screenIn))));
        }
        return null;
    }
}

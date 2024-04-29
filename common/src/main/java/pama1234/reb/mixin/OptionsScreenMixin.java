package pama1234.reb.mixin;

import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pama1234.reb.RoughlyEnoughButtonsMod;

import java.util.List;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen {

    protected OptionsScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "init", at = @At(value = "RETURN"))
    public void afterInit(CallbackInfo callbackInfo) {
        List<AbstractWidget> buttons = Screens.getButtons(this);

        eachButton(buttons);
    }

    private void eachButton(List<AbstractWidget> buttons) {
        for (var button : buttons) {
            if (button.getMessage().equals(Component.translatable("options.skinCustomisation"))) {
                RoughlyEnoughButtonsMod.buttonPos.put(OptionsScreen.class, new Vector2i(button.getX(), button.getY() - 24));
            }
        }
    }
}

package pama1234.reb.mixin;

import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pama1234.reb.RoughlyEnoughButtonsMod;
import pama1234.reb.compat.DistantHorizons;
import pama1234.reb.compat.ModernUI;
import pama1234.reb.compat.NeoKeyWizard;

import java.util.List;

import static pama1234.reb.RoughlyEnoughButtonsMod.isModLoaded;

@Mixin(PauseScreen.class)
public class PauseScreenMixin extends Screen {

    protected PauseScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "init", at = @At(value = "RETURN"))
    public void afterInit(CallbackInfo callbackInfo) {
        List<AbstractWidget> buttons = Screens.getButtons(this);

        eachButton(buttons);
    }

    private void eachButton(List<AbstractWidget> buttons) {
//        boolean flag = false;
//        if (FabricLoader.getInstance().isModLoaded("replaymod")) {
//            flag = ReplayMod.testHaveReplayModButton(buttons);
//        }

        for (var button : buttons) {
            if (button.getMessage().equals(Component.translatable("menu.returnToGame"))) {
                RoughlyEnoughButtonsMod.buttonPos.put(PauseScreen.class, new Vector2i(button.getX(), button.getY() - 24));
//                RoughlyEnoughButtonsMod.buttonPos.put(PauseScreen.class, new Vector2i(button.getX(), button.getY() + (flag ? -24 : 24)));
            }
        }
    }

    @Inject(method = "init", at = @At(value = "RETURN"))
    public void afterCreateNormalMenuOptions(CallbackInfo callbackInfo) {
        if (isModLoaded("distanthorizons")) {
            var b = DistantHorizons.addButton(this);
            if (b != null) addRenderableWidget(b);
        }
        if (isModLoaded("nkw")) {
            addRenderableWidget(NeoKeyWizard.addButton(this));
        }
        if (isModLoaded("modernui")) {
            addRenderableWidget(ModernUI.addButton(this));
        }
    }
}

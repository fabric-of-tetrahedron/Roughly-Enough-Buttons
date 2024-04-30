package pama1234.reb.mixin;

import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pama1234.reb.RoughlyEnoughButtonsMod;
import pama1234.reb.compat.DistantHorizons;
import pama1234.reb.compat.ModernUI;
import pama1234.reb.compat.NeoKeyWizard;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Component component) {
        super(component);
    }

    @Inject(method = "realmsNotificationsEnabled", at = @At("RETURN"), cancellable = true)
    public void realmsNotificationsEnabled(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Redirect(method = "createNormalMenuOptions", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button$Builder;bounds(IIII)Lnet/minecraft/client/gui/components/Button$Builder;"))
    public Button.Builder returnEmptyRealmsButton(Button.Builder instance, int i, int j, int k, int l) {
        // TODO waste
        if (instance.build().getMessage().equals(Component.translatable("menu.online"))) {
            RoughlyEnoughButtonsMod.buttonPos.put(TitleScreen.class, new Vector2i(i, j + 12));
            return instance.bounds(i, j - 24, 2, 0);
        } else return instance.bounds(i, j, k, l);
    }

    @Redirect(method = "added", at = @At(value = "INVOKE", target = "Lcom/mojang/realmsclient/gui/screens/RealmsNotificationsScreen;added()V"))
    public void doNothing(RealmsNotificationsScreen instance) {
    }

    @Inject(method = "createNormalMenuOptions", at = @At("RETURN"))
    public void afterCreateNormalMenuOptions(int i, int j, CallbackInfo callbackInfo) {
        if (FabricLoader.getInstance().isModLoaded("distanthorizons")) {
//            if (Config.Client.optionsButton.get()) {
//                addRenderableWidget(createButton(DH_ICON_TEXTURE, (buttonWidget) -> minecraft.setScreen(GetConfigScreen.getScreen(this))));
//            }
            var b = DistantHorizons.addButton(this);
            if (b != null) addRenderableWidget(b);
        }
        if (FabricLoader.getInstance().isModLoaded("nkw")) {
            addRenderableWidget(NeoKeyWizard.addButton(this));
        }
        // https://github.com/BloCamLimb/ModernUI-MC/blob/master/fabric/src/main/java/icyllis/modernui/mc/fabric/MuiModMenuApi.java
        if (FabricLoader.getInstance().isModLoaded("modernui")) {
            addRenderableWidget(ModernUI.addButton(this));
        }
    }

}

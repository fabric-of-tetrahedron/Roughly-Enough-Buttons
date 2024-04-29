package pama1234.reb.mixin;

import com.mojang.realmsclient.gui.screens.RealmsNotificationsScreen;
import com.seibel.distanthorizons.core.config.Config;
import icyllis.modernui.mc.ScreenCallback;
import icyllis.modernui.mc.fabric.CenterFragment2;
import icyllis.modernui.mc.fabric.MuiFabricApi;
import icyllis.modernui.util.DataSet;
import loaderCommon.fabric.com.seibel.distanthorizons.common.wrappers.gui.GetConfigScreen;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pama1234.nkw.gui.KeyWizardScreen;
import pama1234.reb.RoughlyEnoughButtonsMod;
import pama1234.reb.TexturedButtonWidget;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    private static final ResourceLocation DH_ICON_TEXTURE = new ResourceLocation("distanthorizons", "textures/gui/button.png");
    private static final ResourceLocation NKW_ICON_TEXTURE = new ResourceLocation("nkw", "textures/gui/screen_toggle_widgets.png");
    private static final ResourceLocation MODERNUI_ICON_TEXTURE = new ResourceLocation(RoughlyEnoughButtonsMod.MOD_ID, "textures/modernui_logo.png");

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
            if (Config.Client.optionsButton.get()) {
                addRenderableWidget(createButton(DH_ICON_TEXTURE, (buttonWidget) -> minecraft.setScreen(GetConfigScreen.getScreen(this))));
            }
        }
        if (FabricLoader.getInstance().isModLoaded("nkw")) {
            if (Config.Client.optionsButton.get()) {
                addRenderableWidget(createButton(NKW_ICON_TEXTURE, 40, 40, (buttonWidget) -> minecraft.setScreen(new KeyWizardScreen(this))));
            }
        }
        // https://github.com/BloCamLimb/ModernUI-MC/blob/master/fabric/src/main/java/icyllis/modernui/mc/fabric/MuiModMenuApi.java
        if (FabricLoader.getInstance().isModLoaded("modernui")) {
            if (Config.Client.optionsButton.get()) {
                addRenderableWidget(createButton(MODERNUI_ICON_TEXTURE, (buttonWidget) -> {
                    DataSet args = new DataSet();
                    args.putBoolean("navigateToPreferences", true);
                    CenterFragment2 fragment = new CenterFragment2();
                    fragment.setArguments(args);
                    var s = MuiFabricApi.get().createScreen(fragment, (ScreenCallback) null, this);
                    minecraft.setScreen(s);
                }));
            }
        }
    }

    public @NotNull TexturedButtonWidget createButton(ResourceLocation texture, int w, int h, boolean renderBackground, Button.OnPress pressAction) {
        return new TexturedButtonWidget(
                4, 4,
                20, 20,
                0, 0,
                // Some textuary stuff
                20, texture, w, h,
                // Create the button and tell it where to go
                pressAction,
                // Add a title to the utton
                Component.translatable("distanthorizons.title"), renderBackground);
    }

    public @NotNull TexturedButtonWidget createButton(ResourceLocation texture, int w, int h, Button.OnPress pressAction) {
        return createButton(texture, w, h, true, pressAction);
    }

    public @NotNull TexturedButtonWidget createButton(ResourceLocation texture, Button.OnPress pressAction) {
        return createButton(texture, 20, 40, true, pressAction);
    }

}

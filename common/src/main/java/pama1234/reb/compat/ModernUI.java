package pama1234.reb.compat;

import icyllis.modernui.mc.ScreenCallback;
import icyllis.modernui.mc.fabric.CenterFragment2;
import icyllis.modernui.mc.fabric.MuiFabricApi;
import icyllis.modernui.util.DataSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import pama1234.reb.RoughlyEnoughButtonsMod;
import pama1234.reb.TexturedButtonWidget;

import static pama1234.reb.RoughlyEnoughButtonsMod.createButton;

public class ModernUI {
    public static final ResourceLocation MODERNUI_ICON_TEXTURE = new ResourceLocation(RoughlyEnoughButtonsMod.MOD_ID, "textures/modernui_logo.png");

    public static TexturedButtonWidget addButton(Screen screenIn) {
        return createButton(MODERNUI_ICON_TEXTURE, (buttonWidget) -> {
            DataSet args = new DataSet();
            args.putBoolean("navigateToPreferences", true);
            CenterFragment2 fragment = new CenterFragment2();
            fragment.setArguments(args);
            var s = MuiFabricApi.get().createScreen(fragment, (ScreenCallback) null, screenIn);
            Minecraft.getInstance().setScreen(s);
        });
    }
}

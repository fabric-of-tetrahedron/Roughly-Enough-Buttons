package pama1234.reb.compat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import pama1234.nkw.gui.KeyWizardScreen;
import pama1234.reb.TexturedButtonWidget;

import static pama1234.reb.RoughlyEnoughButtonsMod.createButton;

public class NeoKeyWizard {
    public static final ResourceLocation NKW_ICON_TEXTURE = new ResourceLocation("nkw", "textures/gui/screen_toggle_widgets.png");

    public static TexturedButtonWidget addButton(Screen screenIn) {
        return createButton(NKW_ICON_TEXTURE, 40, 40, (buttonWidget) -> {
            KeyWizardScreen screen = new KeyWizardScreen(screenIn);
//            Screen screen = null;
//            try {
//                screen = (Screen) NeoKeyWizard.class.getClassLoader()
//                        .loadClass("pama1234.nkw.gui.KeyWizardScreen")
//                        .getConstructor(Screen.class).newInstance(screenIn);
//            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
//                     IllegalAccessException | NoSuchMethodException e) {
//                throw new RuntimeException(e);
//            }
            Minecraft.getInstance().setScreen(screen);
        });
    }
}

package pama1234.reb;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

import java.util.HashMap;

public class RoughlyEnoughButtonsMod {
    public static final String MOD_ID = "reb";

    public static final HashMap<Class<?>, Vector2i> buttonPos = new HashMap<>();

    public static void init() {
    }

    public static @NotNull TexturedButtonWidget createButton(ResourceLocation texture, int w, int h, boolean renderBackground, Button.OnPress pressAction) {
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

    public static @NotNull TexturedButtonWidget createButton(ResourceLocation texture, int w, int h, Button.OnPress pressAction) {
        return createButton(texture, w, h, true, pressAction);
    }

    public static @NotNull TexturedButtonWidget createButton(ResourceLocation texture, Button.OnPress pressAction) {
        return createButton(texture, 20, 40, true, pressAction);
    }
}

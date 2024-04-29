package pama1234.reb.mixin;

import com.replaymod.replay.handler.GuiHandler.InjectedButton;
import com.simibubi.create.infrastructure.gui.OpenCreateMenuButton;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.joml.Vector2i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pama1234.reb.RoughlyEnoughButtonsMod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mixin(value = Screen.class, priority = 2000)
public abstract class ScreenMixin {
    private static final ArrayList<String> keys = new ArrayList<>();
    private static final ArrayList<Class<? extends Screen>> classes = new ArrayList<>();

    private static Vector2i pos = new Vector2i();
    private static boolean onResize;

    private static final boolean debug = true;

    static {
        keys.add("narrator.button.language");
        keys.add("narrator.button.accessibility");
        keys.add("distanthorizons.title");
        keys.add("midnightlib.overview.title");


        classes.add(OptionsScreen.class);
        classes.add(TitleScreen.class);
        classes.add(PauseScreen.class);
    }

    private static void tryMoveToPos(Button button) {
        if (debug) {
            Tooltip tooltip = button.getTooltip();
            if (tooltip != null) System.out.println(tooltip.toCharSequence(Minecraft.getInstance()));
        }
        if (button instanceof OpenCreateMenuButton b) {
            moveToPos(b, pos.x, pos.y);
            return;
        }
        if (button instanceof InjectedButton b) {
            moveToPos(b, pos.x, pos.y);
            return;
        }
        Component message = button.getMessage();
        if (debug) System.out.println("Component message=" + message.getClass());
        if (message instanceof MutableComponent mutableComponent) {
            ComponentContents contents = mutableComponent.getContents();
            if (debug) System.out.println("ComponentContents contents=" + contents.getClass());
            if (contents instanceof TranslatableContents translatableContents) {
                String key = translatableContents.getKey();
                if (debug) System.out.println("String key=" + key);
                if (keys.contains(key)) {
                    if (debug) System.out.println("button w=" + button.getWidth() + " h=" + button.getHeight());
                    if (button.getHeight() == 20 && button.getWidth() == 20) {
                        moveToPos(button);
                    }
                }
            }
        }
    }

    private static void moveToPos(Button button) {
        moveToPos(button, pos.x, pos.y);
    }

    private static void moveToPos(Button button, int posX, int posY) {
        button.setPosition(posX, posY);
        pos.x += button.getWidth() + 4;
    }

    @Inject(method = "init", at = @At("HEAD"))
    public void atInit(CallbackInfo callbackInfo) {
    }

    @Inject(method = "init", at = @At("RETURN"))
    public void afterInit(CallbackInfo callbackInfo) {
        if (debug) System.out.println("Screen.class=" + this.getClass());
        List<AbstractWidget> buttons = Screens.getButtons((Screen) (Object) this);
        if (buttons.size() == 0) {
            onResize = true;
            return;
        }

        afterInitWithButton(buttons, this);
    }

    @Inject(method = "resize", at = @At("RETURN"))
    public void afterRebuildWidgets(Minecraft arg, int i, int j, CallbackInfo callbackInfo) {
        if (onResize) onResize = false;
        else return;

        if (debug) System.out.println("Screen.class=" + this.getClass());
        List<AbstractWidget> buttons = Screens.getButtons((Screen) (Object) this);

        afterInitWithButton(buttons, this);
    }

    private static void afterInitWithButton(List<AbstractWidget> buttons, Object screen) {
        if (debug) System.out.println("afterInitWithButton buttons=" + Arrays.toString(buttons.toArray()));


        if (!updateStartPos(screen)) return;

        for (AbstractWidget i : buttons) {
            if (i instanceof Button button)
                tryMoveToPos(button);
        }
    }

    private static boolean updateStartPos(Object screen) {
        for (Class i : classes) {
            if (i.isInstance(screen)) {
                Vector2i v = RoughlyEnoughButtonsMod.buttonPos.get(i);
                if (v == null) continue;
                pos.set(v);
                return true;
            }
        }
        return false;
    }
}

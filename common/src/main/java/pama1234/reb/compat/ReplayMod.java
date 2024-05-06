package pama1234.reb.compat;

import com.replaymod.replay.handler.GuiHandler.InjectedButton;
import net.minecraft.client.gui.components.AbstractWidget;

import java.util.List;

public class ReplayMod {
    public static boolean testHaveReplayModButton(List<AbstractWidget> buttons) {
        for (var button : buttons) {
            if (button instanceof InjectedButton) return true;
        }
        return false;
    }

}

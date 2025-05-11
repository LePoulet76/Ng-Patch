/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 */
package net.ilexiconn.nationsgui.forge.client.gui.main;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.MenuButtonGUI;

@SideOnly(value=Side.CLIENT)
public enum ButtonPositionHandler {
    NONE{

        @Override
        public void handleTransform(MenuButtonGUI button, State state) {
        }
    }
    ,
    LEFT{

        @Override
        public void handleTransform(MenuButtonGUI button, State state) {
            switch (state) {
                case NORMAL: {
                    this.setTargetPosition(button, button.positionXOrig);
                    break;
                }
                case HOVER: {
                    this.setTargetPosition(button, button.positionXOrig + 10.0f);
                }
            }
        }
    }
    ,
    RIGHT{

        @Override
        public void handleTransform(MenuButtonGUI button, State state) {
            switch (state) {
                case NORMAL: {
                    this.setTargetPosition(button, button.positionXOrig);
                    break;
                }
                case HOVER: {
                    this.setTargetPosition(button, button.positionXOrig - 10.0f);
                }
            }
        }
    };

    private static Map<MenuButtonGUI, Float> positionMap;

    protected void setTargetPosition(MenuButtonGUI button, float targetPosition) {
        positionMap.put(button, Float.valueOf(targetPosition));
    }

    public static void updatePositions() {
        ArrayList<MenuButtonGUI> remove = new ArrayList<MenuButtonGUI>();
        for (Map.Entry<MenuButtonGUI, Float> entry : positionMap.entrySet()) {
            entry.getKey().positionX = ButtonPositionHandler.interpolate(entry.getKey().positionX, entry.getValue().floatValue(), 0.35f);
            if (entry.getKey().positionX != entry.getValue().floatValue()) continue;
            remove.add(entry.getKey());
        }
        for (MenuButtonGUI button : remove) {
            positionMap.remove((Object)button);
        }
    }

    private static float interpolate(float prev, float current, float partialTicks) {
        return prev + partialTicks * (current - prev);
    }

    public abstract void handleTransform(MenuButtonGUI var1, State var2);

    static {
        positionMap = new HashMap<MenuButtonGUI, Float>();
    }

    public static enum State {
        NORMAL,
        HOVER;

    }
}


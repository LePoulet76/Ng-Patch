package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.util.ArrayList;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.data.Objective;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;

public class ObjectiveOverride extends Gui implements ElementOverride
{
    private boolean active;

    public ElementType getType()
    {
        return ElementType.HOTBAR;
    }

    public ElementType[] getSubTypes()
    {
        return new ElementType[0];
    }

    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks)
    {
        if (ClientData.versusOverlayData.isEmpty() || ((ArrayList)ClientData.versusOverlayData.get("playersInMatch")).contains(Minecraft.getMinecraft().thePlayer.username))
        {
            if (System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB.longValue() > 50L && ClientData.currentAssault.isEmpty() && ClientData.currentWarzone.isEmpty() && ClientProxy.serverType.equals("ng") && ClientProxy.clientConfig.displayObjectives && !Minecraft.getMinecraft().gameSettings.showDebugInfo && this.active)
            {
                int offsetY = 30;

                if (!ClientData.objectives.isEmpty())
                {
                    for (Iterator var5 = ClientData.objectives.iterator(); var5.hasNext(); offsetY += 34)
                    {
                        Objective objective = (Objective)var5.next();
                        ModernGui.bindTextureOverlayMain();
                        ModernGui.drawScaledCustomSizeModalRect(4.0F, (float)offsetY, (float)(326 * GenericOverride.GUI_SCALE), (float)(580 * GenericOverride.GUI_SCALE), 224 * GenericOverride.GUI_SCALE, 58 * GenericOverride.GUI_SCALE, 112, 29, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
                        ModernGui.drawScaledStringCustomFont(objective.getTitle().length() < 21 ? objective.getTitle().toUpperCase() : objective.getTitle().toUpperCase().substring(0, 20) + "..", 35.0F, (float)(offsetY + 6), 16777215, 0.5F, "left", false, "minecraftDungeons", 21);
                        ModernGui.drawScaledStringCustomFont(objective.getRewardName().length() > 22 ? objective.getRewardName().substring(0, 22) + ".." : objective.getRewardName(), 35.0F, (float)(offsetY + 15), 7239406, 0.5F, "left", false, "georamaSemiBold", 30);
                        ClientEventHandler.STYLE.bindTexture("overlay_icons");
                        ModernGui.drawScaledCustomSizeModalRect(5.7F, (float)offsetY + 3.5F, (float)((objective.getRewardName().isEmpty() ? 0 : 1260) * GenericOverride.GUI_SCALE), (float)(504 * GenericOverride.GUI_SCALE), 126 * GenericOverride.GUI_SCALE, 123 * GenericOverride.GUI_SCALE, 23, 22, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
                    }
                }
            }
        }
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    private void drawSmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, 0.0F);
        GL11.glScalef(0.95F, 0.95F, 0.95F);
        this.drawString(fontRenderer, string, 0, 0, 16777215);
        GL11.glPopMatrix();
    }
}

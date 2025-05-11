/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

@SideOnly(value=Side.CLIENT)
public class AzimutOverride
extends Gui
implements ElementOverride {
    private HashMap<Integer, Integer> azimutOffsets = new HashMap();

    @Override
    public RenderGameOverlayEvent.ElementType getType() {
        return RenderGameOverlayEvent.ElementType.HOTBAR;
    }

    @Override
    public RenderGameOverlayEvent.ElementType[] getSubTypes() {
        return null;
    }

    @Override
    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
        if (client.field_71451_h instanceof EntityPlayer) {
            if (!ClientProxy.clientConfig.enableAzimut) {
                return;
            }
            if (!GenericOverride.displaysCountryEntry() || ClientProxy.clientConfig.azimutBottom) {
                int number;
                int i;
                float offsetXOriginal;
                int offsetXMiddle = resolution.func_78326_a() / 2;
                EntityPlayer player = (EntityPlayer)client.field_71451_h;
                int orientation = (int)((player.field_70177_z % 360.0f + 360.0f) % 360.0f);
                int azimutOffsetY = ClientProxy.clientConfig.azimutBottom || !ClientData.currentAssault.isEmpty() || !ClientData.currentWarzone.isEmpty() ? resolution.func_78328_b() - 50 : 12;
                ModernGui.bindTextureOverlayMain();
                ModernGui.drawScaledCustomSizeModalRect(offsetXMiddle - 110, azimutOffsetY, 226 * GenericOverride.GUI_SCALE, 28 * GenericOverride.GUI_SCALE, 560 * GenericOverride.GUI_SCALE, 22 * GenericOverride.GUI_SCALE, 220, 9, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(orientation + "", offsetXMiddle, azimutOffsetY - 11, 0xFFFFFF, 0.4f, "center", false, "minecraftDungeons", 30);
                int roundedOrientationInf = orientation / 10 * 10;
                int roundedOrientationSup = (int)Math.ceil((double)(orientation + 1) / 10.0) * 10;
                float offsetX = offsetXOriginal = (7.0f + GenericOverride.georamaBold20.getStringWidth(roundedOrientationInf + "") / 2.0f / 2.0f) * (float)(orientation - roundedOrientationInf) / 10.0f;
                for (i = roundedOrientationInf; i >= roundedOrientationInf - 80; i -= 10) {
                    number = (i % 360 + 360) % 360;
                    ModernGui.drawScaledStringCustomFont(number + "", (float)offsetXMiddle - offsetX, azimutOffsetY + 3, 0xBABADA, 0.5f, "center", false, "georamaBold", 20);
                    offsetX += (number != 0 ? GenericOverride.georamaBold20.getStringWidth(number + "") / 2.0f / 2.0f : 3.0f) + 7.0f;
                }
                offsetX = GenericOverride.georamaBold20.getStringWidth(roundedOrientationInf + "") / 2.0f / 2.0f + 7.0f - (7.0f + GenericOverride.georamaBold20.getStringWidth(roundedOrientationSup + "") / 2.0f / 2.0f) * ((float)(orientation - roundedOrientationInf) / 10.0f);
                for (i = roundedOrientationSup; i <= roundedOrientationSup + 80; i += 10) {
                    number = (i % 360 + 360) % 360;
                    ModernGui.drawScaledStringCustomFont(number + "", (float)offsetXMiddle + offsetX, azimutOffsetY + 3, 0xBABADA, 0.5f, "center", false, "georamaBold", 20);
                    offsetX += GenericOverride.georamaBold20.getStringWidth(number + "") / 2.0f / 2.0f + 7.0f;
                }
            }
        }
    }
}


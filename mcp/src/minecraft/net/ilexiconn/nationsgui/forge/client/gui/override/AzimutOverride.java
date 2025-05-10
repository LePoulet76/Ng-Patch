package net.ilexiconn.nationsgui.forge.client.gui.override;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

@SideOnly(Side.CLIENT)
public class AzimutOverride extends Gui implements ElementOverride
{
    private HashMap<Integer, Integer> azimutOffsets = new HashMap();

    public ElementType getType()
    {
        return ElementType.HOTBAR;
    }

    public ElementType[] getSubTypes()
    {
        return null;
    }

    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks)
    {
        if (client.renderViewEntity instanceof EntityPlayer)
        {
            if (!ClientProxy.clientConfig.enableAzimut)
            {
                return;
            }

            if (!GenericOverride.displaysCountryEntry() || ClientProxy.clientConfig.azimutBottom)
            {
                int offsetXMiddle = resolution.getScaledWidth() / 2;
                EntityPlayer player = (EntityPlayer)client.renderViewEntity;
                int orientation = (int)((player.rotationYaw % 360.0F + 360.0F) % 360.0F);
                int azimutOffsetY = !ClientProxy.clientConfig.azimutBottom && ClientData.currentAssault.isEmpty() && ClientData.currentWarzone.isEmpty() ? 12 : resolution.getScaledHeight() - 50;
                ModernGui.bindTextureOverlayMain();
                ModernGui.drawScaledCustomSizeModalRect((float)(offsetXMiddle - 110), (float)azimutOffsetY, (float)(226 * GenericOverride.GUI_SCALE), (float)(28 * GenericOverride.GUI_SCALE), 560 * GenericOverride.GUI_SCALE, 22 * GenericOverride.GUI_SCALE, 220, 9, (float)(1920 * GenericOverride.GUI_SCALE), (float)(1033 * GenericOverride.GUI_SCALE), true);
                ModernGui.drawScaledStringCustomFont(orientation + "", (float)offsetXMiddle, (float)(azimutOffsetY - 11), 16777215, 0.4F, "center", false, "minecraftDungeons", 30);
                int roundedOrientationInf = orientation / 10 * 10;
                int roundedOrientationSup = (int)Math.ceil((double)(orientation + 1) / 10.0D) * 10;
                float offsetXOriginal = (7.0F + GenericOverride.georamaBold20.getStringWidth(roundedOrientationInf + "") / 2.0F / 2.0F) * (float)(orientation - roundedOrientationInf) / 10.0F;
                float offsetX = offsetXOriginal;
                int i;
                int number;

                for (i = roundedOrientationInf; i >= roundedOrientationInf - 80; i -= 10)
                {
                    number = (i % 360 + 360) % 360;
                    ModernGui.drawScaledStringCustomFont(number + "", (float)offsetXMiddle - offsetX, (float)(azimutOffsetY + 3), 12237530, 0.5F, "center", false, "georamaBold", 20);
                    offsetX += (number != 0 ? GenericOverride.georamaBold20.getStringWidth(number + "") / 2.0F / 2.0F : 3.0F) + 7.0F;
                }

                offsetX = GenericOverride.georamaBold20.getStringWidth(roundedOrientationInf + "") / 2.0F / 2.0F + 7.0F - (7.0F + GenericOverride.georamaBold20.getStringWidth(roundedOrientationSup + "") / 2.0F / 2.0F) * ((float)(orientation - roundedOrientationInf) / 10.0F);

                for (i = roundedOrientationSup; i <= roundedOrientationSup + 80; i += 10)
                {
                    number = (i % 360 + 360) % 360;
                    ModernGui.drawScaledStringCustomFont(number + "", (float)offsetXMiddle + offsetX, (float)(azimutOffsetY + 3), 12237530, 0.5F, "center", false, "georamaBold", 20);
                    offsetX += GenericOverride.georamaBold20.getStringWidth(number + "") / 2.0F / 2.0F + 7.0F;
                }
            }
        }
    }
}

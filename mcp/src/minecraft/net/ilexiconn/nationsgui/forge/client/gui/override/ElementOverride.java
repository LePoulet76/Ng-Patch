package net.ilexiconn.nationsgui.forge.client.gui.override;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

@SideOnly(Side.CLIENT)
public interface ElementOverride
{
    ElementType getType();

    ElementType[] getSubTypes();

    void renderOverride(Minecraft var1, ScaledResolution var2, float var3);
}

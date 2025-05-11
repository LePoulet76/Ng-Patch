/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

@SideOnly(value=Side.CLIENT)
public interface ElementOverride {
    public RenderGameOverlayEvent.ElementType getType();

    public RenderGameOverlayEvent.ElementType[] getSubTypes();

    public void renderOverride(Minecraft var1, ScaledResolution var2, float var3);
}


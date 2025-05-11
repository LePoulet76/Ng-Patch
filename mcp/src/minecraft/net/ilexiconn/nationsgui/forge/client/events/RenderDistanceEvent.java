/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.world.World
 *  net.minecraftforge.event.Event
 */
package net.ilexiconn.nationsgui.forge.client.events;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.Event;

@SideOnly(value=Side.CLIENT)
public class RenderDistanceEvent
extends Event {
    public final EntityPlayer player;
    public final World world;
    public int renderDistance;

    public RenderDistanceEvent(int renderDistance, EntityPlayer player, World world) {
        this.renderDistance = renderDistance;
        this.player = player;
        this.world = world;
    }
}

